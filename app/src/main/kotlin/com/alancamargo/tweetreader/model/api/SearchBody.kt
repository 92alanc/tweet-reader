package com.alancamargo.tweetreader.model.api

import com.alancamargo.tweetreader.api.DEFAULT_MAX_SEARCH_RESULTS

class SearchBody private constructor(val data: Data) {

    data class Data(
        val query: String,
        val maxResults: Int
    )

    class Builder {

        private var query: String = ""
        private var maxResults: Int = DEFAULT_MAX_SEARCH_RESULTS

        fun setUserId(userId: String): Builder = apply {
            query = "$query from: $userId"
        }

        fun setQueryTerm(queryTerm: String): Builder = apply {
            query = "$query \"$queryTerm\""
        }

        fun setMaxResults(maxResults: Int): Builder = apply {
            this.maxResults = maxResults
        }

        fun build(): SearchBody {
            val data = Data(query, maxResults)
            return SearchBody(data)
        }

    }

}