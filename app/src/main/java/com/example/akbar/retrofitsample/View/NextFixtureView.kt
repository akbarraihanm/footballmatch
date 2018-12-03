package com.example.akbar.retrofitsample.View

import com.example.akbar.retrofitsample.Model.Fixture

interface NextFixtureView{
    fun showLoading()
    fun hideLoading()
    fun showNextFixtureItem(lastFixture : ArrayList<Fixture>)
}