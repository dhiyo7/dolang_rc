package com.example.dolang.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class FragmentSlideAdapter(private var fragments : List<Fragment>, fm : FragmentManager) : FragmentStatePagerAdapter(fm){
    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount() = fragments.size
}