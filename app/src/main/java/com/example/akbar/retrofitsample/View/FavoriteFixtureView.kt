package com.example.akbar.retrofitsample.View

import com.example.akbar.retrofitsample.Model.Favorite

interface FavoriteFixtureView{
    fun showLoading()
    fun hideLoading()
    fun showFavoriteFixtureItem(favoriteFixture: ArrayList<Favorite>)
}