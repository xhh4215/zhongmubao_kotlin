package zhongmu.org.lib_common.http

import zhongmu.org.service_login.LoginServiceProvider
import zhongmu.org.zhongmu_lib.log.common.SmartLog
import zhongmu.org.zhongmu_lib.restful.HttpRequest
import zhongmu.org.zhongmu_lib.restful.interceptor.HttpInterceptor

class BizInterceptor : HttpInterceptor {
    override fun intercept(chain: HttpInterceptor.Chain): Boolean {
        val request = chain.request()
        val response = chain.response()
        if (chain.isRequestPeriod) {
            val boardingPass = LoginServiceProvider.getBoardingPass() ?: ""
            request.addHeader("board-pass", boardingPass)
            request.addHeader("auth-token", "MTU5Mjg1MDg3NDcwNw==")

        } else if (response != null) {
            var outputBuilder = StringBuilder()
            val httpMethod: String =
                if (request.httpMethod == HttpRequest.METHOD.GET) "GET" else "POST"
            val requestUrl: String = request.endPointUrl()
            outputBuilder.append("\n$requestUrl==>$httpMethod\n")
            if (request.headers != null) {
                outputBuilder.append("【headers\n")
                request.headers!!.forEach(action = {
                    outputBuilder.append(it.key + ":" + it.value)
                    outputBuilder.append("\n")
                })
                outputBuilder.append("headers】\n")
            }
            if (request.parameters != null && request.parameters!!.isNotEmpty()) {
                outputBuilder.append("【parameters==>\n")
                request.parameters!!.forEach(action = {
                    outputBuilder.append(it.key + ":" + it.value + "\n")
                })
                outputBuilder.append("parameters】\n")
            }
            outputBuilder.append("【response==>\n")
            outputBuilder.append(response.rawData + "\n")
            outputBuilder.append("response】\n")
            SmartLog.dt("BizInterceptor Http:", outputBuilder.toString())

        }
        return false
    }

}