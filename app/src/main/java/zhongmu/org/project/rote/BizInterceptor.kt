package zhongmu.org.project.rote

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import zhongmu.org.biz_home.MainActivity
import zhongmu.org.lib_common.route.RouteFlag
import zhongmu.org.service_login.LoginServiceProvider
import zhongmu.org.zhongmu_lib.utils.MainHandler

@Interceptor(name = "biz_interceptor", priority = 9)
class BizInterceptor : IInterceptor {
    private var context: Context? = null


    /***
     * 对Aroute路由的操作的拦截 内部的具体的操作
     */
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        val flag = postcard!!.extra
        if (flag and (RouteFlag.FLAG_LOGIN) != 0) {
            loginIfNecessary(postcard, callback)
        } else {
            callback!!.onContinue(postcard)
        }
    }

    override fun init(context: Context?) {
        this.context = context

    }

    /***
     *  如果需要登录的时候的操作
     */
    private fun loginIfNecessary(postcard: Postcard?, callback: InterceptorCallback?) {
//        MainHandler.handler.post {
//            if (LoginServiceProvider.isLogin()) {
//                callback?.onContinue(postcard)
//            } else {
//                Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
//                val observer = Observer<Boolean> {
//                    callback?.onContinue(postcard)
//                }
//                LoginServiceProvider.login(context, observer)
//            }
//        }
        MainHandler.post(Runnable {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
        })

    }

}