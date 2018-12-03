package com.example.akbar.retrofitsample.Model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @SerializedName("player")
    var player : ArrayList<Player>,
    @SerializedName("players")
    var players : ArrayList<Player>
)