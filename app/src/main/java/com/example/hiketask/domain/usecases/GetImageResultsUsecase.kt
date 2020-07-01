package com.example.hiketask.domain.usecases

import com.example.hiketask.domain.entities.MainEntity
import com.example.hiketask.domain.entities.ParentEntity
import com.example.hiketask.domain.repo.Repository
import com.example.hiketask.domain.requests.GetSearchItemsRequest
import io.reactivex.Single

class GetImageResultsUsecase(private val repo: Repository) {
    fun getImageResults(getSearchItemRequest: GetSearchItemsRequest): Single<ParentEntity> {
        return repo.getSearchResults(getSearchItemRequest)
    }
}