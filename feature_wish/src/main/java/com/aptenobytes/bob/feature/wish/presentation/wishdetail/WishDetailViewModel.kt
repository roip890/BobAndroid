package com.aptenobytes.bob.feature.wish.presentation.wishdetail

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishDetailUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class WishDetailViewModel(
    private val getWishDetailUseCase: GetWishDetailUseCase,
    private val args: WishDetailFragmentArgs
) : BaseViewModel<WishDetailViewState, WishDetailAction>(WishDetailViewState.initial()) {

    private val intentChannel = BroadcastChannel<WishDetailIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: WishDetailIntent) {
        intentChannel.send(intent)
    }

    fun getWishId(): Long {
        return this.args.wishId
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

    private fun <T: WishDetailIntent> Flow<T>.toActionFlow(): Flow<WishDetailAction> {
        return merge(
            filterIsInstance<WishDetailIntent.GetWishDetailIntent>()
                .flatMapConcat { flow {
                    logAction(WishDetailAction.GetWishDetailAction(it.wishId))
                    emit(WishDetailAction.GetWishDetailAction(it.wishId))
                } }
        )
    }

    private fun <T: WishDetailAction> Flow<T>.processResult(): Flow<WishDetailResult> {
        return merge(
            filterIsInstance<WishDetailAction.GetWishDetailAction>()
                .flatMapConcat {
                    processGetWishDetail(it.wishId)
                }
        )
    }

    private fun processGetWishDetail(wishId: Long): Flow<WishDetailResult.GetWishDetailResult> {
        return flow {
            emit(getWishDetailUseCase.execute(wishId))
        }
            .map {
                WishDetailResult.GetWishDetailResult.Success(it) as WishDetailResult.GetWishDetailResult
            }
            .onStart {
                emit(WishDetailResult.GetWishDetailResult.Loading)
            }
            .catch {
                emit(WishDetailResult.GetWishDetailResult.Failure(it))
            }
    }

    private fun <T: WishDetailResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                is WishDetailResult.GetWishDetailResult.Loading -> state.copy(
                    isLoading = true,
                    wish = null,
                    error = null
                )
                is WishDetailResult.GetWishDetailResult.Success -> state.copy(
                    isLoading = false,
                    wish = it.wish,
                    error = null
                )
                is WishDetailResult.GetWishDetailResult.Failure -> state.copy(
                    isLoading = false,
                    wish = null,
                    error = it.error
                )
                else -> state
            }
        }
    }


}


