package com.example.ecomonitor.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecomonitor.data.repositories.IAuthRepository
import com.example.ecomonitor.data.repositories.FirebaseAuthRepository
import com.example.ecomonitor.data.repositories.FirebaseMeasurementRepository
import com.example.ecomonitor.data.repositories.FirebaseUserRepository
import com.example.ecomonitor.data.repositories.IMeasurementRepository
import com.example.ecomonitor.data.repositories.IUserRepository
import com.example.ecomonitor.domain.model.Measurement
import com.example.ecomonitor.domain.model.ProfileData
import com.example.ecomonitor.domain.model.TransactionStatus
import com.example.ecomonitor.domain.model.TransactionStatus.Companion.LOADING_MESSAGE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainMenuViewModel(
    private val authRepository: IAuthRepository = FirebaseAuthRepository(),
    private val userRepository: IUserRepository = FirebaseUserRepository(),
) : ViewModel(){
    // Uninitialized repositories
    private var measurementRepository: IMeasurementRepository? = null

    //Private States
    private val _status = MutableLiveData<TransactionStatus>()
    private val _userState = MutableLiveData<ProfileData>()
    private val measurements = arrayListOf<Measurement>()
    private val _measurementsState = MutableLiveData<ArrayList<Measurement>>(measurements)

    //Public States
    val status: LiveData<TransactionStatus> get() = _status
    val userState:LiveData<ProfileData> get() = _userState
    val measurementsState:LiveData<ArrayList<Measurement>> get() = _measurementsState

    /**
     * Load the user from the repository
     * and update the user state with the new user
     * also initialize the measurement repository
     * with the auth user document id
     */
    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.retrieveProfileData()
            user?.let {
                withContext(Dispatchers.Main) {
                    _userState.value = it
                }
            }
        }
    }

    /**
     * Observe measurements from the repository
     * and update the measurements state
     * with the new measurements
     */
    fun observeMeasurements(userId: String) {
        //Init measurement repository
        measurementRepository = FirebaseMeasurementRepository()
        measurementRepository?.let {repository ->
            repository.observe {
                measurements.add(it)
                _measurementsState.value = measurements
            }
        }
    }

    /**
     * Sign out the user from the repository
     * and update the status state with the new status
     * also clear the user state
     */
    fun signOut() {
        _status.value = TransactionStatus.LoadingStatus(LOADING_MESSAGE)
        viewModelScope.launch(Dispatchers.IO) {
            val result = authRepository.signOut()
            withContext(Dispatchers.Main){ _status.value = result }
        }
    }
}