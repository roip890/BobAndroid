package com.aptenobytes.bob.feature.favourite

import com.aptenobytes.bob.app.feature.KodeinModuleProvider
import com.aptenobytes.bob.feature.favourite.data.dataModule
import com.aptenobytes.bob.feature.favourite.domain.domainModule
import com.aptenobytes.bob.feature.favourite.presentation.presentationModule
import org.kodein.di.Kodein

internal const val FEATURE_NAME = "Favourite"

object FeatureKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${FEATURE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}
