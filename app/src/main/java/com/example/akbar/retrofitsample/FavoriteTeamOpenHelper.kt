package com.example.akbar.retrofitsample

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.akbar.retrofitsample.Model.TeamsFavorite
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.*

class FavoriteTeamOpenHelper(ctx : Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db", null, 1){
    companion object {
        private var instance : FavoriteTeamOpenHelper? = null

        @Synchronized
        fun getInstanceFavorite(ctx: Context) : FavoriteTeamOpenHelper{
            if(instance == null){
                instance = FavoriteTeamOpenHelper(ctx.applicationContext)
            }
            return instance as FavoriteTeamOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TeamsFavorite.TABLE_TEAMSFAV, true,
            TeamsFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            TeamsFavorite.ID_TEAM to TEXT + UNIQUE,
            TeamsFavorite.TEAM_BADGE to TEXT,
            TeamsFavorite.STR_TEAM to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(TeamsFavorite.TABLE_TEAMSFAV, true)
    }
}
val Context.favDatabase : FavoriteTeamOpenHelper
get() = FavoriteTeamOpenHelper.getInstanceFavorite(applicationContext)