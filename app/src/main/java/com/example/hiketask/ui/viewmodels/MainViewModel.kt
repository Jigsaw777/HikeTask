package com.example.hiketask.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiketask.data.repoImpl.RepoImpl
import com.example.hiketask.domain.entities.MainEntity
import com.example.hiketask.domain.repo.Repository
import com.example.hiketask.domain.requests.GetSearchItemsRequest
import com.example.hiketask.domain.usecases.GetImageResultsUsecase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    companion object {
        private val repo: Repository = RepoImpl()
        val getImageResultsUsecase = GetImageResultsUsecase(repo)
    }

    private val compositeDisposable = CompositeDisposable()

    private val searchResultItems = MutableLiveData<MainEntity>()
    private val errorLD = MutableLiveData<String>()

    val resultLD: LiveData<MainEntity>
        get() = searchResultItems
    val errorLiveData: LiveData<String>
        get() = errorLD

    fun getSearchresults(searchTerm: String, pageNumber: Int) {
        val request = GetSearchItemsRequest(pageNumber, searchTerm)
        getImageResultsUsecase.getImageResults(request).subscribeOn(Schedulers.io())
            .subscribe({
                searchResultItems.postValue(it.photos)
            }, {
                errorLD.postValue(it.localizedMessage)
            }).let { compositeDisposable.add(it) }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
}