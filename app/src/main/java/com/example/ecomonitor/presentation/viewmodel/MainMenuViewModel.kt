package com.example.ecomonitor.presentation.viewmodel

import android.widget.Button
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
    private val measurementRepository: IMeasurementRepository = FirebaseMeasurementRepository()
) : ViewModel(){
    private val measurements = mutableListOf<Measurement>()

    private val _status = MutableLiveData<TransactionStatus>()
    private val _userState = MutableLiveData<ProfileData>()
    private val _measurementsState = MutableLiveData(measurements)

    val status: LiveData<TransactionStatus> get() = _status
    val userState:LiveData<ProfileData> get() = _userState
    val measurementsState:LiveData<MutableList<Measurement>> get() = _measurementsState

    /**
     * Loads the user from the repository
     * and updates the user state with the new user.
     */
    fun loadUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.retrieveProfileData()
            user?.let {
                withContext(Dispatchers.Main) { _userState.value = it }
            }
        }
    }

    suspend fun getRole(): String {
        return userRepository.getRole()
    }

    fun determineVisibility(providerButton: Button, clientButton: Button) {
        viewModelScope.launch(Dispatchers.IO) {
            val role = getRole()
            if (role == "CLIENTE") {
                withContext(Dispatchers.Main) {
                    providerButton.visibility = Button.GONE
                    clientButton.visibility = Button.VISIBLE
                }
            } else if (role == "PROVEEDOR") {
                withContext(Dispatchers.Main) {
                    providerButton.visibility = Button.VISIBLE
                    clientButton.visibility = Button.GONE
                }
            }
        }
    }

    /**
     * Observe measurements from the repository
     * and update the measurements state
     * with the new measurements
     */
    fun observeMeasurements() {
        measurementRepository.observe {
            measurements.add(it)
            _measurementsState.value = measurements
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