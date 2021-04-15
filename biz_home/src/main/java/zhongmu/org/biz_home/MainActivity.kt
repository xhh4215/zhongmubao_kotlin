package zhongmu.org.biz_home

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import zhongmu.org.biz_home.databinding.ActivityMainBinding
import zhongmu.org.biz_home.find.FindPageFragment
import zhongmu.org.biz_home.home.HomePageFragment
import zhongmu.org.biz_home.mine.MinePageFragment
import zhongmu.org.biz_home.store.StorePageFragment
import zhongmu.org.lib_common.tab.HiTabViewAdapter
import zhongmu.org.lib_common.ui.component.BaseActivity
import zhongmu.org.lib_ui.tab.bottom.TabBottomInfo
import zhongmu.org.lib_ui.tab.common.ITabLayout
import zhongmu.org.zhongmu_lib.utils.ResUtil
import zhongmu.org.zhongmu_lib.utils.StatusBarUtil

class MainActivity : BaseActivity() {
    private lateinit var mainBinding: ActivityMainBinding
    private val SAVED_CURRENT_ID = "SAVED_CURRENT_ID"

    // 底部按钮的index数值
    private var currentIndex = 0

    //存储item的数据的集合
    private var infoList = arrayListOf<TabBottomInfo<*>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.setStatusBar(this, true, translucent = false)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState[SAVED_CURRENT_ID] as Int
        }
        initBottomTab()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVED_CURRENT_ID, currentIndex)
    }

    /***
     * 初始化主界面底部的切换界面的按钮
     */
    private fun initBottomTab() {
        mainBinding.mainBottomContainer.alpha = 0.85F
        val defaultColor = ResUtil.getColor(R.color.tabBottomDefaultColor)
        val tintColor = ResUtil.getColor(R.color.tabBottomTintColor)

        val homeFragment = HomePageFragment::class.java
        val homeTab = TabBottomInfo<Int>(
            "首页",
            "fonts/iconfont.ttf",
            ResUtil.getString(R.string.if_home),
            null,
            defaultColor,
            tintColor
        )
        homeTab.fragment = homeFragment

        val findFragment = FindPageFragment::class.java

        val findTab = TabBottomInfo<Int>(
            "发现",
            "fonts/iconfont.ttf",
            ResUtil.getString(R.string.if_favorite),
            null,
            defaultColor,
            tintColor
        )
        findTab.fragment = findFragment

        val storeFragment = StorePageFragment::class.java

        val storeTab = TabBottomInfo<Int>(
            "商店",
            "fonts/iconfont.ttf",
            ResUtil.getString(R.string.if_recommend),
            null,
            defaultColor,
            tintColor
        )
        storeTab.fragment = storeFragment

        val mineFragment = MinePageFragment::class.java
        val mineTab = TabBottomInfo<Int>(
            "我的",
            "fonts/iconfont.ttf",
            ResUtil.getString(R.string.if_profile),
            null,
            defaultColor,
            tintColor
        )
        mineTab.fragment = mineFragment
        infoList.add(homeTab)
        infoList.add(findTab)
        infoList.add(storeTab)
        infoList.add(mineTab)
        mainBinding.mainBottomContainer.inflateInfo(infoList)
        initFragmentTabView()
        mainBinding.mainBottomContainer.addTabSelectedChangeListener(object :
            ITabLayout.OnTabSelectedListener<TabBottomInfo<*>> {
            override fun onTabSelectedChange(
                index: Int,
                prevInfo: TabBottomInfo<*>?,
                nextInfo: TabBottomInfo<*>
            ) {
                mainBinding.mainFragment.currentItem = index
                currentIndex = index
            }

        })
        mainBinding.mainBottomContainer.defaultSelected(infoList[currentIndex])
    }

    /***
     * 初始化点击底部按钮对应的Fragment
     */
    private fun initFragmentTabView() {
        val adapter = HiTabViewAdapter(supportFragmentManager, infoList)
        mainBinding.mainFragment.adapter = adapter

    }
}