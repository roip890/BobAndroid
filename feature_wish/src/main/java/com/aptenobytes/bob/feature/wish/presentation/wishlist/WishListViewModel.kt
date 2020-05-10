package com.aptenobytes.bob.feature.wish.presentation.wishlist

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishesListUseCase
import com.aptenobytes.bob.feature.wish.presentation.wishsettings.WishSettingsResult
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class WishListViewModel(
    private val getWishesListUseCase: GetWishesListUseCase
) : BaseViewModel<WishListViewState, WishListAction>(WishListViewState.initial()) {

    private val intentChannel = BroadcastChannel<WishListIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: WishListIntent) {
        intentChannel.send(intent)
    }

    init {
        val intentFlow = intentChannel.asFlow()
        merge(
            intentFlow.filterIsInstance<WishListIntent.InitialIntent>().take(1),
            intentFlow.filterNot {
                it is WishListIntent.InitialIntent
            }
        )
            .toActionFlow()
            .processResult()
            .reduceToStateFlow()
            .launchIn(viewModelScope)
    }

    private fun <T: WishListIntent> Flow<T>.toActionFlow(): Flow<WishListAction> {
        return merge(
            filterIsInstance<WishListIntent.InitialIntent>()
                .flatMapConcat { flow {
                    Timber.v("filterIsInstance")
                    logAction(WishListAction.GetWishListAction)
                    emit(WishListAction.GetWishListAction)
                } },
            filterIsInstance<WishListIntent.GetWishListIntent>()
                .flatMapConcat { flow {
                    logAction(WishListAction.GetWishListAction)
                    emit(WishListAction.GetWishListAction)
                } }
        )
    }

    private fun <T: WishListAction> Flow<T>.processResult(): Flow<WishListResult> {
        return merge(
            filterIsInstance<WishListAction>()
                .flatMapConcat {
                    processGetWishList()
                }
        )
    }

    private fun processGetWishList(): Flow<WishListResult.GetWishListResult> {
        return flow {
            emit(getWishesListUseCase.execute())
        }
            .map {
                WishListResult.GetWishListResult.Success(it) as WishListResult.GetWishListResult
            }
            .onStart {
                emit(WishListResult.GetWishListResult.Loading)
            }
            .catch {
                emit(WishListResult.GetWishListResult.Failure(it))
            }
    }

    private fun <T: WishListResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                is WishListResult.GetWishListResult.Loading -> state.copy(
                    isLoading = true,
                    wishes = listOf(),
                    error = null
                )
                is WishListResult.GetWishListResult.Success -> state.copy(
                    isLoading = false,
                    wishes = it.wishes,
                    error = null
                )
                is WishListResult.GetWishListResult.Failure -> state.copy(
                    isLoading = false,
                    wishes = listOf(),
                    error = it.error
                )
                else -> state
            }
        }
    }


}


