package com.aptenobytes.bob.feature.favourite.domain

import com.aptenobytes.bob.feature.favourite.FEATURE_NAME
import org.kodein.di.Kodein

internal val domainModule = Kodein.Module("${FEATURE_NAME}DomainModule") { }
