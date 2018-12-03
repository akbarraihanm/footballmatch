package com.example.akbar.retrofitsample.presenter

import android.content.Context
import android.widget.Toast
import com.example.akbar.retrofitsample.Model.Player
import com.example.akbar.retrofitsample.Model.PlayerResponse
import com.example.akbar.retrofitsample.View.PlayerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerPresenter(private val call: Call<PlayerResponse>,
                      private val playerView: PlayerView,
                      private val context: Context){
    fun getListPlayerItem(){
        var listPlayer : ArrayList<Player>
        call.enqueue(object : Callback<PlayerResponse>{
            override fun onFailure(call: Call<PlayerResponse>, t: Throwable) {
                Toast.makeText(context,"Gagal fetch list team", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<PlayerResponse>, response: Response<PlayerResponse>) {
                listPlayer = response.body()!!.player
                playerView.showLoading()
                playerView.showPlayerItem(listPlayer)
                playerView.hideLoading()
            }
        })
    }
}