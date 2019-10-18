package com.example.dolang.Activity

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.dolang.Adapter.FragmentSlideAdapter
import com.example.dolang.MenuFragment.Home
import com.example.dolang.MenuFragment.Kategori
import com.example.dolang.MenuFragment.Profile
import com.example.dolang.R
import kotlinx.android.synthetic.main.activity_new_main.*

class NewMainActivity : AppCompatActivity() {

    private var fragments = mutableListOf<Fragment>().apply {
        add(Home())
        add(Kategori())
        add(Profile())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        view_pager.adapter = FragmentSlideAdapter(fragments, supportFragmentManager)
        equal_navigation_bar.setNavigationChangeListener { _ , position ->
            equal_navigation_bar.setCurrentActiveItem(position)
            view_pager.setCurrentItem(position, true)
        }
        view_pager.addOnPageChangeListener(object  : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                equal_navigation_bar.setCurrentActiveItem(position)
            }
        })
        equal_navigation_bar.setCurrentActiveItem(0)
    }

    private fun toast(mess : String?) = Toast.makeText(this, mess, Toast.LENGTH_LONG).show()

    override fun onResume() {
        super.onResume()
        requestedOrientation= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

}
