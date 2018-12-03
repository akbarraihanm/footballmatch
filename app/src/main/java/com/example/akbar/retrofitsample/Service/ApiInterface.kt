package com.example.akbar.retrofitsample.Service

import com.example.akbar.retrofitsample.Model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface{
    @GET("eventspastleague.php?id=4328")
    fun getLastFixture() : Call<FixtureResponse>
    @GET("eventsnextleague.php?id=4328")
    fun getNextFixture() : Call<FixtureResponse>
    @GET("lookupevent.php")
    fun getEventFixture(@Query("id")idEvent : String?) : Call<FixtureResponse>
    @GET("lookupteam.php")
    fun getImageTeam(@Query("id")idTeam : String?) : Call<DetailTeamResponse>
    @GET("lookupteam.php")
    fun getDetailTeamId(@Query("id")idTeam: String?) : Call<DetailTeamResponse>
    @GET("search_all_teams.php")
    fun getLeagueItemId(@Query("l")league : String?) : Call<DetailTeamResponse>
    @GET("search_all_teams.php?l=English Premier League")
    fun getLeagueItem() : Call<DetailTeamResponse>
    @GET("eventspastleague.php")
    fun getLastFixtureId(@Query("id")idLeague : String?) : Call<FixtureResponse>
    @GET("eventsnextleague.php")
    fun getNextFixtureId(@Query("id")idLeague: String?) : Call<FixtureResponse>
    @GET("lookup_all_players.php")
    fun getPlayerItem(@Query("id")idTeam: String?) : Call<PlayerResponse>
    @GET("lookupplayer.php")
    fun getPlayerItemId(@Query("id")idPlayer: String?) : Call<PlayerResponse>
    @GET("searchevents.php")
    fun getSearchEventItem(@Query("e")strEvent : String?) : Call<FixtureResponse>
    @GET("searchteams.php")
    fun getSearchTeamItem(@Query("t")strTeam: String?) : Call<DetailTeamResponse>
}