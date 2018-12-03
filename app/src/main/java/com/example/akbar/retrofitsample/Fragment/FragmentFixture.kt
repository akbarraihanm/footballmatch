package com.example.akbar.retrofitsample.Fragment


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import com.example.akbar.retrofitsample.Adapter.LastFixtureAdapter
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.Model.FixtureResponse
import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.SearchEventActivity
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentFixture : Fragment() {
    lateinit var viewPager3 : ViewPager
    lateinit var tabs3 : TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment_fixture, container, false)
        viewPager3 = view.findViewById(R.id.viewPager2)
        tabs3 = view.findViewById(R.id.tabs2)

        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(FragmentLastFixture(), " Last Fixture ")
        adapter.addFragment(FragmentNextFixture(), " Next Fixture ")
        viewPager3.adapter = adapter
        tabs3.setupWithViewPager(viewPager3)

        return view
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
