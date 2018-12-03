package com.example.akbar.retrofitsample.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient{
    companion object {
        fun getClient() : Retrofit{
            val baseURL = "https://www.thesportsdb.com/api/v1/json/1/"
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}