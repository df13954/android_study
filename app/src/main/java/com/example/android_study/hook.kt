package com.example.android_study

import android.util.Log
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam
import java.lang.reflect.Method
import android.os.Bundle


class hook : IXposedHookLoadPackage {
    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: LoadPackageParam) {
//        if (lpparam.packageName == "com.example.android_study") { // 指定目标应用的包名
        if (lpparam.packageName == "com.ss.android.ugc.trill") { // 指定目标应用的包名
            Log.d(TAG, "Hooking into target app: " + lpparam.packageName)

//            set_language(lpparam.classLoader)
//            hook_gaid(lpparam.classLoader)
//            http_params(lpparam.classLoader)
        }
    }

    private fun set_language(classLoader: ClassLoader){
        val clazz = XposedHelpers.findClass("java.util.Locale",classLoader)
        val className = clazz.name
        val m: Array<Method> = clazz.getDeclaredMethods()
        for (method in m) {
            val classMethodName = method.name
            if ("getDisplayLanguage" != classMethodName && "getLanguage" != classMethodName) {
                continue
            }
            Log.d(TAG, "Hooking into target app11111111111111: " +method.name)
            val output = "类名：$className,方法名：$classMethodName "
            Log.d(TAG, "Hooking into target app2: " +method.name)

            XposedBridge.hookMethod(method, object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    var result = param.result
                    //判断result是否是字符串类型
                    if (result !is String) {
                        return
                    }
                    if (classMethodName=="getDisplayLanguage"){
                        param.result = "한국어"
                        result = param.result
//                        Log.d(TAG, "getDisplayLanguage() result: $result")
                    }else if(classMethodName=="getLanguage"){
                        param.result = "ko"
                        result = param.result
//                        Log.d(TAG, "getLanguage() result: $result")
                    }
                }
            })
        }
    }

    private fun http_params(classLoader: ClassLoader){
        var methodName = "AFInAppEventType"
        Log.d(TAG, "Hooking into target app1: " +methodName)
        val clazz = XposedHelpers.findClass("com.appsflyer.internal.aj",classLoader)
        val className = clazz.name
        val m: Array<Method> = clazz.getDeclaredMethods()
        for (method in m) {
            val classMethodName = method.name
            if (methodName != classMethodName) {
                continue
            }
            val output = "类名：$className,方法名：$classMethodName "
            Log.d(TAG, "Hooking into target app2: " +method.name)

            XposedBridge.hookMethod(method, object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    Log.d(TAG, "Hooking into target app3: ")
                    super.beforeHookedMethod(param)
                    val args = param.args
                    val length = args.size
                    Log.d(TAG, "参数长度：$output + $length")
                    for (i in 0 until length) {
                        Log.d(TAG, "参数：$i = " + args[i])
                    }
                    //输出最后一个数组参数
                    val array = args[length - 1] as Array<*>
                    for (i in array.indices) {
                        Log.d(TAG, "数组参数：$i = " + array[i])
                    }
                }
            })
        }
    }

    private fun hook_gaid(classLoader: ClassLoader) {
        try {
            val parcelClass = XposedHelpers.findClass("android.os.Parcel", classLoader)
            Log.d(TAG, "Parcel class found: " + parcelClass.name)

            XposedHelpers.findAndHookMethod(parcelClass, "readString", object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    super.afterHookedMethod(param)
                    var result = param.result
                    //判断result是否是字符串类型
                    if (result !is String) {
                        return
                    }
                    //用正则判断四个横杠
                    if (result.toString().matches(Regex("[a-f0-9]{8}-[a-f0-9]{4}-4[a-f0-9]{3}-[8|9|aA|bB][a-fA-F0-9]{3}-[a-fA-F0-9]{12}"))) {
                        param.result = "00000000-0000-0000-0000-000000000000"
                        result = param.result
                        Log.d(TAG, "readString() result: $result")
                    }
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "Hooking failed: " + e.message)
        }
    }

    companion object {
        private const val TAG = "XposedModule"
        private const val ADVERTISING_ID_SERVICE_CLASS =
            "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService"
    }
}