package com.alancamargo.tweetreader.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ExtensionsTest {

    @Test
    fun shouldAppendLists() {
        val listA = listOf("a", "b", "c")
        val listB = listOf("d", "e", "f")
        val actual = listA.append(listB)

        assertThat(actual, `is`(listOf("a", "b", "c", "d", "e", "f")))
    }

}