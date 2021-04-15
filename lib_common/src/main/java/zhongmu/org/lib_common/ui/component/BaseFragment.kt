package zhongmu.org.lib_common.ui.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/***
 * @author 栾桂明
 * @date 2021年4月14日
 * @desc 所有Fragment的基础功能的封装
 */
abstract class BaseFragment : Fragment() {
    protected lateinit var layoutView: View
    abstract fun getLayoutId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutView = inflater.inflate(getLayoutId(), container, false)
        return layoutView
    }

    /***
     * 检查宿主是不是还活着
     * @return
     */
    fun isAlive(): Boolean {
        return !(isRemoving || isDetached || activity == null)
    }
}