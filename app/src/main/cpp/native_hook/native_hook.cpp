//
// Created by thouger on 2023/4/19.
//


#include "native_hook.h"

static HookFunType hook_func = nullptr;

int (*backup)();

int fake() {
    return backup() + 1;
}

FILE *(*backup_fopen)(const char *filename, const char *mode);

FILE *fake_fopen(const char *filename, const char *mode) {
    LOGD("fopen filename: %s", filename);
    LOGD("fopen mode: %s", mode);
    if (strstr(filename, "banned")) return nullptr;
    return backup_fopen(filename, mode);
}

jclass (*backup_FindClass)(JNIEnv *env, const char *name);
jclass fake_FindClass(JNIEnv *env, const char *name)
{
    LOGD("FindClass: %s", name);
    if(!strcmp(name, "dalvik/system/BaseDexClassLoader"))
        return nullptr;
    LOGD("find dalvik/system/BaseDexClassLoader class");
    return backup_FindClass(env, name);
}

void on_library_loaded(const char *name, void *handle) {
    // hooks on `libtarget.so`
    LOGD("on_library_loaded: %s", name);
    if (std::string(name).ends_with("libtest.so")) {
        LOGD("hooking libtest.so");
        void *target = dlsym(handle, "helloFromJNI");
        LOGD("libtest.so target: %p", target);
        if (target==nullptr) {
            LOGD("libtest.so target is null");
            return;
        }
        hook_func(target, (void *) fake, (void **) &backup);
    }
}

extern "C" [[gnu::visibility("default")]] [[gnu::used]]
jint JNI_OnLoad(JavaVM *jvm, void*) {
    LOGD("JNI_OnLoad11111111111111111111");
    JNIEnv *env = nullptr;
    jvm->GetEnv((void **)&env, JNI_VERSION_1_6);
    hook_func((void *)env->functions->FindClass, (void *)fake_FindClass, (void **)&backup_FindClass);
    return JNI_VERSION_1_6;
}

extern "C" [[gnu::visibility("default")]] [[gnu::used]]
NativeOnModuleLoaded native_init(const NativeAPIEntries *entries) {
    hook_func = entries->hook_func;
    LOGD("native_init");
    // system hooks
    hook_func((void*) fopen, (void*) fake_fopen, (void**) &backup_fopen);
    return on_library_loaded;
}