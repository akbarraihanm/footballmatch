package com.example.akbar.retrofitsample.Model

import com.google.gson.annotations.SerializedName

data class DetailTeam(
    @SerializedName("idTeam")
    var id_team : String?,
    @SerializedName("strTeamBadge")
    var team_badge : String?,
    @SerializedName("strTeam")
    var str_team : String?,
    @SerializedName("intFormedYear")
    var int_formed_year : String?,
    @SerializedName("strStadium")
    var str_stadium : String?,
    @SerializedName("strDescriptionEN")
    var str_description_en : String?
)