package com.alancamargo.tweetreader.model.api

class SearchBody private constructor(val query: String, val maxResults: Int) {

    class Builder {

        private var query: String = ""
        private var maxResults: Int = 0

        fun setUserId(userId: String): Builder = apply {
            query = if (query.isNotEmpty())
                "$query from: $userId"
            else
                "from: $userId"
        }

        fun setQueryTerm(queryTerm: String): Builder = apply {
            query = if (query.isNotEmpty())
                "$query \"$queryTerm\""
            else
                "\"$queryTerm\""
        }

        fun setMaxResults(maxResults: Int): Builder = apply {
            this.maxResults = maxResults
        }

        fun build(): SearchBody {
            return SearchBody(query, maxResults)
        }

    }

}