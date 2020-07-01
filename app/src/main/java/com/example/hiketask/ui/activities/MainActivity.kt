package com.example.hiketask.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var adapter: SearchItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        observeEvents()
    }

    private fun init() {
        adapter = SearchItemAdapter(this)
    }

    private fun showLoader(isPaginationProgress: Boolean = false) {
        if (isPaginationProgress)
            bottom_progress.visibility = View.VISIBLE
        else
            main_progress.visibility = View.VISIBLE
    }

    private fun hideLoader(isPaginationProgress: Boolean = false) {
        if (isPaginationProgress)
            bottom_progress.visibility= View.GONE
        else
            main_progress.visibility=View.GONE
    }

    private fun observeEvents(){
        viewModel.resultLD.observe(this, Observer {
            if (it.photos.isNotEmpty()) {
                setDataInRecylerView(it.photos)
                rv_results.visibility = View.VISIBLE
                tv_intro.visibility = View.GONE
            } else if (it.photos.isEmpty() && adapter.isEmpty()) {
                tv_intro.text = "No results found. Please try again"
                tv_intro.visibility = View.VISIBLE
            }
            hideLoader()
        })

        viewModel.errorLiveData.observe(this, Observer {
            hideLoader()
            rv_results.visibility=View.GONE
            tv_intro.visibility=View.VISIBLE
            tv_intro.text=resources.getString(R.string.error_text)
        })

        search_img.setOnClickListener{
            adapter.clear()
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
        adapter.setAttributes(searchItems)
        rv_results.layoutManager=layoutManager
        rv_results.adapter=adapter
    }
}