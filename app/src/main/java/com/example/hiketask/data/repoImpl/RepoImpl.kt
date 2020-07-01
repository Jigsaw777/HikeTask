package com.example.hiketask.data.repoImpl

import com.example.hiketask.data.services.GetServices
import com.example.hiketask.domain.entities.MainEntity
import com.example.hiketask.domain.entities.ParentEntity
import com.example.hiketask.domain.repo.Repository
import com.example.hiketask.domain.requests.GetSearchItemsRequest
import com.example.hiketask.utils.NetworkManager
import io.reactivex.Single

class RepoImpl : Repository {

    companion object {
        val getServices: GetServices by lazy { NetworkManager.getFetchSevices() }
    }

    override fun getSearchResults(getSearchItemsRequest: GetSearchItemsRequest): Single<ParentEntity> {
        return getServices.getImages(getSearchItemsRequest.getParams())
    }

}