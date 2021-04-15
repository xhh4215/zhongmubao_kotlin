package zhongmu.org.project.rote

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import zhongmu.org.lib_common.route.HiRoute


/**
 * 全局降级服务,当路由的时候，目标页不存在，此时重定向到统一错误页
 */
@Route(path = "/degrade/global/service")
class DegradeServiceImpl : DegradeService {
    /***
     * 路由出错的时候的处理逻辑
     */
    override fun onLost(context: Context?, postcard: Postcard?) {
        HiRoute.startActivity(null, destination = "/degrade/global/activity")
    }

    override fun init(context: Context?) {

    }

}