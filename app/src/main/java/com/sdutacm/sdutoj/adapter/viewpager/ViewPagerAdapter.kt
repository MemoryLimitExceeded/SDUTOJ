package com.sdutacm.sdutoj.adapter.viewpager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sdutacm.sdutoj.mvp.base.BaseFragment

class ViewPagerAdapter(
    fm: FragmentManager,
    private val titles: Array<String>,
    private val fragments: ArrayList<BaseFragment>
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): BaseFragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

}