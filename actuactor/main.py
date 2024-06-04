import firebase_admin
from firebase_admin import credentials, firestore
import random
import time
from datetime import datetime

cred = credentials.Certificate('./eco-monitor-sdk.json')
firebase_admin.initialize_app(cred)
# /users/dD491lM0fDPyV4a4j4QLpie9LTh1/devices/USKNvHqdeR9sQaFHBKNA
# /users/qKNBvoxHpLNFjN94Dv1DWhnYiTy1/devices/sQE6O12Ox2M8iVqu7lnJ
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
    user_ref = db.collection('users').document('dD491lM0fDPyV4a4j4QLpie9LTh1')
    measurements_ref = user_ref.collection('measurements')
    device_ref = user_ref.collection('devices').document('USKNvHqdeR9sQaFHBKNA')

    for i in range(0, 5):
        measurement = Measurement(date=datetime.now(), value=random.randint(0, 100), measureUnit='KWH', deviceRef=device_ref)
        measurements_ref.add(measurement.to_dict())
        print(f'Sent: {measurement.to_dict()}')
        time.sleep(2)

if __name__ == '__main__':
    send_measurement()
