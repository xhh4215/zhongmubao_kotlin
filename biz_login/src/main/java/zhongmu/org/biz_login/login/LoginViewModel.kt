package zhongmu.org.biz_login.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import zhongmu.org.biz_login.api.AccountApi
import zhongmu.org.lib_common.http.ApiFactory
import zhongmu.org.zhongmu_lib.restful.HttpResponse
import zhongmu.org.zhongmu_lib.restful.callback.HttpCallBack

class LoginViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    fun login(username: String, password: String): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val meCache = savedStateHandle.get<String>("login_key")
        if (meCache != null) {
            liveData.postValue(meCache)
            return liveData
        }
        ApiFactory.create(AccountApi::class.java).login(username, password)
            .enqueue(object : HttpCallBack<String> {
                override fun onSuccess(response: HttpResponse<String>) {
                    val data = response.data
                    if (response.successful() && data != null) {
                        liveData.value = data
                        savedStateHandle.set("login_key", data)

                    }
                }

                override fun onFailed(throwable: Throwable) {
                    //todo  登录失败
                }
            })
        return liveData
    }
}