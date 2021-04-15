package zhongmu.org.lib_common.tab;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import java.util.List;

import zhongmu.org.lib_ui.tab.bottom.TabBottomInfo;

public class HiTabViewAdapter {
    private List<TabBottomInfo<?>> mInfoList;
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    public HiTabViewAdapter(FragmentManager fragmentManager, List<TabBottomInfo<?>> infoList) {
        this.mInfoList = infoList;
        this.mFragmentManager = fragmentManager;
    }

    /***
     * 实例化以及显示指定位置的fragment
     * @param container
     * @param position
     */
    public void instantiateItem(View container, int position) {
        FragmentTransaction mCurTransaction = mFragmentManager.beginTransaction();
        if (mCurrentFragment != null) {
            mCurTransaction.hide(mCurrentFragment);
        }
        String name = container.getId() + ":" + position;
        Fragment fragment = mFragmentManager.findFragmentByTag(name);
        if (fragment != null) {
            mCurTransaction.show(fragment);
        } else {
            fragment = getItem(position);

            if (!fragment.isAdded()) {
                mCurTransaction.add(container.getId(), fragment, name);
            }
        }
        mCurrentFragment = fragment;
        mCurTransaction.commitAllowingStateLoss();
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public int getCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }

    public Fragment getItem(int position) {
        try {
            return mInfoList.get(position).fragment.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
