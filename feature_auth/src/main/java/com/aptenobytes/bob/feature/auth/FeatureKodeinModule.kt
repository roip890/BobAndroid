package com.aptenobytes.bob.feature.auth

import com.aptenobytes.bob.feature.auth.feature.KodeinModuleProvider
import com.aptenobytes.bob.feature.auth.data.dataModule
import com.aptenobytes.bob.feature.auth.domain.domainModule
import com.aptenobytes.bob.feature.auth.presentation.presentationModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.Kodein

internal const val FEATURE_NAME = "Auth"

object FeatureKodeinModule : KodeinModuleProvider {

    @FlowPreview
    @ExperimentalCoroutinesApi
    override val kodeinModule = Kodein.Module("${FEATURE_NAME}Module") {
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }
}
