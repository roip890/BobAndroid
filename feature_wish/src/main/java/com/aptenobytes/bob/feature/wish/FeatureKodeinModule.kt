package com.aptenobytes.bob.feature.wish

import com.aptenobytes.bob.app.feature.KodeinModuleProvider
import com.aptenobytes.bob.feature.wish.data.dataModule
import com.aptenobytes.bob.feature.wish.domain.domainModule
import com.aptenobytes.bob.feature.wish.presentation.presentationModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.Kodein

internal const val FEATURE_NAME = "Wish"

object FeatureKodeinModule : KodeinModuleProvider {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override val kodeinModule = Kodein.Module("${FEATURE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}
