package com.example.akbar.retrofitsample

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.akbar.retrofitsample.Fragment.FragmentFavoriteMatch
import com.example.akbar.retrofitsample.Fragment.FragmentLastFixture
import com.example.akbar.retrofitsample.Fragment.FragmentNextFixture
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    internal var con: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FragmentLastFixture(), " Last Fixture ")
        adapter.addFragment(FragmentNextFixture(), " Next Fixture ")
        adapter.addFragment(FragmentFavoriteMatch(), " Favorite Fixture ")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    class ViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}
