package zhongmu.org.lib_common.ui.component

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity :AppCompatActivity(),BaseActionInterface{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}