package com.alancamargo.tweetreader.util

import com.alancamargo.tweetreader.domain.tools.formatDate
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class DateTimeUtilsTest {

    @Test
    fun shouldFormatDate() {
        val dateString = "Mon Aug 3 23:13:44 -0300 1992"
        val expected = "03/08/1992 23:13"

        assertThat(formatDate(dateString)).isEqualTo(expected)
    }

}