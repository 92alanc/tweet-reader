package com.alancamargo.tweetreader.tools

import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import java.io.InputStream

val moshi: Moshi = Moshi.Builder().build()

fun getJsonFromAsset(fileName: String): String {
    val json: String

    try {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream: InputStream = context.assets.open("fixtures/$fileName.json")
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return ""
    }

    return json.replace("\n", "")
}