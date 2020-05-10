package com.aptenobytes.bob.feature.wish.presentation

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import org.kodein.di.Kodein
import androidx.fragment.app.Fragment
import com.aptenobytes.bob.feature.wish.presentation.allwishes.AllWishesViewModel
import com.aptenobytes.bob.feature.wish.presentation.wishessettings.WishesSettingsViewModel
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

    // Wishes Settings
    bind<WishesSettingsViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { WishesSettingsViewModel(instance(), instance(), instance()) }
    }

    // All Wishes
    bind<AllWishesViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { AllWishesViewModel(instance()) }
    }

}
