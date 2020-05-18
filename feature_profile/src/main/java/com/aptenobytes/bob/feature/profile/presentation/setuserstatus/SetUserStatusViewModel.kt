package com.aptenobytes.bob.feature.profile.presentation.setuserstatus

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.feature.profile.domain.usecase.SetUserStatusUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SetUserStatusViewModel(
    private val setUserStatusUseCase: SetUserStatusUseCase
) : BaseViewModel<SetUserStatusViewState, SetUserStatusAction>(SetUserStatusViewState.initial()) {

    private val intentChannel = BroadcastChannel<SetUserStatusIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: SetUserStatusIntent) {
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

    private fun <T: SetUserStatusIntent> Flow<T>.toActionFlow(): Flow<SetUserStatusAction> {
        return merge(
            filterIsInstance<SetUserStatusIntent.SetStatusIntent>()
                .flatMapConcat { flow {
                    logAction(SetUserStatusAction.SetStatusAction(userId = it.userId, status = it.status))
                    emit(SetUserStatusAction.SetStatusAction(userId = it.userId, status = it.status))
                } }
        )
    }

    private fun <T: SetUserStatusAction> Flow<T>.processResult(): Flow<SetUserStatusResult> {
        return merge(
            filterIsInstance<SetUserStatusAction.SetStatusAction>()
                .flatMapConcat {
                    processSetUserStatus(userId = it.userId, status = it.status)
                }
        )
    }

    private fun processSetUserStatus(userId: Long, status: UserStatusType): Flow<SetUserStatusResult.SetStatusResult> {
        return flow {
            emit(setUserStatusUseCase.execute(userId = userId, status = status))
        }
            .map {
                SetUserStatusResult.SetStatusResult.Success(it) as SetUserStatusResult.SetStatusResult
            }
            .onStart {
                emit(SetUserStatusResult.SetStatusResult.Loading)
            }
            .catch {
                emit(SetUserStatusResult.SetStatusResult.Failure(it))
            }
    }

    private fun <T: SetUserStatusResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                is SetUserStatusResult.SetStatusResult.Loading -> state.copy(
                    isLoading = true,
                    error = null
                )
                is SetUserStatusResult.SetStatusResult.Success -> state.copy(
                    isLoading = false,
                    user = it.user,
                    error = null
                )
                is SetUserStatusResult.SetStatusResult.Failure -> state.copy(
                    isLoading = false,
                    error = it.error
                )
                else -> state
            }
        }
    }


}


