package com.example.akbar.retrofitsample.Model

import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("idTeam")
    var id_team_p : String?,
    @SerializedName("idPlayer")
    var id_player : String?,
    @SerializedName("strPlayer")
    var str_player : String?,
    @SerializedName("strDescriptionEN")
    var str_description_en_p : String?,
    @SerializedName("strHeight")
    var str_height : String?,
    @SerializedName("strWeight")
    var str_weight : String?,
    @SerializedName("strCutout")
    var str_cutout : String?,
    @SerializedName("strFanart3")
    var str_fanart3 : String?,
    @SerializedName("strPosition")
    var str_position : String?
)