import firebase_admin
from firebase_admin import credentials, firestore
import random
import time
from datetime import datetime

cred = credentials.Certificate('./eco-monitor-sdk.json')
firebase_admin.initialize_app(cred)
# /users/dD491lM0fDPyV4a4j4QLpie9LTh1/devices/USKNvHqdeR9sQaFHBKNA
# /users/qKNBvoxHpLNFjN94Dv1DWhnYiTy1/devices/sQE6O12Ox2M8iVqu7lnJ

class Alert: 
    def __init__(self, date, name, deviceRef):
        self.date = date
        self.name = name
        self.deviceRef = deviceRef

    def to_dict(self):
        return {
            'date': self.date,
            'name': self.name,
            'deviceUid': self.deviceRef
        }

class Measurement:
    def __init__(self, date, value, measureUnit, deviceRef):
        self.date = date
        self.value = value
        self.measureUnit = measureUnit
        self.deviceRef = deviceRef

    def to_dict(self):
        return {
            'date': self.date,
            'value': self.value,
            'measureUnit': self.measureUnit,
            'deviceUid': self.deviceRef
        }

def send_measurement():
    db = firestore.client()
    user_ref = db.collection('users').document('qKNBvoxHpLNFjN94Dv1DWhnYiTy1')
    measurements_ref = user_ref.collection('measurements')
    device_ref = user_ref.collection('devices').document('sQE6O12Ox2M8iVqu7lnJ')

    for i in range(0, 5):
        value = random.randint(0, 100)
        measurement = Measurement(date=datetime.now(), value=random.randint(0, 100), measureUnit='KWH', deviceRef=device_ref)
        if value > 70:
            alert = Alert(date=datetime.now(), name='High Consumption', deviceRef=device_ref)
            alerts_ref = user_ref.collection('alerts')
            alerts_ref.add(alert.to_dict())
            print(f'Sent: {alert.to_dict()}')
        measurements_ref.add(measurement.to_dict())
        print(f'Sent: {measurement.to_dict()}')
        time.sleep(2)

if __name__ == '__main__':
    send_measurement()
