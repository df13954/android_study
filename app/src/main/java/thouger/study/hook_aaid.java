/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  android.util.Log
 *  de.robv.android.xposed.IXposedHookLoadPackage
 *  de.robv.android.xposed.XC_MethodHook
 *  de.robv.android.xposed.XC_MethodHook$MethodHookParam
 *  de.robv.android.xposed.XposedHelpers
 *  de.robv.android.xposed.callbacks.XC_LoadPackage$LoadPackageParam
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Ref$BooleanRef
 *  org.jetbrains.annotations.NotNull
 */
package thouger.study;

import android.util.Log;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public final class hook_aaid implements IXposedHookLoadPackage {
    @NotNull
    private static final String TAG = "XposedModule";
    private static final String PACKAGE_NAME = "com.zhiliaoapp.musically";


    public void handleLoadPackage(@NotNull XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals(PACKAGE_NAME)) return;
        hookAdvertisingIdService(lpparam.classLoader);
    }

    private void hookAdvertisingIdService(ClassLoader classLoader) {
        final boolean[] going = {false};
        Class<?> parcelClass = XposedHelpers.findClass("android.os.Parcel", (ClassLoader) classLoader);
        Log.d(TAG, "Parcel class found: " + parcelClass.getName());
        XposedHelpers.findAndHookMethod(parcelClass, "writeInterfaceToken", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                Log.d(TAG, "writeInterfaceToken() method called");
                String className = (String) param.args[0];
                if (className.equals("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService")) {
                    going[0] = true;
                }
            }
        });
        XposedHelpers.findAndHookMethod(parcelClass, "readString", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (going[0]) {
                    Log.d(TAG, "readString() method called");
                    param.setResult("a262d7d0-2154-4aa3-bcd6-79c376b5f356");
                    going[0] = false;
                }else{
                    super.afterHookedMethod(param);
                }
            }
        });
    }
}
