package com.example.akbar.retrofitsample.Fragment


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import android.view.View.INVISIBLE

import com.example.akbar.retrofitsample.R

class FragmentFavorite : Fragment() {
    lateinit var tabsFavorite : TabLayout
    lateinit var viewPagerFavorite : ViewPager
    lateinit var svFavorite : SearchView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        val view =  inflater.inflate(R.layout.fragment_fragment_favorite, container, false)

        tabsFavorite = view.findViewById(R.id.tabs_favorite)
        viewPagerFavorite = view.findViewById(R.id.viewPager_favorite)

        val adapter = ViewPagerFavoriteAdapter(childFragmentManager)
        adapter.addFragment(FragmentFavoriteMatch(), "Fixtures")
        adapter.addFragment(FragmentFavoriteTeam(), "Teams")
        viewPagerFavorite.adapter = adapter
        tabsFavorite.setupWithViewPager(viewPagerFavorite)

        return view
    }

    class ViewPagerFavoriteAdapter(manager : FragmentManager) : FragmentPagerAdapter(manager){
        private val fragmentFavList : MutableList<Fragment> = ArrayList()
        private val titleFavList : MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentFavList[position]
        }

        override fun getCount(): Int {
            return fragmentFavList.size
        }

        fun addFragment(fragment: Fragment, title : String){
            fragmentFavList.add(fragment)
            titleFavList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleFavList[position]
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        svFavorite = searchItem.actionView as SearchView
        searchItem.isVisible = false
    }

}
