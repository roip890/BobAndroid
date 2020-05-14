package com.aptenobytes.bob.feature.notification.presentation

import androidx.fragment.app.Fragment
import com.aptenobytes.bob.feature.notification.FEATURE_NAME
import com.aptenobytes.bob.feature.notification.presentation.notificationlist.NotificationListViewModel
import com.aptenobytes.bob.library.base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
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

    // Notification List
    bind<NotificationListViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context) { NotificationListViewModel(instance(), instance()) }
    }

}
