package com.alancamargo.tweetreader.di

import com.alancamargo.tweetreader.services.config.PaidVersionAdDisplayConfig
import com.alancamargo.tweetreader.services.config.PaidVersionAdDisplayConfigImpl
import com.alancamargo.tweetreader.services.config.PaidVersionAdScheduleConfig
import com.alancamargo.tweetreader.services.config.PaidVersionAdScheduleConfigImpl
import org.koin.core.module.Module
import org.koin.dsl.module

class FreeKoinModules : KoinModules() {

    override fun getModules(): ArrayList<Module> {
        return super.getModules().apply {
            add(services)
        }
    }

    private val services = module {
        factory<PaidVersionAdScheduleConfig> { PaidVersionAdScheduleConfigImpl() }
        single<PaidVersionAdDisplayConfig> { PaidVersionAdDisplayConfigImpl() }
    }

}