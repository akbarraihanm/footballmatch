package com.example.akbar.retrofitsample.presenter

import android.content.Context
import android.widget.Toast
import com.example.akbar.retrofitsample.Model.DetailTeam
import com.example.akbar.retrofitsample.Model.DetailTeamResponse
import com.example.akbar.retrofitsample.View.OverviewTeamView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OverviewTeamPresenter(private val call : Call<DetailTeamResponse>,
                            private val view : OverviewTeamView,
                            private val context: Context){
    fun getOverviewTeamItem(){
        var listTeam : ArrayList<DetailTeam>
        call.enqueue(object : Callback<DetailTeamResponse> {
            override fun onFailure(call: Call<DetailTeamResponse>, t: Throwable) {
                Toast.makeText(context,"Gagal fetch list team", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DetailTeamResponse>, response: Response<DetailTeamResponse>) {
                listTeam = response.body()!!.teams
                view.showLoading()
                view.showOverviewTeamItem(listTeam)
                view.hideLoading()
            }
        })
    }
}