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
import com.example.akbar.retrofitsample.Adapter.FavoriteTeamAdapter
import com.example.akbar.retrofitsample.Model.TeamsFavorite
import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.View.FavoriteFixtureView
import com.example.akbar.retrofitsample.View.FavoriteTeamVIew
import com.example.akbar.retrofitsample.favDatabase
import com.example.akbar.retrofitsample.presenter.FavoriteTeamPresenter
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FragmentFavoriteTeam : Fragment(), FavoriteTeamVIew {

    lateinit var rvFavoriteTeam : RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var progressBar : ProgressBar
    lateinit var favoriteTeamPresenter: FavoriteTeamPresenter
    lateinit var view : FavoriteFixtureView
    var teamFavorite : ArrayList<TeamsFavorite> = arrayListOf()
    override fun onResume() {
        super.onResume()
        teamFavorite.clear()
        showFavorite()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_fragment_favorite_team, container, false)
        rvFavoriteTeam = v.findViewById(R.id.rv_team_list_fav)
        swipeRefreshLayout = v.findViewById(R.id.sl_favorite_team)
        progressBar = v.findViewById(R.id.pb_loading_teams_fav)
        rvFavoriteTeam.layoutManager = LinearLayoutManager(context)
        favoriteTeamPresenter = FavoriteTeamPresenter(this, context!!)
        favoriteTeamPresenter.getFavoriteTeamItem()

        return v
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = INVISIBLE
    }

    override fun showFavoriteTeamItem(favoriteTeam: ArrayList<TeamsFavorite>) {
        rvFavoriteTeam.adapter = FavoriteTeamAdapter(context!!, teamFavorite){
            hideLoading()
        }
        showFavorite()
        swipeRefreshLayout.setOnRefreshListener {
            teamFavorite.clear()
            showFavorite()
        }
    }
    private fun showFavorite(){
        context?.favDatabase?.use {
            swipeRefreshLayout.isRefreshing = false
            val result = select(TeamsFavorite.TABLE_TEAMSFAV)
            val huhu = result.parseList(classParser<TeamsFavorite>())
            teamFavorite.addAll(huhu)
            rvFavoriteTeam.adapter?.notifyDataSetChanged()
        }
    }

}
