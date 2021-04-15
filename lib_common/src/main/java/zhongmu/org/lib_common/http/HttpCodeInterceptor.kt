package zhongmu.org.lib_common.http

import zhongmu.org.zhongmu_lib.restful.HttpResponse
import zhongmu.org.zhongmu_lib.restful.interceptor.HttpInterceptor

class HttpCodeInterceptor : HttpInterceptor {

    override fun intercept(chain: HttpInterceptor.Chain): Boolean {
        val response = chain.response()
        if (chain.isRequestPeriod && response != null) {
            when (response.code) {
                HttpResponse.RC_NEED_LOGIN -> {
                    //路由到登录
                }

            }
        }

        return false
    }

}