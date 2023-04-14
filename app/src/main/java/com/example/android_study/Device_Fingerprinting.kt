package com.example.android_study

import android.Manifest.permission.READ_PHONE_STATE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import timber.log.Timber
import java.util.*


class DeviceInfoUtils {
    val manufacturer: String
        get() = Build.MANUFACTURER

    val model: String
        get() = Build.MODEL

    val oSVersion: String
        get() = Build.VERSION.RELEASE

    val sDKVersion: Int
        get() = Build.VERSION.SDK_INT

    val TYPE: String
        get() = Build.TYPE

    val DEVICE: String
        get() = Build.DEVICE

    val DISPLAY: String
        get() = Build.DISPLAY

    val BRAND: String
        get() = Build.BRAND

    val BOARD: String
        get() = Build.BOARD

    val FINGERPRINT: String
        get() = Build.FINGERPRINT

    val HOST: String
        get() = Build.HOST

    val ID: String
        get() = Build.ID

    val MODEL: String
        get() = Build.MODEL

    val PRODUCT: String
        get() = Build.PRODUCT

    val TAGS: String
        get() = Build.TAGS

    val TIME: Long
        get() = Build.TIME

    val UNKNOWN: String
        get() = Build.UNKNOWN

    val HARDWARE: String
        get() = Build.HARDWARE

    val BUILD_USER: String
        get() = Build.USER

    val BUILD_HOST: String
        get() = Build.HOST

    val SUPPORTED_32_BIT_ABIS: Array<String>
        get() = Build.SUPPORTED_32_BIT_ABIS

    val SUPPORTED_64_BIT_ABIS: Array<String>
        get() = Build.SUPPORTED_64_BIT_ABIS

    val SUPPORTED_ABIS: Array<String>
        get() = Build.SUPPORTED_ABIS


    @RequiresApi(Build.VERSION_CODES.R)
    fun printALL(context: Context) {
        // 获取当前设备所在国家的名称
        Timber.d("country: " + getCountry());

        // 打印设备型号
        Timber.d("Model: " + model);

        // 打印设备类型
        Timber.d("TYPE: " + TYPE);

        // 打印设备品牌
        Timber.d("BRAND: " + BRAND);

        // 打印设备驱动名称
        Timber.d("DEVICE: " + DEVICE);

        // 打印设备SDK版本号
        Timber.d("SDK Version: " + sDKVersion);

        // 获取当前设备的系统语言
        Timber.d("language: " + getLanguage());

        // 获取当前设备的语言码
        Timber.d("languageCode: " + getLanguageCode());

        // 打印设备产品名称
        Timber.d("PRODUCT: " + PRODUCT);

        // 获取当前网络类型
        Timber.d("Network Type: " + getNetworkType(context));

        // 获取当前 SIM 卡所在的 MCC 码
        Timber.d("MCC: " + getMcc(context))

        //移动网络码
        Timber.d("get mnc: " + getMnc(context));

        // 打印当前设备的显示指标信息
        Timber.d("printDisplayMetrics: " + printDisplayMetrics(context))

        // 获取当前存储空间信息
        Timber.d("StatFs: " + getStatFs())

        // 获取设备的 CPU 指令集（ABI）
        Timber.d("CPU ABI: " + getSystemProperty("ro.product.cpu.abi"));
        // 获取设备的次要 CPU 指令集（ABI2）
        Timber.d("CPU ABI2: " + getSystemProperty("ro.product.cpu.abi2"));
        // 获取设备操作系统的架构类型
        Timber.d("OS ABI: " + getSystemProperty("os.arch"));
        // 获取设备当前运行的 ROM 版本
        Timber.d("ROM: " + getSystemProperty("ro.build.display.id"));

        //----------------------------------------------------------------------//
        // 打印设备制造商
        Timber.d("Manufacturer: " + manufacturer);


        // 打印设备操作系统版本号
        Timber.d("OS Version: " + oSVersion);


        // 打印设备序列号
        Timber.d("Serial Number: " + getSerialNumber(context));


        // 打印设备显示的版本包（在系统设置中显示为版本号）和ID一样
        Timber.d("DISPLAY: " + DISPLAY);


        // 打印设备主板名称
        Timber.d("BOARD: " + BOARD);

        // 打印设备指纹
        Timber.d("FINGERPRINT: " + FINGERPRINT);


        // 打印设备主机地址
        Timber.d("HOST: " + HOST);

        // 打印设备版本号
        Timber.d("ID: " + ID);

        // 打印设备硬件名称，一般和基板名称一样（BOARD）
        Timber.d("MODEL: " + MODEL);


        // 打印设备描述Build的标签
        Timber.d("TAGS: " + TAGS);

        // 打印设备当前时间
        Timber.d("TIME: " + TIME);

        // 打印设备版本号
        Timber.d("UNKNOWN: " + UNKNOWN);

        // 打印设备版本类型
        Timber.d("HARDWARE: " + HARDWARE);

        // 打印设备版本号
        Timber.d("BUILD_USER: " + BUILD_USER);

        // 打印设备版本号
        Timber.d("BUILD_HOST: " + BUILD_HOST);

        // 打印设备支持的32位ABIs（应用二进制接口）
        Timber.d("SUPPORTED_32_BIT_ABIS: " + SUPPORTED_32_BIT_ABIS);

        // 打印设备支持的64位ABIs
        Timber.d("SUPPORTED_64_BIT_ABIS: " + SUPPORTED_64_BIT_ABIS);

        // 打印设备支持的ABIs
        Timber.d("SUPPORTED_ABIS: " + SUPPORTED_ABIS);


        // 检测当前设备是否为模拟器
        Timber.d("isEmulator: " + isEmulator());
        // 检测当前设备是否已 root
        Timber.d("isRooted: " + isRooted());
        // 检测当前设备是否为平板电脑
        Timber.d("isTablet: " + isTablet(context));
        // 检测当前设备是否处于飞行模式
        Timber.d("isAirplaneMode: " + isAirplaneMode(context));
        // 检测当前设备是否正在充电
        Timber.d("isBatteryCharging: " + isBatteryCharging(context));


        // 获取 Android ID
        Timber.d("Android ID: " + getAndroidId(context));
        // 检测当前网络是否已连接
        Timber.d("Network Connected: " + isNetworkConnected(context));

        // 获取当前电池电量
        Timber.d("Battery Level: " + getBatteryLevel(context));
        // 获取当前屏幕密度
        Timber.d("Screen Density: " + getScreenDensity(context));
        // 获取当前屏幕宽度
        Timber.d("Screen Width: " + getScreenWidth(context));
        // 获取当前屏幕高度
        Timber.d("Screen Height: " + getScreenHeight(context));
        // 获取当前设备的 IMEI 号
        Timber.d("IMEI: " + getIMEI(context));
        // 获取当前连接的 WiFi 的 SSID
        Timber.d("WiFi SSID: " + getWifiSSID(context));
        // 检测当前设备是否具备加速度传感器
        Timber.d("Has Accelerometer: " + hasAccelerometer(context));
        // 检测当前设备是否具备陀螺仪传感器
        Timber.d("Has Gyroscope: " + hasGyroscope(context));
        // 检测当前设备是否具备 NFC 功能
        Timber.d("Has NFC: " + hasNFC(context));
        // 获取当前设备所在国家的国家码
        Timber.d("countryCode: " + getCountryCode(context));


        // 获取当前设备的语言变种
        Timber.d("variant: " + getVariant());


        // 获取当前设备所有的系统属性
//        Timber.d("getAllSystemProperties: " + getAllSystemProperties())

    }

