package com.SARankDirector.libxr;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class PythonInterface {
    public static String StringData; // Stores a string with the current rotation values, useful for demos/debugging
    public static Python Py;
    public static PyObject fuser;
    public static PyObject fsr;
    private Context context;

    public PythonInterface(Context ApplicationContext){
        context = ApplicationContext;
        Python.start(new AndroidPlatform(context) );
        Py = Python.getInstance();
        fuser = Py.getModule("Sensorfusion");
        fsr = fuser.callAttr("fuse");
    }



    public static void Fuse_Sensors(int[] RawValueArray, int GyroMultiplier, long GyroDivisor, int AccelMultiplier, long AccelDivisor) {
        fsr.callAttr("fuse_sensors",RawValueArray,GyroMultiplier, GyroDivisor,AccelMultiplier, AccelDivisor);
    }
    public static void Update_StringData(){
        StringData = fsr.callAttr("AngleString").toString();
    }

}