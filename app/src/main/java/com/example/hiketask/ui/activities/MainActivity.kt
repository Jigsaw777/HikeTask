package com.example.hiketask.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hiketask.R
import com.example.hiketask.domain.entities.PhotoEntity
import com.example.hiketask.ui.adapters.SearchItemAdapter
import com.example.hiketask.ui.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel()::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeEvents()
    }

    private fun showLoader(isPaginationProgress:Boolean=false){
        if(isPaginationProgress)
            bottom_progress.visibility= View.VISIBLE
        else
            main_progress.visibility=View.VISIBLE
    }

    private fun hideLoader(isPaginationProgress:Boolean=false){
        if(isPaginationProgress)
            bottom_progress.visibility= View.GONE
        else
            main_progress.visibility=View.GONE
    }

    private fun observeEvents(){
        viewModel.resultLD.observe(this, Observer {
            setDataInRecylerView(it.photos)
            hideLoader()
            rv_results.visibility=View.VISIBLE
        })

        viewModel.errorLiveData.observe(this, Observer {
            hideLoader()
            rv_results.visibility=View.GONE
            tv_intro.visibility=View.VISIBLE
            tv_intro.text=resources.getString(R.string.error_text)
        })

        search_img.setOnClickListener{
            val searchItem=search_text.text.toString()
            if(searchItem.trim().isEmpty())
                Toast.makeText(this,"Please enter something",Toast.LENGTH_SHORT).show()
            else{
                viewModel.getSearchresults(searchItem,1)
                showLoader()
                tv_intro.visibility=View.GONE
            }
        }
    }

    private fun setDataInRecylerView(searchItems:List<PhotoEntity>){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val adapter=SearchItemAdapter(this)
        adapter.setAttributes(searchItems)
        rv_results.layoutManager=layoutManager
        rv_results.adapter=adapter
    }
}