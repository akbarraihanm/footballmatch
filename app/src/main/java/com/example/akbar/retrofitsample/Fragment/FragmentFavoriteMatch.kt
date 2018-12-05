package com.example.akbar.retrofitsample.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.akbar.retrofitsample.Adapter.FavoriteFixtureAdapter
import com.example.akbar.retrofitsample.Model.Favorite
import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.View.FavoriteFixtureView
import com.example.akbar.retrofitsample.database
import com.example.akbar.retrofitsample.presenter.FavoriteFixturePresenter
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FragmentFavoriteMatch : Fragment(), FavoriteFixtureView {

    private lateinit var rvFavoriteFixture : RecyclerView
    private var lastFixture: ArrayList<Favorite> = arrayListOf()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar
    lateinit var favoriteFixturePresenter: FavoriteFixturePresenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_favorite_match, container, false)
    }
    override fun onResume() {
        super.onResume()
        lastFixture.clear()
        showFavorite()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar = view.findViewById(R.id.pb_loading)
        swipeRefresh = view.findViewById(R.id.sl_favorite)
        rvFavoriteFixture = view.findViewById(R.id.rv_favoriteFixture)
        rvFavoriteFixture.layoutManager = LinearLayoutManager(context)
        favoriteFixturePresenter = FavoriteFixturePresenter(this, context!!)
        favoriteFixturePresenter.getFavoriteFixtureItem()
    }
    override fun showFavoriteFixtureItem(favoriteFixture: ArrayList<Favorite>) {
        rvFavoriteFixture.adapter = FavoriteFixtureAdapter(context!!, lastFixture){
            hideLoading()
        }
        showFavorite()
        swipeRefresh.setOnRefreshListener {
            lastFixture.clear()
            showFavorite()
        }
    }
    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<Favorite>())
            lastFixture.addAll(favorite)
            rvFavoriteFixture.adapter?.notifyDataSetChanged()
        }
    }
    override fun showLoading(){
        progressBar.visibility = VISIBLE
    }
    override fun hideLoading(){
        progressBar.visibility = INVISIBLE
    }
}
