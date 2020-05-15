package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.domain.usecase.SetWishStatusUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class SetWishStatusViewModel(
    private val setWishStatusUseCase: SetWishStatusUseCase
) : BaseViewModel<SetWishStatusViewState, SetWishStatusAction>(SetWishStatusViewState.initial()) {

    private val intentChannel = BroadcastChannel<SetWishStatusIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: SetWishStatusIntent) {
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

    private fun <T: SetWishStatusIntent> Flow<T>.toActionFlow(): Flow<SetWishStatusAction> {
        return merge(
            filterIsInstance<SetWishStatusIntent.SetStatusIntent>()
                .flatMapConcat { flow {
                    logAction(SetWishStatusAction.SetStatusAction(wishId = it.wishId, status = it.status))
                    emit(SetWishStatusAction.SetStatusAction(wishId = it.wishId, status = it.status))
                } }
        )
    }

    private fun <T: SetWishStatusAction> Flow<T>.processResult(): Flow<SetWishStatusResult> {
        return merge(
            filterIsInstance<SetWishStatusAction.SetStatusAction>()
                .flatMapConcat {
                    processSetWishStatus(wishId = it.wishId, status = it.status)
                }
        )
    }

    private fun processSetWishStatus(wishId: Long, status: WishStatusType): Flow<SetWishStatusResult.SetStatusResult> {
        return flow {
            emit(setWishStatusUseCase.execute(wishId = wishId, status = status))
        }
            .map {
                SetWishStatusResult.SetStatusResult.Success(it) as SetWishStatusResult.SetStatusResult
            }
            .onStart {
                emit(SetWishStatusResult.SetStatusResult.Loading)
            }
            .catch {
                emit(SetWishStatusResult.SetStatusResult.Failure(it))
            }
    }

    private fun <T: SetWishStatusResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                is SetWishStatusResult.SetStatusResult.Loading -> state.copy(
                    isLoading = true,
                    error = null
                )
                is SetWishStatusResult.SetStatusResult.Success -> state.copy(
                    isLoading = false,
                    wish = it.wish,
                    error = null
                )
                is SetWishStatusResult.SetStatusResult.Failure -> state.copy(
                    isLoading = false,
                    error = it.error
                )
                else -> state
            }
        }
    }


}


