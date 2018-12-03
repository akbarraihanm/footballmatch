package com.example.akbar.retrofitsample.View

import com.example.akbar.retrofitsample.Model.TeamsFavorite

interface FavoriteTeamVIew{
    fun showLoading()
    fun hideLoading()
    fun showFavoriteTeamItem(favoriteTeam : ArrayList<TeamsFavorite>)
}