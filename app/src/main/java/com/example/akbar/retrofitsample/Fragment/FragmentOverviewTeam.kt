package com.example.akbar.retrofitsample.Fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ProgressBar
import com.example.akbar.retrofitsample.DetailTeamActivity.Companion.string_id2
import com.example.akbar.retrofitsample.Model.DetailTeam
import com.example.akbar.retrofitsample.Model.DetailTeamResponse
import com.example.akbar.retrofitsample.R
import com.example.akbar.retrofitsample.Service.ApiClient
import com.example.akbar.retrofitsample.Service.ApiInterface
import com.example.akbar.retrofitsample.View.OverviewTeamView
import com.example.akbar.retrofitsample.presenter.OverviewTeamPresenter
import kotlinx.android.synthetic.main.fragment_fragment_overview_team.*
import retrofit2.Call

class FragmentOverviewTeam : Fragment(), OverviewTeamView {

    lateinit var v : View
    lateinit var overviewTeamPresenter: OverviewTeamPresenter
    lateinit var progressBar: ProgressBar
    lateinit var view: OverviewTeamView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_fragment_overview_team, container, false)
        progressBar = v.findViewById(R.id.pb_loading_detail)

        val apiInterface : ApiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call : Call<DetailTeamResponse> = apiInterface.getDetailTeamId(string_id2)

        overviewTeamPresenter = OverviewTeamPresenter(call, this, context!!)
        overviewTeamPresenter.getOverviewTeamItem()

        return v
    }

    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = INVISIBLE
    }

    override fun showOverviewTeamItem(listTeam: ArrayList<DetailTeam>) {
        tv_description.text = listTeam[0].str_description_en
    }
}
