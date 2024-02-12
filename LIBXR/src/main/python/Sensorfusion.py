#Written by @SARankDirector
import java
import inspect
from com.SARankDirector.libxr import ImuDataRaw
#from fusion import Fusion
import numpy as np
import time
from ahrs.filters import Madgwick
from ahrs import Quaternion
class fuse():


    def __init__(self):
        self.oldtime = time.time()
        self.madgwick = Madgwick()
        self.Quaternion = Quaternion()
        pass
    def fuse_sensors(self, RawImuData,GyroMultiplier,GyroDivisor,Accelmultiplier,AccelDivisor):
        if (GyroMultiplier!= 0) and (GyroDivisor !=0) and (Accelmultiplier!= 0) and (AccelDivisor !=0):
            RVA = list(RawImuData)
            self.AccelData = [RVA[0]*Accelmultiplier/AccelDivisor,RVA[1]*Accelmultiplier/AccelDivisor,RVA[2]*Accelmultiplier/AccelDivisor]
            self.GyroData = [RVA[3]*GyroMultiplier/GyroDivisor*3.14159/180,RVA[4]*GyroMultiplier/GyroDivisor*3.14159/180,RVA[5]*GyroMultiplier/GyroDivisor*3.14159/180]
            self.MagData = [RVA[6],RVA[7],RVA[8]]
            self.Deltat = time.time() - self.oldtime
            self.madgwick.Dt =self.Deltat
            self.Quaternion = self.madgwick.updateIMU(self.Quaternion,acc=self.AccelData,gyr=self.GyroData)
            self.oldtime = time.time()
            return (str(self.Quaternion.to_angles()*180/3.14159))

        else:
            return("Error: no headset connected")

    def AngleString(self):
        return (str(self.Quaternion.to_angles()*180/3.14159))

