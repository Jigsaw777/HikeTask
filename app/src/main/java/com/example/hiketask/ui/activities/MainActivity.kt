package com.example.hiketask.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private var isScrolling:Boolean=false

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
            hideLoader(true)
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
            viewModel.searchTerm=searchItem
            if(searchItem.trim().isEmpty())
                Toast.makeText(this,"Please enter something",Toast.LENGTH_SHORT).show()
            else{
                viewModel.getSearchresults(searchItem)
                showLoader()
                tv_intro.visibility=View.GONE
            }
        }
    }

    private fun setDataInRecylerView(searchItems:List<PhotoEntity>){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter.setAttributes(searchItems)
        rv_results.layoutManager=layoutManager
        rv_results.itemAnimator=DefaultItemAnimator()
        rv_results.adapter=adapter

        rv_results.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItems=layoutManager.childCount
                val totalItems=layoutManager.itemCount
                val scrollItems=layoutManager.findFirstVisibleItemPosition()

                if(isScrolling && (currentItems+scrollItems == totalItems)){
                    isScrolling=false
                    hideLoader(true)
                    viewModel.getSearchresults(viewModel.searchTerm,true)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)){
                    isScrolling=true
                    showLoader(true)
                }
            }
        })
    }
}