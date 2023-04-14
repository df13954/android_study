package com.example.android_study

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())

        Timber.d("检测授权")
        checkAndRequestLocationPermissions()

        val device_info = DeviceInfoUtils()
        device_info.printALL(this)
    }


    private fun checkAndRequestLocationPermissions() {
        val REQUEST_CODE_PERMISSIONS = 100
        val PERMISSIONS = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        //检查是否已经授权
        var allGranted = true
        for (permission in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                allGranted = false
                break
            }
        }
        //如果未授权，则请求权限
        if (!allGranted) {
            Timber.d("未授权")
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        } else {
            //已授权
            //TODO: 执行需要权限的操作
            Timber.d("已授权")
        }
    }

    private fun performLocationOperations() {
        // Perform location operations here
    }
}