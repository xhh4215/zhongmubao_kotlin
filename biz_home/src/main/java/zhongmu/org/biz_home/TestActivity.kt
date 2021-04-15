package zhongmu.org.biz_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import zhongmu.org.lib_common.route.RouteFlag

@Route(path = "/test/activity", extras = RouteFlag.FLAG_LOGIN)
class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}