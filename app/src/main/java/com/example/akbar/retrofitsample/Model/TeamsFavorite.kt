package com.example.akbar.retrofitsample.Model

data class TeamsFavorite(val id : Long?,
                         val id_team : String?,
                         val team_badge : String?,
                         val str_team : String?
){
    companion object {
        const val TABLE_TEAMSFAV : String = "TABLE_TEAMSFAV"
        const val ID : String = "ID_"
        const val ID_TEAM : String = "ID_TEAM"
        const val TEAM_BADGE : String = "TEAM_BADGE"
        const val STR_TEAM : String = "STR_TEAM"
    }
}