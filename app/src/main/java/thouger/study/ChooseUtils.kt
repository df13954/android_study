//package thouger.study
//
//import android.annotation.TargetApi
//import android.os.Build
//import android.provider.SyncStateContract.Constants
//import android.util.Log
//import java.lang.reflect.Method
//import java.util.Arrays
//
//
//object ChooseUtils {
//    private var startMethodTracingMethod: Method? = null
//    private var stopMethodTracingMethod: Method? = null
//    private var getMethodTracingModeMethod: Method? = null
//    private var getRuntimeStatMethod: Method? = null
//    private var getRuntimeStatsMethod: Method? = null
//    private var countInstancesOfClassMethod: Method? = null
//    private var countInstancesOfClassesMethod: Method? = null
//    private var getInstancesOfClassesMethod: Method? = null
//
//    init {
//        try {
//            val c: Class<*> = Class.forName("dalvik.system.VMDebug")
//            val startMethodTracingMethod: Method = c.getDeclaredMethod(
//                "startMethodTracing", String::class.java,
//                Int::class.javaPrimitiveType, Int::class.javaPrimitiveType, Boolean::class.javaPrimitiveType, Int::class.javaPrimitiveType
//            )
//            val stopMethodTracingMethod: Method = c.getDeclaredMethod("stopMethodTracing")
//            val getMethodTracingModeMethod: Method = c.getDeclaredMethod("getMethodTracingMode")
//            val getRuntimeStatMethod: Method = c.getDeclaredMethod("getRuntimeStat", String::class.java)
//            val getRuntimeStatsMethod: Method = c.getDeclaredMethod("getRuntimeStats")
//
//            val countInstancesOfClassMethod: Method = c.getDeclaredMethod(
//                "countInstancesOfClass",
//                Class::class.java, Boolean::class.javaPrimitiveType
//            )
//
//            val countInstancesOfClassesMethod: Method = c.getDeclaredMethod(
//                "countInstancesOfClasses",
//                Array<Class<*>>::class.java, Boolean::class.javaPrimitiveType
//            )
//
////android 9.0以上才有这个方法
//            val getInstancesOfClassesMethod: Method? = if (android.os.Build.VERSION.SDK_INT >= 28) {
//                c.getDeclaredMethod(
//                    "getInstancesOfClasses",
//                    Array<Class<*>>::class.java, Boolean::class.javaPrimitiveType
//                )
//            } else {
//                null
//            }
//        } catch (e: Exception) {
//            throw RuntimeException(e)
//        }
//    }
//
//    /**
//     * 根据Class获取当前进程全部的实例
//     *
//     * @param clazz 需要查找的Class
//     * @return 当前进程的全部实例。
//     */
//    @TargetApi(28)
//    fun choose(clazz: Class<*>?): ArrayList<Any>? {
//        return choose(clazz, false)
//    }
//
//    /**
//     * 根据Class获取当前进程全部的实例
//     *
//     * @param clazz      需要查找的Class
//     * @param assignable 是否包含子类的实例
//     * @return 当前进程的全部实例。
//     */
//    @TargetApi(28)
//    @Synchronized
//    fun choose(clazz: Class<*>?, assignable: Boolean): ArrayList<Any>? {
//        var resut: ArrayList<Any>? = null
//        try {
//            val instancesOfClasses = getInstancesOfClasses(arrayOf(clazz), assignable)
//            if (instancesOfClasses != null) {
//                resut = ArrayList()
//                for (instancesOfClass in instancesOfClasses) {
//                    resut.addAll(Arrays.asList(*instancesOfClass))
//                }
//            }
//        } catch (e: Throwable) {
//            Log.e(Constants.TAG, "ChooseUtils choose error ", e)
//            e.printStackTrace()
//        }
//        return resut
//    }
//
//    @TargetApi(28)
//    @Throws(Exception::class)
//    private fun getInstancesOfClasses(
//        classes: Array<Class<*>?>,
//        assignable: Boolean
//    ): Array<Array<Any>> {
//        return getInstancesOfClassesMethod!!.invoke(
//            null, *arrayOf<Any>(classes, assignable)
//        ) as Array<Array<Any>>
//    }
//
//    @Throws(Exception::class)
//    fun startMethodTracing(
//        filename: String?, bufferSize: Int, flags: Int,
//        samplingEnabled: Boolean, intervalUs: Int
//    ) {
//        startMethodTracingMethod!!.invoke(
//            null, filename, bufferSize, flags, samplingEnabled,
//            intervalUs
//        )
//    }
//
//    @Throws(Exception::class)
//    fun stopMethodTracing() {
//        stopMethodTracingMethod!!.invoke(null)
//    }
//
//    @get:Throws(Exception::class)
//    val methodTracingMode: Int
//        get() = getMethodTracingModeMethod!!.invoke(null) as Int
//
//    /**
//     * String gc_count = VMDebug.getRuntimeStat("art.gc.gc-count");
//     * String gc_time = VMDebug.getRuntimeStat("art.gc.gc-time");
//     * String bytes_allocated = VMDebug.getRuntimeStat("art.gc.bytes-allocated");
//     * String bytes_freed = VMDebug.getRuntimeStat("art.gc.bytes-freed");
//     * String blocking_gc_count = VMDebug.getRuntimeStat("art.gc.blocking-gc-count");
//     * String blocking_gc_time = VMDebug.getRuntimeStat("art.gc.blocking-gc-time");
//     * String gc_count_rate_histogram = VMDebug.getRuntimeStat("art.gc.gc-count-rate-histogram");
//     * String blocking_gc_count_rate_histogram =VMDebug.getRuntimeStat("art.gc.gc-count-rate-histogram");
//     */
//    @Throws(Exception::class)
//    fun getRuntimeStat(statName: String?): String {
//        return getRuntimeStatMethod!!.invoke(null, statName) as String
//    }
//
//    @get:Throws(Exception::class)
//    val runtimeStats: Map<String, String>
//        /**
//         * 获取当前进程的状态信息
//         */
//        get() = getRuntimeStatsMethod!!.invoke(null) as Map<String, String>
//
//    @Throws(Exception::class)
//    fun countInstancesofClass(c: Class<*>, assignable: Boolean): Long {
//        return countInstancesOfClassMethod!!.invoke(null, *arrayOf<Any>(c, assignable)) as Long
//    }
//
//    @Throws(Exception::class)
//    fun countInstancesofClasses(classes: Array<Class<*>?>, assignable: Boolean): LongArray {
//        return countInstancesOfClassesMethod!!.invoke(
//            null, *arrayOf<Any>(classes, assignable)
//        ) as LongArray
//    }
//}