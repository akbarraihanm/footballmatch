package com.example.akbar.retrofitsample.View

import com.example.akbar.retrofitsample.Model.Player

interface PlayerView{
    fun showLoading()
    fun hideLoading()
    fun showPlayerItem(listPlayer: ArrayList<Player>)
}