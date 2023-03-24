package com.example.myapplication;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
final class AFa1nSDK$30218 {}
public class hook implements IXposedHookLoadPackage {
    //定义包名
    private static final String PACKAGE_NAME = "com.example.appa";
    
    private static XC_LoadPackage.LoadPackageParam lpparam = null;

    private static Object new_obj = null;

    public hook() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam _lpparam) throws ClassNotFoundException {
        lpparam = _lpparam;
        // 过滤不必要的应用
        if (!lpparam.packageName.equals(PACKAGE_NAME)) return;
        // 执行Hook
        hook(lpparam);
    }

    private void hook(XC_LoadPackage.LoadPackageParam lpparam) throws ClassNotFoundException {
        Logger.i("handleLoadPackage: " + lpparam.packageName);

//        Class<?> clazz = XposedHelpers.findClass("java.lang.System", lpparam.classLoader);
        Class<?> classs = lpparam.classLoader.loadClass("dalvik.system.DexFile");;
//        hook_one_method(classs,"attach", Context.class);
        hook_all_method(classs,"defineClass");

    }

    private void hook_one_method(Class<?> clazz, String methodName, Object... parameterTypesAndCallback) {
        findAndHookMethod(Application.class, "attach",
                Context.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                if (param == null) {
                    return;
                }
                //输出所有参数
                for (Object o : param.args) {
                    Logger.i("beforeHookedMethod参数：" + o);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Logger.i("afterHookedMethod返回值：" + param.getResult());
                if (param == null) {
                    return;
                }
                //输出所有参数
                for (Object o : param.args) {
                    Logger.i("afterHookedMethod参数：" + o);
                }

                ClassLoader cl = ((Context) param.args[0]).getClassLoader();
                Class<?> hookclass = null;
                try {
                    hookclass = cl.loadClass("com.appsflyer.internal.AFa1nSDK$30218$AFa1wSDK$25697");
                    Logger.i("load success");
                } catch (Exception e) {
                    Logger.i("load class error");
                    return;
                }
            }
        });
    }

        private void hook_all_method(Class<?> clazz,String methodName){
            String className = clazz.getName();
            Method[] m = clazz.getDeclaredMethods();

            //hook构造函数
            if (className.equals("com.appsflyer.internal.AFa1uSDK$24407") || className.equals("com.appsflyer.internal.AFa1nSDK$30218$AFa1wSDK$25697")) {
                XposedBridge.hookAllConstructors(clazz, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (param == null) {
                            return;
                        }
                        //输出所有参数
                        for (Object o : param.args) {
                            Logger.i(className + ",Constructor beforeHookedMethod参数：" + o);
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Object obj = param.thisObject;
                        Logger.i(className + ",Constructor afterHookedMethod参数：" + obj);
                        if (className.equals("com.appsflyer.internal.AFa1nSDK$30218$AFa1wSDK$25697")){
                            new_obj = obj;
                            Logger.i("new_obj is1: " + new_obj);
                        }
                        try {
                            Field field = obj.getClass().getDeclaredField("valueOf");
                            field.setAccessible(true);
                            Object value = field.get(obj);
                            // 打印成员变量
                            //values是一个字节数组，下面输出数组的值
                            Logger.i("com.appsflyer.internal.AFa1uSDK$24407  valuesof is1: " + Arrays.toString((byte[]) value));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            for (Method method : m) {
                String classMethodName = method.getName();
                if (methodName != null && !methodName.equals(classMethodName)) {
                    continue;
                }
                String output = "类名：" + className + ",方法名：" + classMethodName + " ";



                XposedBridge.hookMethod(method, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        if (param == null) {
                            return;
                        }
                        //输出所有参数
                        for (Object o : param.args) {
                            Logger.i(output + ",beforeHookedMethod参数：" + o);
                        }
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Object result = param.getResult();
                        Logger.i(output + ",afterHookedMethod返回值：" + result);
                        if (param == null) {
                            return;
                        }
                        //输出所有参数
                        for (int i = 0; i < param.args.length; i++) {
                            Logger.i(output + ",afterHookedMethod参数"+i+"： " + param.args[i]);
                        }
                        if (param.args[0] instanceof String) {
                            String ClassName = (String) param.args[0];
                            if (ClassName.contains("com.appsflyer.internal.AFa1nSDK$30218")) {
                                Logger.i("find class1");
                                Class _clazz = (Class) param.getResult();
                                hook_all_method(_clazz, null);
                            }
                            if (ClassName.contains("com.appsflyer.internal.AFa1uSDK$24407")) {
                                Logger.i("find class2");
                                Class _clazz = (Class) param.getResult();
                                hook_all_method(_clazz, null);
                            }
                        }
                        if (classMethodName.equals("AFInAppEventParameterName")) {
                            //将返回值转化为数组输出
                            byte[] array = (byte[]) result;
                            Logger.i("com.appsflyer.internal.AFa1nSDK$30218.AFInAppEventParameterName 返回值：" + Arrays.toString(array));
                        }
                        if (classMethodName.equals("AFInAppEventType")) {
                            //将返回值转化为数组输出
                            byte[] array = (byte[]) getFieldValue(clazz, "values");
                            Logger.i("com.appsflyer.internal.AFa1uSDK$24407 values " + Arrays.toString(array));
                            Logger.i("com.appsflyer.internal.AFa1uSDK$24407 AFInAppEventType bArr " + Arrays.toString((byte[]) param.args[0]));
                        }
                        if ( classMethodName.equals("AFKeystoreWrapper") && className.equals("com.appsflyer.internal.AFa1nSDK$30218")){
                            //返回值是一个字节数组，
                            byte[] array = (byte[]) result;
                            //转为字符串输出
                            String str = new String(array);
                            Logger.i("com.appsflyer.internal.AFa1nSDK$30218.AFKeystoreWrapper 返回值字符串：" + str);
                        }
                    }
                });
            }
        }

//    private Object getFieldValue(Class obj) {
//        Field field = null;
//        if (obj == null) {
//            return null;
//        }
//        Logger.i("obj is: " + obj);
//        for(Field f : obj.getDeclaredFields()){
//            f.setAccessible(true);
//            try{
//                field = obj.getDeclaredField(f.getName());
//                Logger.i("field is: " + field.get(obj));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//        return field;
//    }


    private Object getFieldValue(Class<?> obj, String... fieldNames) {
        Object field = null;
        if (obj == null) {
            return null;
        }
        for (String fieldName : fieldNames) {
            field = getFieldValue(obj, fieldName);
        }
        return field;
    }


    private Object getFieldValue(Class<?> obj, String fieldName) {
        try {
            Logger.i("obj is: " + obj);
            Field field = obj.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
        // 第一步：Hook方法ClassLoader#loadClass(String)
//        findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) {
//                if (param.hasThrowable()) return;
//                Class<?> cls = (Class<?>) param.getResult();
//                String name = cls.getName();
//                Logger.i("loadClass: " + name);
////                if (classes.contains(name)) {
////                    // 所有的类都是通过loadClass方法加载的
////                    // 所以这里通过判断全限定类名，查找到目标类
////                    // 第二步：Hook目标方法
////                    findAndHookMethod(cls, "methodName", paramTypes, new XC_MethodHook() {
////                        @Override
////                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
////                            // TODO
////                        }
////
////                        @Override
////                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
////                            // TODO
////                        }
////                    });
////                }
//            }
//        });
    }
