package com.alancamargo.tweetreader.util

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Ignore
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun shouldSplitWords() {
        val string = "This is a test string containing words, punctuation and " +
                "numbers123."
        val expected = listOf("This", "is", "a", "test", "string", "containing",
            "words", "punctuation", "and", "numbers123")
        val actual = string.getWords()

        assertThat(actual, `is`(expected))
    }

    @Test
    fun shouldDetectHashtagWithoutNumbers() {
        val string = "#Test"

        assertThat(string.isHashtag(), `is`(true))
    }

    @Test
    fun shouldDetectHashtagWithNumbers() {
        val string = "#Test123"

        assertThat(string.isHashtag(), `is`(true))
    }

    @Test
    fun shouldDetectHashtagWithUnderscores() {
        val string = "#Test_1_2_3"

        assertThat(string.isHashtag(), `is`(true))
    }

    @Test
    fun shouldDetectMention() {
        val string = "@prisonplanet"

        assertThat(string.isMention(), `is`(true))
    }

    @Test
    fun shouldDetectUrlWithHttpsWwwAndEndpoint() {
        val string = "http://www.github.com/alancamargo92"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    fun shouldDetectUrlWithHttpsAndWww() {
        val string = "https://www.github.com"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    fun shouldDetectUrlWithHttps() {
        val string = "https://github.com"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    @Ignore("Fix regex")
    // TODO
    fun shouldDetectUrlWithDomainOnly() {
        val string = "github.com"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    @Ignore("Fix regex")
    // TODO
    fun shouldDetectUrlWithDomainAndEndpoint() {
        val string = "github.com/alancamargo92"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    fun shouldDetectUrlWithHttpWwwAndEndpoint() {
        val string = "http://www.github.com/alancamargo92"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    fun shouldDetectUrlWithHttpAndWww() {
        val string = "http://www.github.com"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    fun shouldDetectUrlWithHttp() {
        val string = "http://github.com"

        assertThat(string.isUrl(), `is`(true))
    }

    @Test
    fun shouldDetectPlainText() {
        val string = "This is a plain text with 8 words."

        assertThat(string.isPlainText(), `is`(true))
    }

    @Test
    @Ignore("Fix regex")
    fun shouldDetectStringWithLink() {
        val string = "Check out my github on github.com/alancamargo92, it\'s amazing!"

        assertThat(string.hasLink(), `is`(true))
    }

}