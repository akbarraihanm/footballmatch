package com.example.akbar.retrofitsample.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FixtureResponse(
    @SerializedName("events")
    val events : ArrayList<Fixture>,
    @SerializedName("event")
    val event : ArrayList<Fixture>
)