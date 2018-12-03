package com.example.akbar.retrofitsample

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.akbar.retrofitsample.Model.Favorite
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper (ctx : Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteFixture.db", null, 1){
    companion object {
        private var instance : MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) : MyDatabaseOpenHelper{
            if (instance == null){
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_EVENT to TEXT + UNIQUE,
            Favorite.STR_DATE to TEXT,
            Favorite.STR_HOMETEAM to TEXT,
            Favorite.STR_AWAYTEAM to TEXT,
            Favorite.INT_HOMESCORE to TEXT,
            Favorite.INT_AWAYSCORE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}
val Context.database : MyDatabaseOpenHelper
get() = MyDatabaseOpenHelper.getInstance(applicationContext)