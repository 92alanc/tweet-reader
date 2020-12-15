package com.alancamargo.tweetreader.domain.tools

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun shouldSplitWords() {
        val string = "This is a test string containing words, punctuation and numbers123."
        val expected = listOf("This", "is", "a", "test", "string", "containing",
            "words", "punctuation", "and", "numbers123.")
        val actual = string.getWords()


        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun shouldDetectHashtagWithoutNumbers() {
        val string = "#Test"

        assertThat(string.isHashtag()).isTrue()
    }

    @Test
    fun shouldDetectHashtagWithNumbers() {
        val string = "#Test123"

        assertThat(string.isHashtag()).isTrue()
    }

    @Test
    fun shouldDetectHashtagWithUnderscores() {
        val string = "#Test_1_2_3"

        assertThat(string.isHashtag()).isTrue()
    }

    @Test
    fun shouldDetectMention() {
        val string = "@prisonplanet"

        assertThat(string.isMention()).isTrue()
    }

    @Test
    fun shouldDetectUrlWithHttpsWwwAndEndpoint() {
        val string = "http://www.github.com/alancamargo92"

        assertThat(string.isUrl()).isTrue()
    }

    @Test
    fun shouldDetectUrlWithHttpsAndWww() {
        val string = "https://www.github.com"

        assertThat(string.isUrl()).isTrue()
    }

    @Test
    fun shouldDetectUrlWithHttps() {
        val string = "https://github.com"

        assertThat(string.isUrl()).isTrue()
    }

    @Test
    fun shouldDetectUrlWithHttpWwwAndEndpoint() {
        val string = "http://www.github.com/alancamargo92"

        assertThat(string.isUrl()).isTrue()
    }

    @Test
    fun shouldDetectUrlWithHttpAndWww() {
        val string = "http://www.github.com"

        assertThat(string.isUrl()).isTrue()
    }

    @Test
    fun shouldDetectUrlWithHttp() {
        val string = "http://github.com"

        assertThat(string.isUrl()).isTrue()
    }

    @Test
    fun shouldDetectPlainText() {
        val string = "This is a plain text with 8 words."

        assertThat(string.isPlainText()).isTrue()
    }

    @Test
    fun shouldDetectShortUrl() {
        val string = "https://t.co/h12345ABc2y"

        assertThat(string.hasLink()).isTrue()
    }

    @Test
    fun shouldNotSplitLinksWhenGettingSeparateWords() {
        val string = "https://t.co/h12345ABc2y"

        assertThat(string.getWords().size).isEqualTo(1)
    }

}