package thouger.study

import android.Manifest.permission.READ_PHONE_STATE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
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
import org.lsposed.hiddenapibypass.HiddenApiBypass
import timber.log.Timber
import java.lang.reflect.Field
import java.util.*


class DeviceInfoUtils {
    val manufacturer: String
        get() = Build.MANUFACTURER

    val MODEL: String
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
    fun printALL(context: Context): MutableMap<String, String> {
        val map: MutableMap<String, String> = HashMap()
        val country = getCountry()

        // 打印设备型号
        val model = MODEL

        // 打印设备类型
        val type = TYPE

        // 打印设备品牌
        val brand = BRAND

        // 打印设备驱动名称
        val device = DEVICE

        // 打印设备SDK版本号
        val sDKVersion = sDKVersion

        // 获取当前设备的系统语言
        val language = getLanguage()

        // 获取当前设备的语言码
        val languageCode = getLanguageCode()

        // 打印设备产品名称
        val product = PRODUCT

        // 获取当前网络类型
        val networkType = getNetworkType(context)

        // 获取当前 SIM 卡所在的 MCC 码
        val mcc = getMcc(context)

        //移动网络码
        val mnc = getMnc(context)

        // 打印当前设备的显示指标信息
        val DisplayMetrics = getDisplayMetrics(context)

        // 获取当前存储空间信息
        val (usedSpace, totalSpace) = getStatFs()

        // 获取设备的 CPU 指令集（ABI）
        val abi = getSystemProperty("ro.product.cpu.abi")

        // 获取设备的次要 CPU 指令集（ABI2）
        val abi2 = getSystemProperty("ro.product.cpu.abi2")

        // 获取设备操作系统的架构类型
        val osAbi = getSystemProperty("os.arch")

        // 获取设备当前运行的 ROM 版本
        val rom = getSystemProperty("ro.build.display.id")

        //----------------------------------------------------------------------//
        // 打印设备制造商
        val manufacturer = manufacturer

        // 打印设备操作系统版本号
        val oSVersion = oSVersion

        // 打印设备序列号
        val serialNumber = getSerialNumber()

        // 打印设备显示的版本包（在系统设置中显示为版本号）和ID一样
        val display = DISPLAY

        // 打印设备主板名称
        val board = BOARD

        // 打印设备指纹
        val fingerprint = FINGERPRINT

        // 打印设备主机地址
        val host = HOST

        // 打印设备版本号
        val id = ID

        // 打印设备描述Build的标签
        val tags = TAGS

        // 打印设备当前时间
        val time = TIME

        // 打印设备版本号
        val unknown = UNKNOWN

        // 打印设备版本类型
        val hardware = HARDWARE

        // 打印设备版本号
        val buildUser = BUILD_USER

        // 打印设备版本号
        val buildHost = BUILD_HOST

        // 打印设备支持的32位ABIs（应用二进制接口）
        val supported32BitAbis = SUPPORTED_32_BIT_ABIS

        // 打印设备支持的64位ABIs
        val supported64BitAbis = SUPPORTED_64_BIT_ABIS

        // 打印设备支持的ABIs
        val supportedAbis = SUPPORTED_ABIS

        // 检测当前设备是否为模拟器
        val isEmulator = isEmulator()
        // 检测当前设备是否已 root
        val isRooted = isRooted()
        // 检测当前设备是否为平板电脑
        val isTablet = isTablet(context)
        // 检测当前设备是否处于飞行模式
        val isAirplaneMode = isAirplaneMode(context)
        // 检测当前设备是否正在充电
        val isBatteryCharging = isBatteryCharging(context)

        // 获取 Android ID
        val androidId = getAndroidId(context)
        // 检测当前网络是否已连接
        val isNetworkConnected = isNetworkConnected(context)

        // 获取当前电池电量
        val batteryLevel = getBatteryLevel(context)
        // 获取当前屏幕密度
        val screenDensity = getScreenDensity(context)
        // 获取当前屏幕宽度
        val screenWidth = getScreenWidth(context)
        // 获取当前屏幕高度
        val screenHeight = getScreenHeight(context)
        // 获取当前设备的 IMEI 号
        val imei = getIMEI(context)
        // 获取当前连接的 WiFi 的 SSID
        val wifiSSID = getWifiSSID(context)
        // 检测当前设备是否具备加速度传感器
        val hasAccelerometer = hasAccelerometer(context)
        // 检测当前设备是否具备陀螺仪传感器
        val hasGyroscope = hasGyroscope(context)
        // 检测当前设备是否具备 NFC 功能
        val hasNFC = hasNFC(context)
        // 获取当前设备所在国家的国家码
        val countryCode = getCountryCode(context)

        // 获取当前设备的语言变种
        val variant = getVariant()

        // 获取当前设备所有的系统属性
        val AllSystemProperties = getAllSystemProperties()

        // 获得所有
        map["country"] = country
        map["language"] = language
        map["model"] = model
        map["manufacturer"] = manufacturer
        map["brand"] = brand
        map["product"] = product
        map["device"] = device
        map["sDKVersion"] = sDKVersion.toString()
        map["languageCode"] = languageCode
        map["networkType"] = networkType
        map["mcc"] = mcc
        map["mnc"] = mnc
        map["usedSpace"] = usedSpace.toString()
        map["totalSpace"] = totalSpace.toString()
        map["abi"] = abi
        map["abi2"] = abi2
        map["osAbi"] = osAbi
        map["rom"] = rom
        map["manufacturer"] = manufacturer
        map["oSVersion"] = oSVersion
        map["serialNumber"] = serialNumber
        map["display"] = display
        map["board"] = board
        map["fingerprint"] = fingerprint
        map["host"] = host
        map["id"] = id
        map["tags"] = tags
        map["time"] = time.toString()
        map["unknown"] = unknown
        map["hardware"] = hardware
        map["buildUser"] = buildUser
        map["buildHost"] = buildHost
        map["supported32BitAbis"] = Arrays.toString(supported32BitAbis)
        map["supported64BitAbis"] = Arrays.toString(supported64BitAbis)
        map["supportedAbis"] = Arrays.toString(supportedAbis)
        map["isEmulator"] = isEmulator.toString()
        map["isRooted"] = isRooted().toString()
        map["isTablet"] = isTablet.toString()
        map["isAirplaneMode"] = isAirplaneMode.toString()
        map["isBatteryCharging"] = isBatteryCharging.toString()
        map["androidId"] = androidId
        map["isNetworkConnected"] = isNetworkConnected.toString()
        map["batteryLevel"] = batteryLevel.toString()
        map["screenDensity"] = screenDensity.toString()
        map["screenWidth"] = screenWidth.toString()
        map["screenHeight"] = screenHeight.toString()
        map["imei"] = imei
        map["wifiSSID"] = wifiSSID
        map["hasAccelerometer"] = hasAccelerometer.toString()
        map["hasGyroscope"] = hasGyroscope.toString()
        map["hasNFC"] = hasNFC.toString()
        map["countryCode"] = countryCode
        map["variant"] = variant
        for (key in DisplayMetrics.keys) {
            map[key] = DisplayMetrics[key].toString()
        }
        for (key in AllSystemProperties.keys) {
            map[key] = AllSystemProperties[key].toString()
        }
        for(key in map.keys){
            Timber.d(key + " : " + map[key])
        }
        return map
    }

