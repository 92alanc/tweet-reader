package com.alancamargo.tweetreader.activities

import com.alancamargo.tweetreader.activities.robots.launch
import com.alancamargo.tweetreader.di.getTestModules
import com.alancamargo.tweetreader.handlers.ImageHandler
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject

class PhotoDetailsActivityTest : KoinTest {

    val mockImageHandler by inject<ImageHandler>()

    @Before
    fun setUp() {
        loadKoinModules(getTestModules())
    }

    @Test
    fun shouldLoadPhoto() {
        launch {
        } assert {
            photoIsLoaded()
        }
    }

    @Test
    fun clickClose_shouldCloseActivity() {
        launch {
        } clickClose {
            activityIsClosed()
        }
    }

}