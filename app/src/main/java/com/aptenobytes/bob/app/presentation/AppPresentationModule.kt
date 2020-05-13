package com.aptenobytes.bob.app.presentation

import androidx.fragment.app.Fragment
import com.aptenobytes.bob.app.presentation.login.LoginViewModel
import com.aptenobytes.bob.library.base.di.KotlinViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

@ExperimentalCoroutinesApi
@FlowPreview
internal val presentationModule = Kodein.Module("AppPresentationModule") {

    // Login
    bind<LoginViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { LoginViewModel(instance()) }
    }

}
