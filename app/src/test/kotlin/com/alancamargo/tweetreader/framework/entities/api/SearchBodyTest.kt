package com.alancamargo.tweetreader.framework.entities.api

import com.alancamargo.tweetreader.domain.entities.SearchBody
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SearchBodyTest {

    @Test
    fun shouldBuildSearchBody() {
        val searchBody = SearchBody.Builder()
            .setUserId("12345")
            .setQueryTerm("my query term")
            .setMaxResults(20)
            .build()

        assertThat(searchBody.query).isEqualTo("from: 12345 \"my query term\"")
        assertThat(searchBody.maxResults).isEqualTo(20)
    }

}