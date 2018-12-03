package com.example.akbar.retrofitsample.View

import com.example.akbar.retrofitsample.Model.Fixture


interface LastFixtureView{
    fun showLastFixtureItem (lastFixture : ArrayList<Fixture>)
    fun showLoading()
    fun hideLoading()
}