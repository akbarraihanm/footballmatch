package com.example.akbar.retrofitsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.example.akbar.retrofitsample.Fragment.FragmentFavorite
import com.example.akbar.retrofitsample.Fragment.FragmentFavoriteMatch
import com.example.akbar.retrofitsample.Fragment.FragmentFixture
import com.example.akbar.retrofitsample.Fragment.FragmentListTeam
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    private var menuItem: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fixture -> {
                    loadFragmentFixture(savedInstanceState)
                }
                R.id.teams -> {
                    loadFragmentListTeam(savedInstanceState)
                }
                R.id.favorites -> {
                    loadFragmentFavorite(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.fixture
    }
    private fun loadFragmentFixture(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.cobacontainer, FragmentFixture(), FragmentFixture::class.java.simpleName)
                .commit()
        }
    }

    private fun loadFragmentFavorite(savedInstanceState: Bundle?){
            if(savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.cobacontainer, FragmentFavorite(), FragmentFavorite::class.java.simpleName)
                    .commit()
            }
    }

    private fun loadFragmentListTeam(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.cobacontainer, FragmentListTeam(), FragmentListTeam::class.java.simpleName)
                .commit()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        menuItem = menu

        return true
    }

}
