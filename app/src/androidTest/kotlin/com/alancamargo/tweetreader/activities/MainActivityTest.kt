package com.alancamargo.tweetreader.activities

import com.alancamargo.tweetreader.di.getTestModules
import com.alancamargo.tweetreader.repository.TweetRepository
import org.junit.After
import org.junit.Before
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class MainActivityTest : KoinTest {

    private val mockRepository by inject<TweetRepository>()

    @Before
    fun setUp() {
        loadKoinModules(getTestModules())
    }

    @After
    fun tearDown() {
        stopKoin()
    }

}