package com.aptenobytes.bob.feature.wish.presentation.wishlist

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishListFromSettingsUseCase
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishesListUseCase
import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListIntent
import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListResult
import com.aptenobytes.bob.feature.wish.presentation.wishlist.WishListViewState
import com.aptenobytes.bob.library.base.presentation.navigation.NavManager
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class WishListViewModel(
    private val navManager: NavManager,
    private val getWishesListUseCase: GetWishesListUseCase,
    private val getWishListFromSettingsUseCase: GetWishListFromSettingsUseCase
) : BaseViewModel<WishListViewState, WishListAction>(WishListViewState.initial()) {

    private val intentChannel = BroadcastChannel<WishListIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: WishListIntent) {
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

    private fun <T: WishListIntent> Flow<T>.toActionFlow(): Flow<WishListAction> {
        return merge(
            filterIsInstance<WishListIntent.GetWishListIntent>()
                .flatMapConcat { flow {
                    logAction(WishListAction.GetWishListAction(it.index, it.limit, it.refresh))
                    emit(WishListAction.GetWishListAction(it.index, it.limit, it.refresh))
                } }
        )
    }

    private fun <T: WishListAction> Flow<T>.processResult(): Flow<WishListResult> {
        return merge(
            filterIsInstance<WishListAction.GetWishListAction>()
                .flatMapConcat {
                    processGetWishList(it.index, it.limit, it.refresh)
                }
        )
    }

    private fun processGetWishList(index: Int, limit: Int, refresh: Boolean): Flow<WishListResult.GetWishListResult> {
        return flow {
            emit(getWishListFromSettingsUseCase.execute(index, limit))
        }
            .map {
                WishListResult.GetWishListResult.Success(it, refresh) as WishListResult.GetWishListResult
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
                    refresh = it.refresh,
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

    // navigation
    fun navigateToWishDetail(wishId: Long) {
        val navDirections = WishListFragmentDirections.actionWishListToWishDetail(wishId)
        navManager.navigateDirection(navDirections)
    }

}
