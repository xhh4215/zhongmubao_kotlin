package zhongmu.org.lib_common.http

import zhongmu.org.lib_common.utils.SPUtil
import zhongmu.org.zhongmu_lib.restful.HttpRestful

object ApiFactory {
    const val KEY_DEGRADE_HTTP = "degrade_http"
    const val HTTPS_BASE_URL = "https://api.devio.org/as/"
    const val HTTP_BASE_URL = "http://api.devio.org/as/"
    private val degrade2Http = SPUtil.getBoolean(KEY_DEGRADE_HTTP)
    private val baseUrl = if (degrade2Http) HTTP_BASE_URL else HTTPS_BASE_URL
    private val hiRestful = HttpRestful(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        hiRestful.addInterceptor(BizInterceptor())
        hiRestful.addInterceptor(HttpCodeInterceptor())
        SPUtil.putBoolean(KEY_DEGRADE_HTTP, false)

    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}