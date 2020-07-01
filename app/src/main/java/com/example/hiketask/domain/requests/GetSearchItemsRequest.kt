package com.example.hiketask.domain.requests

class GetSearchItemsRequest(private val pageNumber: Int = 1, private val searchTerm:String) {

    fun getParams() : Map<String,String>{
        val params=HashMap<String,String>()
        params["page"]=pageNumber.toString()
        params["text"]=searchTerm
        params["nojsoncallback"]="1"
        params["format"]="json"
        params["api_key"]="3e7cc266ae2b0e0d78e279ce8e361736"
        params["method"]="flickr.photos.search"
        return params
    }
}