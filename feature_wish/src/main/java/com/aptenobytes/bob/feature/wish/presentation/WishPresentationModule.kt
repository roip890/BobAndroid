package com.aptenobytes.bob.feature.wish.presentation

import com.aptenobytes.bob.feature.wish.FEATURE_NAME
import org.kodein.di.Kodein
import androidx.fragment.app.Fragment
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.SetWishStatusViewModel
import com.aptenobytes.bob.feature.wish.presentation.wishdetail.WishDetailViewModel
import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListViewModel
import com.aptenobytes.bob.feature.wish.presentation.wishsettings.WishSettingsViewModel
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
    bind<WishSettingsViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { WishSettingsViewModel(instance(), instance(), instance()) }
    }

    // Set Status
    bind<SetWishStatusViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { SetWishStatusViewModel(instance()) }
    }

    // Wish List
    bind<WishListViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { WishListViewModel(instance(), instance(), instance()) }
    }

    // Wish Details
    bind<WishDetailViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { WishDetailViewModel(instance(), instance()) }
    }

}
