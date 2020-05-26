package me.brendonp.tabcorp.application

import android.app.Application

class TabcorpSpaceApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DependencyContainer.initialize();
    }
}