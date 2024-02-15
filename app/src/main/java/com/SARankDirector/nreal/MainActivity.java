package com.SARankDirector.nreal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.SARankDirector.libxr.Manager;
import com.SARankDirector.nreal.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


  private ActivityMainBinding binding;

  private Manager XRManager;
  private Context context;



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    appendLog("Welcome. Logs will appear below.\n");
    context = getApplicationContext();
  }


  @Override
  protected void onStart() {
    super.onStart();
    appendLog("onStart -> connectToNrealUsbDevice()");
    XRManager = new Manager();
    XRManager.init(this);
    XRManager.Connect();
  }

  @Override
  protected void onStop() {
    super.onStop();
    appendLog("onStop -> closeNrealUsbDevice()");
    XRManager.Close();
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);
    appendLog("onNewIntent -> connectToNrealUsbDevice()");
    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
    if (device != null)
      XRManager.Connect();
  }





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
      //if (XRManager.mImuDataRaw.RawValueArray != null){
        //binding.statusTextContent.setText(XRManager.Fuse_and_get_string_from_raw_values());
      //}
  }

}