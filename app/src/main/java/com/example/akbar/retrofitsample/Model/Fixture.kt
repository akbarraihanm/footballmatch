package com.example.akbar.retrofitsample.Model

import com.google.gson.annotations.SerializedName

data class Fixture (
    @SerializedName("idEvent")
    var id_event : String?,
    @SerializedName("idHomeTeam")
    var id_home_team : String?,
    @SerializedName("idAwayTeam")
    var id_away_team : String?,
    @SerializedName("dateEvent")
    var str_date : String?,
    @SerializedName("strTime")
    var str_time : String?,
    @SerializedName("strHomeTeam")
    var str_HomeTeam : String?,
    @SerializedName("strAwayTeam")
    var str_AwayTeam : String?,
    @SerializedName("intHomeScore")
    var int_HomeScore : String?,
    @SerializedName("intAwayScore")
    var int_AwayScore : String?,
    @SerializedName("strHomeGoalDetails")
    var str_home_goal : String?,
    @SerializedName("strAwayGoalDetails")
    var str_awa_goal : String?,
    @SerializedName("intHomeShots")
    var int_home_shot : String?,
    @SerializedName("intAwayShots")
    var int_away_shot : String?,
    @SerializedName("strHomeLineupGoalkeeper")
    var home_gk : String?,
    @SerializedName("strHomeLineupDefense")
    var home_def : String?,
    @SerializedName("strHomeLineupMidfield")
    var home_mid : String?,
    @SerializedName("strHomeLineupForward")
    var home_fw : String?,
    @SerializedName("strHomeLineupSubstitutes")
    var home_subs : String?,
    @SerializedName("strAwayLineupGoalkeeper")
    var away_gk : String?,
    @SerializedName("strAwayLineupDefense")
    var away_def : String?,
    @SerializedName("strAwayLineupMidfield")
    var away_mid : String?,
    @SerializedName("strAwayLineupForward")
    var away_fw : String?,
    @SerializedName("strAwayLineupSubstitutes")
    var away_subs : String?
)