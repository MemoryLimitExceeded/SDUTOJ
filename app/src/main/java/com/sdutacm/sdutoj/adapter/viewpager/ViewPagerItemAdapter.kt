package com.sdutacm.sdutoj.adapter.viewpager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sdutacm.sdutoj.item.entity.common.ListItemEntity
import com.sdutacm.sdutoj.ui.fragment.common.ItemFragment

class ViewPagerItemAdapter(private val fragment: ItemFragment<out ListItemEntity>, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): ItemFragment<out ListItemEntity> {
        return fragment
    }

    override fun getCount(): Int {
        return 1
    }

}