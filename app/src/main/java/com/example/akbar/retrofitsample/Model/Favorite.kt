package com.example.akbar.retrofitsample.Model

data class Favorite(val id : Long?,
                    val id_event : String?,
                    val str_date : String?,
                    val str_HomeTeam : String?,
                    val str_AwayTeam : String?,
                    val int_HomeScore : String?,
                    val int_AwayScore : String?){
    companion object {
        const val TABLE_FAVORITE : String = "TABLE_FAVORITE"
        const val ID : String = "ID_"
        const val ID_EVENT : String = "ID_EVENT"
        const val STR_DATE : String = "STR_DATE"
        const val STR_HOMETEAM : String = "STR_HOMETEAM"
        const val STR_AWAYTEAM : String = "STR_AWAYTEAM"
        const val INT_HOMESCORE : String = "INT_HOMESCORE"
        const val INT_AWAYSCORE : String = "INT_AWAYSCORE"
    }
}