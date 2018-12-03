package com.example.akbar.retrofitsample.View

import com.example.akbar.retrofitsample.Model.DetailTeam

interface ListTeamView{
    fun showListTeamItem(listTeam: ArrayList<DetailTeam>)
    fun showLoading()
    fun hideLoading()
}