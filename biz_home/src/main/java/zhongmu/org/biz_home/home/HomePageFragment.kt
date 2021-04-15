package zhongmu.org.biz_home.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.home_page_fragment.view.*
import zhongmu.org.biz_home.R
import zhongmu.org.biz_home.databinding.HomePageFragmentBinding
import zhongmu.org.lib_common.route.HiRoute
import zhongmu.org.lib_common.ui.component.BaseFragment
import zhongmu.org.zhongmu_lib.utils.MainHandler

class HomePageFragment : BaseFragment() {
     override fun getLayoutId(): Int {
        return R.layout.home_page_fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutView.toLogin.setOnClickListener {
            ARouter.getInstance().build("/test/activity").navigation()
        }
    }


}