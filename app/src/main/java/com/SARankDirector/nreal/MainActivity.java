package com.SARankDirector.nreal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.SARankDirector.nreal.databinding.ActivityMainBinding;
import com.SARankDirector.libxr.ImuDataRaw;
import com.SARankDirector.libxr.NrealManager;

public class MainActivity extends AppCompatActivity {

  private NrealManager nrealManager;
  private ActivityMainBinding binding;
  private ImuDataRaw mImuDataRaw;
  private Python Py;
  private PyObject fuser;
  private PyObject fsr;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    appendLog("Welcome. Logs will appear below.\n");

    nrealManager = new NrealManager(getApplicationContext(), mNrealListener);
    Python.start(new AndroidPlatform(this) );
    Py = Python.getInstance();
    fuser = Py.getModule("Sensorfusion");
    fsr = fuser.callAttr("fuse");
  }


  @Override
  protected void onStart() {
    super.onStart();
    appendLog("onStart -> connectToNrealUsbDevice()");
    nrealManager.connectToNrealUsbDevice();
  }

  @Override
  protected void onStop() {
    super.onStop();
    appendLog("onStop -> closeNrealUsbDevice()");
    nrealManager.closeNrealUsbDevice();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    appendLog("onNewIntent -> connectToNrealUsbDevice()");
    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
    if (device != null)
      nrealManager.connectToNrealUsbDevice();
  }


  private final NrealManager.Listener mNrealListener = new NrealManager.Listener() {
    @Override
    public void onDeviceConnected() {
      appendLog("onDeviceConnected: great");
    }

    @Override
    public void onDeviceDisconnected() {
      appendLog("onDeviceDisconnected: bye");
    }

    @Override
    public void onPermissionDenied() {
      appendLog("onPermissionDenied: please grant permission");
    }

    @Override
    public void onConnectionError(String error) {
      appendLog("onConnectionError: " + error);
    }

    @Override
    public void onMessage(String message) {
      appendLog("onMessage: " + message);
    }

    @Override
    public void onNewDataTemp(ImuDataRaw imuDataRawCopy) {
      mImuDataRaw = imuDataRawCopy;
      binding.vectorDisplayView.updateAcceleration(mImuDataRaw.getAcceleration());
      updateStatus();
    }

    @Override
    public void onButtonPressedTemp(int buttonId, int relatedValue) {
      appendLog("onButtonPressedTemp: btn=" + buttonId + ", relatedValue=" + relatedValue);
    }
  };


  private void appendLog(String message) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      if (!isUiContext()) {
        runOnUiThread(() -> appendLog("Error: this did not come from the UI thread -- " + message));
        return;
      }
    }
    binding.logTextView.append(message + "\n");
    binding.scrollView.post(() -> binding.scrollView.fullScroll(View.FOCUS_DOWN));
    updateStatus();
  }

  @SuppressLint("SetTextI18n")
  private void updateStatus() {
    if (nrealManager != null)
    //Original output from the original driver (commented out).
      /*binding.statusTextContent.setText("" +
          (nrealManager.isDeviceConnected() ? "Connected" : "Disconnected") + ", " +
          (nrealManager.isDeviceStreaming() ? "and Streaming" : "NOT streaming") + "\n" +
          (mImuDataRaw == null ? "No IMU data" : mImuDataRaw.toString())
      )
       */

      if (mImuDataRaw.RawValueArray != null){
        binding.statusTextContent.setText(fsr.callAttr("fuse_sensors",mImuDataRaw.RawValueArray,mImuDataRaw.GyroMultiplier, mImuDataRaw.GyroDivisor,mImuDataRaw.AccelMultiplier, mImuDataRaw.AccelDivisor).toString());
      }
  }

}