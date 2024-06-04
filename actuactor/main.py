import firebase_admin
from firebase_admin import credentials, firestore
import random
import time
from datetime import datetime

cred = credentials.Certificate('./eco-monitor-sdk.json')
firebase_admin.initialize_app(cred)

class Measurement:
    def __init__(self, date, value, measureUnit):
        self.date = date
        self.value = value
        self.measureUnit = measureUnit

    def to_dict(self):
        return {
            'date': self.date,
            'value': self.value,
            'measureUnit': self.measureUnit
        }

def send_measurement():
    db = firestore.client()
    user_ref = db.collection('users').document('qKNBvoxHpLNFjN94Dv1DWhnYiTy1')
    measurements_ref = user_ref.collection('measurements')

    for i in range(0, 4):
        measurement = Measurement(date=datetime.now(), value=random.randint(0, 100), measureUnit='M3')
        measurements_ref.add(measurement.to_dict())
        print(f'Sent: {measurement.to_dict()}')
        time.sleep(2)

if __name__ == '__main__':
    send_measurement()
