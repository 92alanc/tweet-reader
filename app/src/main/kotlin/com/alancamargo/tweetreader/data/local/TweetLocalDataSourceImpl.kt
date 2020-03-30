package com.alancamargo.tweetreader.data.local

import android.content.Context
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import androidx.core.content.FileProvider
import com.alancamargo.tweetreader.db.TweetDatabaseManager
import com.alancamargo.tweetreader.model.Tweet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class TweetLocalDataSourceImpl(
    private val context: Context,
    private val dbManager: TweetDatabaseManager
) : TweetLocalDataSource {

    private val baseDir by lazy { context.filesDir }

    override suspend fun getTweets(): List<Tweet> {
        val tweets = withContext(Dispatchers.IO) {
            dbManager.select()
        }

        if (tweets.isNotEmpty())
            return tweets
        else
            throw Exception("No tweets found in cache")
    }

    override suspend fun updateCache(tweets: List<Tweet>) = withContext(Dispatchers.IO) {
        tweets.forEach {
            if (!dbManager.hasTweet(it))
                dbManager.insert(it)
        }
    }

    override suspend fun clearCache() = withContext(Dispatchers.IO) {
        dbManager.delete()
    }

    override suspend fun getFileUri(byteStream: InputStream, id: String, fileType: FileType): Uri {
        val file = saveFile(byteStream, id, fileType)
        return getFileUri(file)
    }

    private suspend fun TweetDatabaseManager.hasTweet(tweet: Tweet): Boolean {
        return count(tweet.id) > 0
    }

    private suspend fun saveFile(byteStream: InputStream, id: String, fileType: FileType): File {
        if (!baseDir.exists())
            baseDir.mkdirs()

        val fileName = composeFileName(id, fileType)
        val file = File(baseDir, fileName)

        withContext(Dispatchers.IO) {
            with(byteStream) {
                FileOutputStream(file).use { out ->
                    val buffer = ByteArray(available())
                    var content = read(buffer)

                    while (content != -1) {
                        out.write(buffer, 0, content)
                        content = read(buffer)
                    }

                    out.flush()
                }
            }
        }

        return file
    }

    private fun composeFileName(id: String, fileType: FileType): String {
        val extension = if (fileType == FileType.IMAGE) "jpg" else "mp4"

        return "$id.$extension"
    }

    private fun getFileUri(file: File): Uri {
        return if (SDK_INT >= N) {
            val authority = "${context.packageName}.provider"
            FileProvider.getUriForFile(context, authority, file)
        } else {
            Uri.fromFile(file)
        }
    }

}