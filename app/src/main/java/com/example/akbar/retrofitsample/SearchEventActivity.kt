package com.example.akbar.retrofitsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView

class SearchEventActivity : AppCompatActivity() {



    lateinit var svFixtureActivity : SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_event)
        svFixtureActivity = findViewById(R.id.sv_fixture_activity)

    }
}
