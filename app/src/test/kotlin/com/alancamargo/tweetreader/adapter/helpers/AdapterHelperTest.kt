package com.alancamargo.tweetreader.adapter.helpers

import com.alancamargo.tweetreader.adapter.viewholder.*
import com.alancamargo.tweetreader.testtools.MockTweetBuilder
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class AdapterHelperTest {

    @MockK lateinit var mockViewHolderFactory: ViewHolderFactory

    private lateinit var adapterHelper: AdapterHelper

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxed = true)
        adapterHelper = AdapterHelperImpl(mockViewHolderFactory).apply {
            inflater = mockk()
            parent = mockk()
        }
    }

    @Test
    fun withVideoTweet_shouldReturnCorrectViewType() {
        val tweet = MockTweetBuilder().withVideo().build()

        val viewType = adapterHelper.getItemViewType(tweet, 0)

        assertThat(viewType).isEqualTo(VIEW_TYPE_VIDEO)
    }

    @Test
    fun withPhotoTweet_shouldReturnCorrectViewType() {
        val tweet = MockTweetBuilder().withPhoto().build()

        val viewType = adapterHelper.getItemViewType(tweet, 0)

        assertThat(viewType).isEqualTo(VIEW_TYPE_PHOTO)
    }

    @Test
    fun withLinkTweet_shouldReturnCorrectViewType() {
        val tweet = MockTweetBuilder().withLink().build()

        val viewType = adapterHelper.getItemViewType(tweet, 0)

        assertThat(viewType).isEqualTo(VIEW_TYPE_LINK)
    }

    @Test
    fun withQuotedTweet_shouldReturnCorrectViewType() {
        val tweet = MockTweetBuilder().withQuotedTweet().build()

        val viewType = adapterHelper.getItemViewType(tweet, 0)

        assertThat(viewType).isEqualTo(VIEW_TYPE_QUOTED_TWEET)
    }

    @Test
    fun withRetweet_shouldReturnCorrectViewType() {
        val tweet = MockTweetBuilder().withRetweet().build()

        val viewType = adapterHelper.getItemViewType(tweet, 0)

        assertThat(viewType).isEqualTo(VIEW_TYPE_RETWEET)
    }

    @Test
    fun withRepliedTweet_shouldReturnCorrectViewType() {
        val tweet = MockTweetBuilder().withReply().build()

        val viewType = adapterHelper.getItemViewType(tweet, 0)

        assertThat(viewType).isEqualTo(VIEW_TYPE_REPLY)
    }

    @Test
    fun withTextTweet_shouldReturnCorrectViewType() {
        val tweet = MockTweetBuilder().build()

        val viewType = adapterHelper.getItemViewType(tweet, 0)

        assertThat(viewType).isEqualTo(VIEW_TYPE_TEXT)
    }

    @Test
    fun withVideoViewType_shouldReturnCorrectViewHolder() {
        val viewHolder = adapterHelper.getViewHolder(VIEW_TYPE_VIDEO)

        assertThat(viewHolder).isInstanceOf(VideoTweetViewHolder::class.java)
    }

    @Test
    fun withPhotoViewType_shouldReturnCorrectViewHolder() {
        val viewHolder = adapterHelper.getViewHolder(VIEW_TYPE_PHOTO)

        assertThat(viewHolder).isInstanceOf(PhotoTweetViewHolder::class.java)
    }

    @Test
    fun withLinkViewType_shouldReturnCorrectViewHolder() {
        val viewHolder = adapterHelper.getViewHolder(VIEW_TYPE_LINK)

        assertThat(viewHolder).isInstanceOf(LinkTweetViewHolder::class.java)
    }

    @Test
    fun withQuotedTweetViewType_shouldReturnCorrectViewHolder() {
        val viewHolder = adapterHelper.getViewHolder(VIEW_TYPE_QUOTED_TWEET)

        assertThat(viewHolder).isInstanceOf(QuotedTweetViewHolder::class.java)
    }

    @Test
    fun withRetweetViewType_shouldReturnCorrectViewHolder() {
        val viewHolder = adapterHelper.getViewHolder(VIEW_TYPE_RETWEET)

        assertThat(viewHolder).isInstanceOf(RetweetViewHolder::class.java)
    }

    @Test
    fun withReplyViewType_shouldReturnCorrectViewHolder() {
        val viewHolder = adapterHelper.getViewHolder(VIEW_TYPE_REPLY)

        assertThat(viewHolder).isInstanceOf(ReplyViewHolder::class.java)
    }

    @Test
    fun withTextViewType_shouldReturnCorrectViewHolder() {
        val viewHolder = adapterHelper.getViewHolder(VIEW_TYPE_TEXT)

        assertThat(viewHolder).isInstanceOf(TweetViewHolder::class.java)
    }

    @Test
    fun whenCallingGetViewHolder_shouldInitialiseViewHolderFactory() {
        adapterHelper.getViewHolder(VIEW_TYPE_TEXT)

        verify { mockViewHolderFactory.init(any(), any(), any()) }
    }

    private companion object {
        const val VIEW_TYPE_TEXT = 0
        const val VIEW_TYPE_PHOTO = 1
        const val VIEW_TYPE_VIDEO = 2
        const val VIEW_TYPE_LINK = 3
        const val VIEW_TYPE_QUOTED_TWEET = 4
        const val VIEW_TYPE_RETWEET = 5
        const val VIEW_TYPE_REPLY = 6
    }

}