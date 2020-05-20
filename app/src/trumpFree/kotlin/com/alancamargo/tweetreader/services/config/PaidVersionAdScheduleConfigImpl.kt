package com.alancamargo.tweetreader.services.config

class PaidVersionAdScheduleConfigImpl : PaidVersionAdScheduleConfig {

    override fun shouldScheduleBackgroundWork(): Boolean = true

}