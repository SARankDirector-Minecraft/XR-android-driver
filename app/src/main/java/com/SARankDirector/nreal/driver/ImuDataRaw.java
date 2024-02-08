package com.SARankDirector.nreal.driver;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

public class ImuDataRaw {
  int accelX, accelY, accelZ;
  int angVelX, angVelY, angVelZ;
  public static int GyroMultiplier, AccelMultiplier;
  int magX, magY, magZ;
  long uptimeNs;
  public static long GyroDivisor, AccelDivisor;
  String _tmpOther;
  public static int[] RawValueArray = new int[9];

  void update(int AccelMultiplier, long AccelDivisor,int accelX, int accelY, int accelZ, int GyroMultiplier,long GyroDivisor, int angVelX, int angVelY, int angVelZ, int magX, int magY, int magZ, long uptimeNs) {
    this.AccelMultiplier = AccelMultiplier;
    this.AccelDivisor = AccelDivisor;
    this.accelX = accelX;
    this.accelY = accelY;
    this.accelZ = accelZ;
    this.GyroMultiplier = GyroMultiplier;
    this.GyroDivisor = GyroDivisor;
    this.angVelX = angVelX;
    this.angVelY = angVelY;
    this.angVelZ = angVelZ;
    this.magX = magX;
    this.magY = magY;
    this.magZ = magZ;
    this.uptimeNs = uptimeNs;
    RawValueArray[0] = accelX;
    RawValueArray[1] = accelY;
    RawValueArray[2] = accelZ;
    RawValueArray[3] = angVelX;
    RawValueArray[4] = angVelY;
    RawValueArray[5] = angVelZ;
    RawValueArray[6] = magX;
    RawValueArray[7] = magY;
    RawValueArray[8] = magZ;
  }

  void update(String other) {
    this._tmpOther = other;
  }

  public ImuDataRaw() {
    this.accelX = 0;
    this.accelY = 0;
    this.accelZ = 0;
    this.angVelX = 0;
    this.angVelY = 0;
    this.angVelZ = 0;
    this.magX = 0;
    this.magY = 0;
    this.magZ = 0;
    this.uptimeNs = 0;
    this._tmpOther = null;
    RawValueArray[0] = 0;
    RawValueArray[1] = 0;
    RawValueArray[2] = 0;
    RawValueArray[3] = 0;
    RawValueArray[4] = 0;
    RawValueArray[5] = 0;
    RawValueArray[6] = 0;
    RawValueArray[7] = 0;
    RawValueArray[8] = 0;
  }

  // copy constructor - used for now to send between threads
  public ImuDataRaw(@NonNull ImuDataRaw other) {
    this.accelX = other.accelX;
    this.accelY = other.accelY;
    this.accelZ = other.accelZ;
    this.angVelX = other.angVelX;
    this.angVelY = other.angVelY;
    this.angVelZ = other.angVelZ;
    this.magX = other.magX;
    this.magY = other.magY;
    this.magZ = other.magZ;
    this.uptimeNs = other.uptimeNs;
    this._tmpOther = other._tmpOther;
  }

  // string every vector
  @Override
  @NonNull
  @SuppressLint("DefaultLocale")
  public String toString() {
    return String.format(" - accel:  %d %d %d\n - angVel: %d %d %d\n - mag: %d %d %d\n - uptime: %d (s)%s",
        accelX, accelY, accelZ, angVelX, angVelY, angVelZ, magX, magY, magZ, (long) (uptimeNs / 1e9), _tmpOther != null ? _tmpOther : "n/a");
  }

  public float[] getAcceleration() {
    return new float[]{(float) accelX, (float) accelY, (float) accelZ};
  }
}