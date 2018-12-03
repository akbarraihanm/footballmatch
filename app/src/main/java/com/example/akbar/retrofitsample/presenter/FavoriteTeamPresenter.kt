package com.example.akbar.retrofitsample.presenter

import android.content.Context
import com.example.akbar.retrofitsample.Model.TeamsFavorite
import com.example.akbar.retrofitsample.View.FavoriteTeamVIew

class FavoriteTeamPresenter(private val view : FavoriteTeamVIew,
                            private val context: Context){
    fun getFavoriteTeamItem(){
        val favoriteTeam : ArrayList<TeamsFavorite> = arrayListOf()
        view.showLoading()
        view.showFavoriteTeamItem(favoriteTeam)
        view.hideLoading()
    }
}