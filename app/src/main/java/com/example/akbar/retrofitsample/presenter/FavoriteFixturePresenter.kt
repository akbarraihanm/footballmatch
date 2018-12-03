package com.example.akbar.retrofitsample.presenter

import android.content.Context
import com.example.akbar.retrofitsample.Model.Favorite
import com.example.akbar.retrofitsample.View.FavoriteFixtureView

class FavoriteFixturePresenter(private val view : FavoriteFixtureView,
                               private val context: Context){
    fun getFavoriteFixtureItem(){
        val favoriteFixture : ArrayList<Favorite> = arrayListOf()
        view.showLoading()
        view.showFavoriteFixtureItem(favoriteFixture)
        view.hideLoading()
    }
}