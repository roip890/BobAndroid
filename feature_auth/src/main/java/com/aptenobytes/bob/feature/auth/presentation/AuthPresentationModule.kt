package com.aptenobytes.bob.feature.auth.presentation

import org.kodein.di.Kodein
import androidx.fragment.app.Fragment
import com.aptenobytes.bob.feature.auth.presentation.login.LoginViewModel
import com.aptenobytes.bob.feature.auth.FEATURE_NAME
import com.aptenobytes.bob.library.base.di.KotlinViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

@ExperimentalCoroutinesApi
@FlowPreview
internal val presentationModule = Kodein.Module("${FEATURE_NAME}PresentationModule") {

    // Login
    bind<LoginViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { LoginViewModel(instance(), instance()) }
    }

}
