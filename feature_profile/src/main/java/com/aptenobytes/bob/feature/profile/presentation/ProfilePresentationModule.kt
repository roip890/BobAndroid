package com.aptenobytes.bob.feature.profile.presentation

import org.kodein.di.Kodein
import androidx.fragment.app.Fragment
import com.aptenobytes.bob.feature.profile.FEATURE_NAME
import com.aptenobytes.bob.feature.profile.presentation.profilepage.ProfilePageViewModel
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

    // Profile Page
    bind<ProfilePageViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { ProfilePageViewModel(instance()) }
    }

}
