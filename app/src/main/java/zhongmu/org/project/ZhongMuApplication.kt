package zhongmu.org.project

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
 import com.google.gson.Gson
import zhongmu.org.lib_common.manager.ActivityManager
import zhongmu.org.zhongmu_lib.log.common.SmartLogConfig
import zhongmu.org.zhongmu_lib.log.common.SmartLogManager

class ZhongMuApplication : Application() {
    private val APPLICATION_TAG = "global_log"

    override fun onCreate() {
        super.onCreate()
        ActivityManager.instance.init(this)
        initLog()
        initArouter()
    }

    /****
     * 初始化全局的日志打印
     */
    private fun initLog() {
        SmartLogManager.init(object : SmartLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return object : JsonParser {
                    override fun toJson(src: Any): String {
                        return Gson().toJson(src)
                    }
                }
            }

            /***
             * 全局的打印tag
             */
            override fun getGlobalTag(): String {
                return APPLICATION_TAG
            }

            /***
             * 是否开启日志打印
             */
            override fun enable(): Boolean {
                return true
            }
        })
    }


    /***
     * 初始化阿里巴巴的路由导航框架
     */
    private fun initArouter() {
        // 阿里巴巴的路由导航框架的初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}