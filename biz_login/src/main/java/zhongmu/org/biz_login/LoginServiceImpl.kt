package zhongmu.org.biz_login

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.template.IProvider
import zhongmu.org.service_login.ILoginService
import zhongmu.org.service_login.UserProfile

@Route(path = "/login/service")
class LoginServiceImpl :ILoginService,IProvider{
    override fun login(context: Context?, observer: Observer<Boolean>) {
        AccountManager.login(context,observer)
    }

    override fun isLogin(): Boolean {
        return AccountManager.isLogin()
    }

    override fun getUserProfile(
        lifecycleOwner: LifecycleOwner?,
        observer: Observer<UserProfile?>,
        onlyCache: Boolean
    ) {
         AccountManager.getUserProfile(lifecycleOwner,observer,onlyCache)
    }

    override fun getBoardingPass(): String? {
       return AccountManager.getBoardingPass()
    }

    override fun init(context: Context?) {
     }

}