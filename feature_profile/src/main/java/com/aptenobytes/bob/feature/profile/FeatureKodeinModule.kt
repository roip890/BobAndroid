package com.aptenobytes.bob.feature.profile

import com.aptenobytes.bob.app.feature.KodeinModuleProvider
import com.aptenobytes.bob.feature.profile.data.dataModule
import com.aptenobytes.bob.feature.profile.domain.domainModule
import com.aptenobytes.bob.feature.profile.presentation.presentationModule
import org.kodein.di.Kodein

internal const val FEATURE_NAME = "Profile"

object FeatureKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${FEATURE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}
