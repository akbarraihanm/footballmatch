package com.example.akbar.retrofitsample.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.akbar.retrofitsample.Adapter.PlayerAdapter
import com.example.akbar.retrofitsample.DetailTeamActivity.Companion.string_id2
import com.example.akbar.retrofitsample.Model.Player
import com.example.akbar.retrofitsample.Model.PlayerResponse

import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import com.example.akbar.retrofitsample.View.PlayerView
import com.example.akbar.retrofitsample.presenter.PlayerPresenter
import retrofit2.Call

class FragmentPlayerTeam : Fragment(), PlayerView {

    lateinit var progressBar: ProgressBar
    lateinit var rvPlayerList : RecyclerView
    lateinit var view : PlayerView
    lateinit var v : View
    lateinit var playerPresenter : PlayerPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v =  inflater.inflate(R.layout.fragment_fragment_player_team, container, false)
        rvPlayerList = v.findViewById(R.id.rv_player_list)
        progressBar = v.findViewById(R.id.pb_loading_players)
        rvPlayerList.layoutManager = LinearLayoutManager(context)

        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<PlayerResponse> = apiInterface.getPlayerItem(string_id2)

        playerPresenter = PlayerPresenter(call, this, context!!)
        playerPresenter.getListPlayerItem()

        return  v
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = INVISIBLE
    }

    override fun showPlayerItem(listPlayer: ArrayList<Player>) {
        rvPlayerList.adapter = PlayerAdapter(context!!, listPlayer){
        }
    }

}