    fun getSerialNumber(context: Context): String? {
        val serialNumber = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            Build.SERIAL
        } else {
            val androidId = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            androidId ?: ""
        }
        return serialNumber
    }

    private fun isBatteryCharging(context: Context): Any {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, intentFilter)
        val status = batteryStatus?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        return status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
    }

    private fun isAirplaneMode(context: Context): Any {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.AIRPLANE_MODE_ON,
            0
        ) != 0
    }

    private fun isTablet(context: Context): Any {
        return context.resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_LARGE
    }

    private fun isEmulator(): Any {
        return Build.FINGERPRINT.contains("generic") || Build.FINGERPRINT.contains("unknown") || Build.MODEL.contains(
            "google_sdk"
        ) || Build.MODEL.contains("Emulator") || Build.MODEL.contains("Android SDK built for x86") || Build.MANUFACTURER.contains(
            "Genymotion"
        ) || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")) || "google_sdk" == Build.PRODUCT
    }

    private fun isRooted(): Boolean {
        return "false" != getSystemProperty("ro.debuggable") || "1" == getSystemProperty("ro.secure") || "0" == getSystemProperty(
            "ro.secure"
        )
    }

    @SuppressLint("PrivateApi")
    private fun getSystemProperty(s: String): Any? {
        return try {
            val localClass = Class.forName("android.os.SystemProperties")
            val localMethod = localClass.getMethod("get", String::class.java)
            localMethod.invoke(localClass, s) as String
        } catch (localException: Exception) {
            localException.printStackTrace()
            null
        }
    }

    @SuppressLint("PrivateApi")
    fun getAllSystemProperties() {
        try {
            for (propertyName in getAllPropertyNames()) {
                val propertyValue = getSystemProperty(propertyName)
                Timber.d("Property: $propertyName, Value: $propertyValue")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAllPropertyNames(): Array<String> {
        return arrayOf(
            "ro.product.cpu.abi",
            "ro.product.cpu.abi2",
            "ro.product.cpu.arch",
            "ro.build.id",
            "ro.build.display.id",
            "ro.build.version.release",
            "ro.build.version.sdk",
            "ro.build.version.codename",
            "ro.build.version.incremental",
            "ro.build.type",
            "ro.build.tags",
            "ro.build.fingerprint",
            "ro.build.characteristics",
            "ro.product.model",
            "ro.product.brand",
            "ro.product.name",
            "ro.product.device",
            "ro.product.board",
            "ro.product.manufacturer",
            "ro.product.locale.language",
            "ro.product.locale.region",
            "persist.sys.timezone",
            "persist.sys.language",
            "persist.sys.country",
            "persist.sys.localevar",
            "ro.build.date.utc",
            "ro.build.date",
            "ro.build.user",
            "ro.build.host",
            "ro.product.cpu.build_display_id"
        )
    }

    fun printDisplayMetrics(context: Context) {
        val displayMetrics = DisplayMetrics()

        val x_px = displayMetrics.widthPixels.toString()
        val y_px = displayMetrics.heightPixels.toString()
        val d_dpi = displayMetrics.densityDpi.toString()
        val size: String =
            (context.getResources().getConfiguration().screenLayout and 15).toString()
        val xdp = displayMetrics.xdpi.toString()
        val ydp = displayMetrics.ydpi.toString()
        Timber.d("xdp: $xdp")
        Timber.d("ydp: $ydp")
        Timber.d("x_px: $x_px")
        Timber.d("y_px: $y_px")
        Timber.d("d_dpi: $d_dpi")
        Timber.d("size: $size")

    }

    fun getStatFs(): StatFs {
        return StatFs(Environment.getDataDirectory().absolutePath)
    }

    fun getMcc(context: Context): String {
        return context.resources.configuration.mcc.toString()
    }

    fun getMnc(context: Context): String {
        return context.resources.configuration.mnc.toString()
    }

    fun getCountryCode(context: Context): String {
        return (context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager).networkCountryIso
    }

    fun getCountry(): String {
        return Locale.getDefault().country
    }

    fun getLanguage(): String {
        return Locale.getDefault().language
    }

    fun getLanguageCode(): String {
        return Locale.getDefault().displayLanguage
    }

    fun getVariant(): String {
        return Locale.getDefault().displayVariant
    }

    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            var isConnected = false
            override fun onAvailable(network: Network) {
                isConnected = true
            }

            override fun onLost(network: Network) {
                isConnected = false
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        return networkCallback.isConnected
    }

    fun getNetworkType(context: Context): String {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return "No Network"
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return "WiFi"
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                val telephonyManager =
                    context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager




                if (ActivityCompat.checkSelfPermission(
                        context,
                        READ_PHONE_STATE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Timber.d("Permission Denied")
                } else {
                    Timber.d("Permission Granted")
                }
                when (telephonyManager.dataNetworkType) {
                    TelephonyManager.NETWORK_TYPE_GPRS,
                    TelephonyManager.NETWORK_TYPE_EDGE,
                    TelephonyManager.NETWORK_TYPE_CDMA,
                    TelephonyManager.NETWORK_TYPE_1xRTT,
                    TelephonyManager.NETWORK_TYPE_IDEN -> {
                        return "2G"
                    }
                    TelephonyManager.NETWORK_TYPE_UMTS,
                    TelephonyManager.NETWORK_TYPE_EVDO_0,
                    TelephonyManager.NETWORK_TYPE_EVDO_A,
                    TelephonyManager.NETWORK_TYPE_HSDPA,
                    TelephonyManager.NETWORK_TYPE_HSUPA,
                    TelephonyManager.NETWORK_TYPE_HSPA,
                    TelephonyManager.NETWORK_TYPE_EVDO_B,
                    TelephonyManager.NETWORK_TYPE_EHRPD,
                    TelephonyManager.NETWORK_TYPE_HSPAP -> {
                        return "3G"
                    }
                    TelephonyManager.NETWORK_TYPE_LTE,
                    TelephonyManager.NETWORK_TYPE_NR -> {
                        return "4G"
                    }
                }
            }
        }
        return "Unknown"
    }

    fun getBatteryLevel(context: Context): Int {
        val intent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        return (level * 100 / scale.toFloat()).toInt()
    }

    fun getScreenDensity(context: Context): Float {
        val metrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getRealMetrics(metrics)
        return metrics.density
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getScreenWidth(context: Context): Int {
        val activity = context as Activity
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun getScreenHeight(context: Context): Int {
        val activity = context as Activity
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.top - insets.bottom
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    fun getIMEI(context: Context): String? {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tm.imei
        } else {
            tm.deviceId
        }
    }

    fun getWifiSSID(context: Context): String? {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager != null) {
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo != null && wifiInfo.ssid != null) {
                return wifiInfo.ssid
            }
        }
        return null
    }

    fun hasAccelerometer(context: Context): Boolean {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        return accelerometer != null
    }

    fun hasGyroscope(context: Context): Boolean {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        return gyroscope != null
    }

    fun hasNFC(context: Context): Boolean {
        val packageManager = context.packageManager
        return packageManager.hasSystemFeature(PackageManager.FEATURE_NFC)
    }
}