package zhongmu.org.biz_login

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import zhongmu.org.biz_login.api.AccountApi
import zhongmu.org.biz_login.login.LoginActivity
import zhongmu.org.lib_common.http.ApiFactory
import zhongmu.org.lib_common.utils.SPUtil
import zhongmu.org.service_login.UserProfile
import zhongmu.org.zhongmu_lib.cache.HiStorage
import zhongmu.org.zhongmu_lib.executor.HiExecutor
import zhongmu.org.zhongmu_lib.restful.HttpResponse
import zhongmu.org.zhongmu_lib.restful.callback.HttpCallBack
import zhongmu.org.zhongmu_lib.utils.AppGlobals
import zhongmu.org.zhongmu_lib.utils.MainHandler

object AccountManager {
    private val lock = Any()
    private var userProfile: UserProfile? = null
    private var boardingPass: String? = null

    //获取缓存的用户信息的KEY
    private const val KEY_USER_PROFILE = "user_profile"
    private const val KEY_BOARDING_PASS = "boarding_pass"
    private val loginLiveData = ObserverLiveData<Boolean>()
    private val profileLiveData = ObserverLiveData<UserProfile>()

    @Volatile
    private var isFetching = false

    init {
        HiExecutor.execute(runnable = Runnable {
            val local = HiStorage.getCache<UserProfile?>(KEY_USER_PROFILE)
            synchronized(lock) {
                if (userProfile == null && local == null) {
                    userProfile = local
                }
            }
        })
    }

    fun login(context: Context? = AppGlobals.get(), observer: Observer<Boolean>) {
        if (context is LifecycleOwner) {
            loginLiveData.observe(context, observer)
        } else {
            loginLiveData.observeForever(observer)
        }
        val intent = Intent(context, LoginActivity::class.java)
        if (context is Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        if (context == null) {
            throw IllegalStateException("context must not be null.")
        }
        context.startActivity(intent)
    }

    internal fun loginSuccess(boardingSuccess: String) {
        SPUtil.putString(KEY_BOARDING_PASS, boardingPass)
        this.boardingPass = boardingPass
        loginLiveData.value = true
    }

    fun getBoardingPass(): String? {
        if (TextUtils.isEmpty(boardingPass)) {
            boardingPass = SPUtil.getString(KEY_BOARDING_PASS)
        }
        return boardingPass

    }

    fun isLogin(): Boolean {
        return !TextUtils.isEmpty(getBoardingPass())
    }

    fun getUserProfile(
        lifecycleOwner: LifecycleOwner?,
        observer: Observer<UserProfile?>, onlyCache: Boolean = false
    ) {
        if (lifecycleOwner == null) {
            profileLiveData.observeForever(observer)
        } else {
            profileLiveData.observe(lifecycleOwner, observer)
        }
        if (isFetching) return
        isFetching = true
        if (onlyCache) {
            synchronized(lock) {
                if (userProfile != null) {
                    MainHandler.post(Runnable {
                        this.profileLiveData.value = userProfile
                    })
                    return
                }
            }
        }
        ApiFactory.create(AccountApi::class.java).profile()
            .enqueue(object : HttpCallBack<UserProfile> {
                override fun onSuccess(response: HttpResponse<UserProfile>) {
                    if (response.data != null && response.successful()) {
                        userProfile = response.data
                        HiExecutor.execute(runnable = Runnable {
                            HiStorage.saveCache(KEY_USER_PROFILE, userProfile)
                            isFetching = false
                        })
                        profileLiveData.value = userProfile

                    } else {
                        profileLiveData.value = null
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    isFetching = false
                    profileLiveData.value = null
                }


            })

    }

    private class ObserverLiveData<T> : MutableLiveData<T>() {
        private val observers = arrayListOf<Observer<in T>>()
        override fun observeForever(observer: Observer<in T>) {
            super.observeForever(observer)
            if (!observers.contains(observer)) {
                observers.add(observer)
            }
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            super.observe(owner, observer)
            if (!observers.contains(observer)) {
                observers.add(observer)
            }
        }

        fun removeAllObservers() {
            for (observer in observers) {
                removeObserver(observer)
            }
            observers.clear()

        }
    }

    internal fun clearObservers() {

    }
}