package zhongmu.org.biz_login.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import zhongmu.org.biz_login.AccountManager
import zhongmu.org.biz_login.R
import zhongmu.org.biz_login.databinding.ActivityLoginBinding
import zhongmu.org.lib_common.ui.component.BaseActivity
import zhongmu.org.zhongmu_lib.utils.ResUtil
import zhongmu.org.zhongmu_lib.utils.StatusBarUtil

@Route(path = "/account/login")
class LoginActivity : BaseActivity() {
    private lateinit var loginBinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private val REQUEST_CODE_REGISTRATION = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this, true, translucent = false)
        loginBinding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        setNavBar()
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        loginBinding.actionLogin.setOnClickListener { goLogin() }
    }

    /***
     * 设置顶部的标题栏
     */
    private fun setNavBar() {
        loginBinding.mainNavBar.setTitle(ResUtil.getString(R.string.title_login))
        loginBinding.mainNavBar.setNavListener(View.OnClickListener { onBackPressed() })
    }

    /***
     * 登录
     */
    private fun goLogin() {
        val name = loginBinding.inputItemUsername.getEditText().text
        val password = loginBinding.inputItemPassword.getEditText().text
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            return
        }
        loginViewModel.login(name.toString(), password.toString())
            .observe(this, Observer {
                AccountManager.loginSuccess(it)
                setResult(Activity.RESULT_OK, Intent())
                finish()
            })
    }


}