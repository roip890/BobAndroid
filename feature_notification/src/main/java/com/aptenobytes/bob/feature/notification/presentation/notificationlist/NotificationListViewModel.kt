package com.aptenobytes.bob.feature.notification.presentation.notificationlist

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.notification.domain.usecase.GetNotificationsListUseCase
import com.aptenobytes.bob.library.base.presentation.navigation.NavManager
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class NotificationListViewModel(
    private val navManager: NavManager,
    private val getNotificationsListUseCase: GetNotificationsListUseCase
) : BaseViewModel<NotificationListViewState, NotificationListAction>(NotificationListViewState.initial()) {

    private val intentChannel = BroadcastChannel<NotificationListIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: NotificationListIntent) {
        intentChannel.send(intent)
    }

    init {
        val intentFlow = intentChannel.asFlow()
        merge(
            intentFlow
        )
            .toActionFlow()
            .processResult()
            .reduceToStateFlow()
            .launchIn(viewModelScope)
    }

    private fun <T: NotificationListIntent> Flow<T>.toActionFlow(): Flow<NotificationListAction> {
        return merge(
            filterIsInstance<NotificationListIntent.GetNotificationListIntent>()
                .flatMapConcat { flow {
                    logAction(NotificationListAction.GetNotificationListAction(it.index, it.limit, it.refresh))
                    emit(NotificationListAction.GetNotificationListAction(it.index, it.limit, it.refresh))
                } }
        )
    }

    private fun <T: NotificationListAction> Flow<T>.processResult(): Flow<NotificationListResult> {
        return merge(
            filterIsInstance<NotificationListAction.GetNotificationListAction>()
                .flatMapConcat {
                    processGetNotificationList(it.index, it.limit, it.refresh)
                }
        )
    }

    private fun processGetNotificationList(index: Int, limit: Int, refresh: Boolean): Flow<NotificationListResult.GetNotificationListResult> {
        return flow {
            emit(getNotificationsListUseCase.execute(index, limit))
        }
            .map {
                NotificationListResult.GetNotificationListResult.Success(it, refresh) as NotificationListResult.GetNotificationListResult
            }
            .onStart {
                emit(NotificationListResult.GetNotificationListResult.Loading)
            }
            .catch {
                emit(NotificationListResult.GetNotificationListResult.Failure(it))
            }
    }

    private fun <T: NotificationListResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                is NotificationListResult.GetNotificationListResult.Loading -> state.copy(
                    isLoading = true,
                    notifications = listOf(),
                    error = null
                )
                is NotificationListResult.GetNotificationListResult.Success -> state.copy(
                    refresh = it.refresh,
                    isLoading = false,
                    notifications = it.notifications,
                    error = null
                )
                is NotificationListResult.GetNotificationListResult.Failure -> state.copy(
                    isLoading = false,
                    notifications = listOf(),
                    error = it.error
                )
                else -> state
            }
        }
    }

}
