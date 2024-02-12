package com.SARankDirector.libxr;
import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.SARankDirector.libxr.PythonInterface;
import com.SARankDirector.libxr.NrealManager;
import android.util.Log;



public class Manager {
    private PythonInterface PyInt;
    private NrealManager nrealManager;
    public static ImuDataRaw mImuDataRaw;
    private Context context;

    public Manager(Context ApplicationContext) {
        context = ApplicationContext;
        //Constructor
        nrealManager = new NrealManager(context, mNrealListener);
        PyInt = new PythonInterface(context);
    }
    public String Fuse_and_get_string_from_raw_values(){
        PythonInterface.Fuse_Sensors(mImuDataRaw.RawValueArray,mImuDataRaw.GyroMultiplier, mImuDataRaw.GyroDivisor,mImuDataRaw.AccelMultiplier, mImuDataRaw.AccelDivisor);
        PythonInterface.Update_StringData();
        return(PythonInterface.StringData);
    }
    public void Connect(){
        nrealManager.connectToNrealUsbDevice();
    }
    public void Close(){
        nrealManager.closeNrealUsbDevice();
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
            //binding.vectorDisplayView.updateAcceleration(mImuDataRaw.getAcceleration());
            //updateStatus();
            //System.out.println(Fuse_and_get_string_from_raw_values());
        }

        @Override
        public void onButtonPressedTemp(int buttonId, int relatedValue) {
            appendLog("onButtonPressedTemp: btn=" + buttonId + ", relatedValue=" + relatedValue);
        }
    };
    private void appendLog(String str){
        System.out.println(str);
        System.out.println(Fuse_and_get_string_from_raw_values());
    }



}
