package com.example.akbar.retrofitsample.presenter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.akbar.retrofitsample.Model.Fixture
import com.example.akbar.retrofitsample.Model.FixtureResponse
import com.example.akbar.retrofitsample.View.NextFixtureView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class NextFixturePresenter(private val call : Call<FixtureResponse>,
                           private val context: Context,
                           private val view : NextFixtureView){
    fun getNextFixtureItem(){
        var lastFixture : ArrayList<Fixture>
        call.enqueue(object : Callback<FixtureResponse> {
            override fun onFailure(call: Call<FixtureResponse>?, t: Throwable?) {
                Toast.makeText(context, "Gagal fetch last fixture", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "Gagal fetch last fixture")
            }
            override fun onResponse(call: Call<FixtureResponse>?, response: Response<FixtureResponse>?) {
                lastFixture = response!!.body()!!.events
                Log.d(TAG, response.body().toString())
                view.showLoading()
                try {
                    view.showNextFixtureItem(lastFixture)
                }catch (e:Exception){
                    Log.d(TAG, "Pindah Fragment")
                }

                view.hideLoading()
            }
        })
    }
    fun cancelRequest(){
        call.cancel()
    }
}