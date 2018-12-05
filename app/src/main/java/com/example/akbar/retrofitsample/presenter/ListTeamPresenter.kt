package com.example.akbar.retrofitsample.presenter

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.akbar.retrofitsample.Model.DetailTeam
import com.example.akbar.retrofitsample.Model.DetailTeamResponse
import com.example.akbar.retrofitsample.View.ListTeamView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class ListTeamPresenter(private val call: Call<DetailTeamResponse>,
                        private val listTeamView : ListTeamView,
                        private val context: Context){
    fun cancelteam(){
        call.cancel()
    }
    fun getListTeamItem(){
        var listTeam : ArrayList<DetailTeam>
            call.enqueue(object : Callback<DetailTeamResponse>{
                override fun onFailure(call: Call<DetailTeamResponse>, t: Throwable) {
                    Toast.makeText(context,"Gagal fetch list team", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<DetailTeamResponse>, response: Response<DetailTeamResponse>) {
                    listTeam = response.body()!!.teams
                    listTeamView.showLoading()
                    try {
                        listTeamView.showListTeamItem(listTeam)
                    }catch (e: Exception){
                        Log.d(ContentValues.TAG, "Pindah Fragment")
                    }

                    listTeamView.hideLoading()
                }
            })
    }
}