    @SuppressLint("PrivateApi")
    fun getAllSystemProperties(): MutableMap<String, String> {
        val map: MutableMap<String, String> = HashMap()
        try {
            for (propertyName in getAllPropertyNames()) {
                val propertyValue = getSystemProperty(propertyName)
                map[propertyName] = propertyValue
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    fun getSerialNumber(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10及更高版本，无法直接获取IMEI号，给出提示
            Timber.d("Android 11及更高版本，无法直接获取SerialNumber号")
            "null"
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Build.getSerial()
        } else {
            Build.SERIAL
        }
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
    private fun getSystemProperty(s: String): String {
        return try {
            val localClass = Class.forName("android.os.SystemProperties")
            val localMethod = localClass.getMethod("get", String::class.java)
            localMethod.invoke(localClass, s) as String
        } catch (localException: Exception) {
            localException.printStackTrace()
            "null"
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

    fun getDisplayMetrics(context: Context): MutableMap<String, String> {
        val displayMetrics = DisplayMetrics()

        val x_px = displayMetrics.widthPixels.toString()
        val y_px = displayMetrics.heightPixels.toString()
        val d_dpi = displayMetrics.densityDpi.toString()
        val size: String =
            (context.getResources().getConfiguration().screenLayout and 15).toString()
        val xdp = displayMetrics.xdpi.toString()
        val ydp = displayMetrics.ydpi.toString()
        val map: MutableMap<String, String> = HashMap()
        map["xdp"] = xdp
        map["ydp"] = ydp
        map["x_px"] = x_px
        map["y_px"] = y_px
        map["d_dpi"] = d_dpi
        map["size"] = size
        return map
    }

    fun getStatFs(): Pair<Long, Long> {
        val statFs = StatFs(Environment.getExternalStorageDirectory().path)
        val blockSize = statFs.blockSizeLong
        val totalBlocks = statFs.blockCountLong
        val availableBlocks = statFs.availableBlocksLong

        val totalSpace = totalBlocks * blockSize
        val usedSpace = (totalBlocks - availableBlocks) * blockSize

        return Pair(usedSpace, totalSpace)
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

    @RequiresApi(Build.VERSION_CODES.P)
    fun diff_androidId(context: Context) {
        val allStaticFields = HiddenApiBypass.getStaticFields(Settings.Secure::class.java)
        for (field in allStaticFields) {
            if (field.toString().contains("sNameValueCache")) {
                val _field = field as Field
                _field.isAccessible = true
                val nameValueCacheObj = field.get(null)
                // 获取 getStringForUser 方法
                val getStringForUserMethod = nameValueCacheObj::class.java.getDeclaredMethod(
                    "getStringForUser",
                    ContentResolver::class.java,
                    String::class.java,
                    Int::class.java
                )
                getStringForUserMethod.isAccessible = true

                // 调用 getStringForUser 方法
                val contentResolver: ContentResolver = context.contentResolver
                val androidId = getStringForUserMethod.invoke(
                    nameValueCacheObj,
                    contentResolver,
                    "android_id",
                    0
                )
                //#调用putStringForUser
                for (method in HiddenApiBypass.getDeclaredMethods(nameValueCacheObj::class.java)) {
                    Timber.d("NameValueTable method: $method")
                    if (method.toString().contains("putStringForUser")) {

                        Timber.d("ApplicationInfo method: $method")
                        val _method = method as java.lang.reflect.Method
                        _method.isAccessible = true
                        if (_method.name == "putStringForUser") {
                            val androidid = _method.invoke(
                                nameValueCacheObj,
                                contentResolver,
                                "android_id",
                                "07771e81ad2d6680",
                                null,
                                false,
                                1,
                                false
                            )
                            Timber.d("result1111111111111: " + androidid)
                        }
                    }
                }

                val android = Settings.Secure.getString(
                    contentResolver,
                    Settings.Secure.ANDROID_ID
                )
            }
        }
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

    fun getIMEI(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Android 10及更高版本，无法直接获取IMEI号，给出提示
            Timber.d("Android 10及更高版本，无法直接获取IMEI号")
            "null"
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tm.imei
        } else {
            tm.deviceId
        }
    }

    fun getWifiSSID(context: Context): String {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (wifiManager != null) {
            val wifiInfo = wifiManager.connectionInfo
            if (wifiInfo != null && wifiInfo.ssid != null) {
                return wifiInfo.ssid
            }
        }
        return "null"
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