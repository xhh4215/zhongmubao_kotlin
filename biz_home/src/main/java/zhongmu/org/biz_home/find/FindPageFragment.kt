package zhongmu.org.biz_home.find

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import zhongmu.org.biz_home.R
import zhongmu.org.lib_common.route.RouteFlag
import zhongmu.org.lib_common.ui.component.BaseFragment

@Route(path = "/find/main", extras = RouteFlag.FLAG_LOGIN)
class FindPageFragment : BaseFragment() {
    private lateinit var viewModel: FindPageViewModel
    override fun getLayoutId(): Int {
        return R.layout.find_page_fragment
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FindPageViewModel::class.java)

    }

}