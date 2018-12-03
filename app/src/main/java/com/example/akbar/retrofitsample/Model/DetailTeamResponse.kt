package com.example.akbar.retrofitsample.Model

import com.google.gson.annotations.SerializedName

data class DetailTeamResponse(
    @SerializedName("teams")
    var teams : ArrayList<DetailTeam>
)