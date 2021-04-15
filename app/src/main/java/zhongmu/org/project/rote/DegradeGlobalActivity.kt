package zhongmu.org.project.rote

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.activity_degrade_global.*
import zhongmu.org.lib_common.route.HiRoute
import zhongmu.org.project.R

/***
 * @author 栾桂明
 * @date  2021年4月13日
 * @desc  这是一个全局统一的错误页
 */
@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity() {
    @JvmField
    @Autowired
    var degrade_title: String? = null

    @JvmField
    @Autowired
    var degrade_desc: String? = null

    @JvmField
    @Autowired
    var degrade_action: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HiRoute.inject(this)
        setContentView(R.layout.activity_degrade_global)
        empty_view.setIcon(R.string.app_name)
        if (degrade_title != null) {
            empty_view.setTitle(degrade_title!!)
        }
        if (degrade_desc != null) {
            empty_view.setDesc(degrade_desc!!)
        }
        if (degrade_action != null) {
            empty_view.setHelpAction(listener = View.OnClickListener {
                var intent = Intent(Intent.ACTION_VIEW, Uri.parse(degrade_action))
                startActivity(intent)
            })
        }


    }
}