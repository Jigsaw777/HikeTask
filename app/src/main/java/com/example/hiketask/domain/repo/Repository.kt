package com.example.hiketask.domain.repo

import com.example.hiketask.domain.entities.MainEntity
import com.example.hiketask.domain.requests.GetSearchItemsRequest
import io.reactivex.Single

interface Repository {
    fun getSearchResults(getSearchItemsRequest: GetSearchItemsRequest): Single<MainEntity>
}