package com.alancamargo.tweetreader.framework.local

import android.content.Context
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import androidx.core.content.FileProvider
import com.alancamargo.tweetreader.data.local.FileType
import com.alancamargo.tweetreader.data.local.TweetLocalDataSource
import com.alancamargo.tweetreader.domain.entities.Tweet
import com.alancamargo.tweetreader.domain.mapper.EntityMapper
import com.alancamargo.tweetreader.framework.entities.TweetResponse
import com.alancamargo.tweetreader.framework.local.db.TweetDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class TweetLocalDataSourceImpl(
    private val context: Context,
    private val tweetDao: TweetDao,
    private val tweetResponseMapper: EntityMapper<TweetResponse, Tweet>,
    private val tweetMapper: EntityMapper<Tweet, TweetResponse>
) : TweetLocalDataSource {

    private val baseDir by lazy { context.filesDir }

    override suspend fun getTweets(): List<Tweet> {
        val tweets = withContext(Dispatchers.IO) {
            tweetDao.select()
        }

        if (tweets.isNotEmpty())
            return tweets.map(tweetResponseMapper::map)
        else
            throw Exception("No tweets found in cache")
    }

    override suspend fun updateCache(tweets: List<Tweet>) = withContext(Dispatchers.IO) {
        tweets.forEach {
            val tweet = tweetMapper.map(it)
            if (!tweetDao.hasTweet(tweet))
                tweetDao.insert(tweet)
        }
    }

    override suspend fun clearCache() = withContext(Dispatchers.IO) {
        tweetDao.delete()
    }

    override suspend fun getFileUri(byteStream: InputStream, id: String, fileType: FileType): Uri {
        val file = saveFile(byteStream, id, fileType)
        return getFileUri(file)
    }

    private suspend fun TweetDao.hasTweet(tweet: TweetResponse): Boolean {
        return count(tweet.id) > 0
    }

    @Suppress("BlockingMethodInNonBlockingContext")
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