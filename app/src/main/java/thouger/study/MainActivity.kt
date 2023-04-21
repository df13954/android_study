package thouger.study

import android.Manifest
import android.content.ContentResolver
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.lsposed.hiddenapibypass.HiddenApiBypass
import timber.log.Timber
import java.lang.reflect.Field
import java.lang.reflect.Method


class MainActivity : AppCompatActivity() {
    //定义请求码和请求的权限数组
    private val REQUEST_CODE_PERMISSIONS = 100

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val PERMISSIONS = arrayOf(
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.plant(Timber.DebugTree())


//        Timber.d("检测授权")
        checkAndRequestLocationPermissions()

        val device_info = DeviceInfoUtils()
//        val map = device_info.printALL(this)

        //内存漫游
        val choose = ChooseUtils.choose(MainActivity::class.java)
        if (choose != null) {
            Timber.d("当前class的个数: ${choose.size}")
            for (i in choose.indices) {
                Timber.d("当前class是: ${choose[i]}")
            }
        }

//        val fileName = "device_info.json-"+System.currentTimeMillis()
//        fileUtils().saveFile(this, fileName, map)
//        val DetectVirtualAppsResult = DetectVirtualApps().isRunningInVirtualApp()
//        if (DetectVirtualAppsResult) {
//            Toast.makeText(this, "检测到虚拟环境", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "未检测到虚拟环境", Toast.LENGTH_SHORT).show()
//        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkAndRequestLocationPermissions() {
        //检查是否已经授权
        for (permission in PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Timber.d("未授权")
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_PERMISSIONS)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //检查是否是请求读取手机状态和位置权限的请求码
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            //检查权限是否授予
            var allGranted = true
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                    break
                }
            }
            if (allGranted) {
                //TODO: 执行需要权限的操作
                Timber.d("已授权")
            } else {
                //TODO: 处理用户未授权的情况
                Timber.d("未授权")
            }
        }
    }

    private fun performLocationOperations() {
        // Perform location operations here
    }
}