package com.example.akbar.retrofitsample.View

import com.example.akbar.retrofitsample.Model.DetailTeam

interface OverviewTeamView{
    fun showLoading()
    fun hideLoading()
    fun showOverviewTeamItem(listTeam : ArrayList<DetailTeam>)
}