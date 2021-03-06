package com.aptenobytes.bob.app

import android.content.Context
import com.facebook.stetho.Stetho
import com.google.android.play.core.splitcompat.SplitCompatApplication
import com.aptenobytes.bob.BuildConfig
import com.aptenobytes.bob.app.feature.FeatureManager
import com.aptenobytes.bob.app.kodein.FragmentArgsExternalSource
import com.aptenobytes.bob.appModule
import com.aptenobytes.bob.library.base.baseModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber

/*
False positive "Unused symbol" for a custom Android application class referenced in AndroidManifest.xml file:
https://youtrack.jetbrains.net/issue/KT-27971
*/
class BobApplication : SplitCompatApplication(), KodeinAware {
    @ExperimentalCoroutinesApi
    @FlowPreview
    override val kodein = Kodein.lazy {
        import(androidXModule(this@BobApplication))
        import(baseModule)
        import(appModule)
        importAll(FeatureManager.kodeinModules)

        externalSources.add(FragmentArgsExternalSource())
    }

    private lateinit var context: Context

    override fun onCreate() {
        super.onCreate()

        context = this

        initTimber()
        initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
