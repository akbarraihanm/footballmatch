package com.example.akbar.retrofitsample

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import com.example.akbar.retrofitsample.Fragment.*
import com.example.akbar.retrofitsample.View.LastFixtureView
import com.example.akbar.retrofitsample.presenter.LastFixturePresenter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    @SuppressLint("ResourceType")
    private var menuItem: Menu? = null

    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        progressBar = findViewById(R.id.pb_loading_home)

        bottom_navigation.isClickable=false

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fixture -> {
                    showLoading()
                    loadFragmentFixture(savedInstanceState)
                    hideLoading()
                }
                R.id.teams -> {
                    showLoading()
                    loadFragmentListTeam(savedInstanceState)
                    hideLoading()
                }
                R.id.favorites -> {
                    showLoading()
                    loadFragmentFavorite(savedInstanceState)
                    hideLoading()
                }
            }
            true
        }
        bottom_navigation.selectedItemId = R.id.fixture

    }
    fun showLoading(){
        progressBar.visibility = VISIBLE
    }
    fun hideLoading(){
        progressBar.visibility = INVISIBLE
    }
    private fun loadFragmentFixture(savedInstanceState: Bundle?){
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.cobacontainer, FragmentFixture(), FragmentFixture::class.java.simpleName)
                .commit()
        }
    }

    fun loadFragmentFavorite(savedInstanceState: Bundle?){
            if(savedInstanceState == null) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.cobacontainer, FragmentFavorite(), FragmentFavorite::class.java.simpleName)
                    .commit()
            }
    }

    fun loadFragmentListTeam(savedInstanceState: Bundle?){
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
