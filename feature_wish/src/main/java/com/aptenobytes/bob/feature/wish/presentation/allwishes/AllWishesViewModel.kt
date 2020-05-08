package com.aptenobytes.bob.feature.wish.presentation.allwishes

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishesListUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class AllWishesViewModel(
    private val getWishesListUseCase: GetWishesListUseCase
) : BaseViewModel<AllWishesViewState, AllWishesAction>(AllWishesViewState.initial()) {

  private val intentChannel = BroadcastChannel<AllWishesIntent>(capacity = Channel.CONFLATED)
  suspend fun processIntent(intent: AllWishesIntent) {
      intentChannel.send(intent)
  }

  init {
      val intentFlow = intentChannel.asFlow()
      merge(
          intentFlow.filterIsInstance<AllWishesIntent.InitialIntent>().take(1),
          intentFlow.filterNot {
              it is AllWishesIntent.InitialIntent
          }
      )
          .toActionFlow()
          .processResult()
          .reduceToStateFlow()
          .launchIn(viewModelScope)
  }

  private fun <T: AllWishesResult> Flow<T>.reduceToStateFlow(): Flow<T> {
      return onEach {
          state = when (it) {
              is AllWishesResult.GetAllWishesResult.Loading -> state.copy(
                  isLoading = true,
                  wishes = listOf(),
                  error = null
              )
              is AllWishesResult.GetAllWishesResult.Success -> state.copy(
                  isLoading = false,
                  wishes = it.wishes,
                  error = null
              )
              is AllWishesResult.GetAllWishesResult.Failure -> state.copy(
                  isLoading = false,
                  wishes = listOf(),
                  error = it.error
              )
              else -> state
          }
      }
  }

  private fun <T: AllWishesIntent> Flow<T>.toActionFlow(): Flow<AllWishesAction> {
      return merge(
          filterIsInstance<AllWishesIntent.InitialIntent>()
              .flatMapConcat { flow {
                  Timber.v("filterIsInstance")
                  logAction(AllWishesAction.GetAllWishesAction)
                  emit(AllWishesAction.GetAllWishesAction)
              } },
          filterIsInstance<AllWishesIntent.GetAllWishesIntent>()
              .flatMapConcat { flow {
                  logAction(AllWishesAction.GetAllWishesAction)
                  emit(AllWishesAction.GetAllWishesAction)
              } }
      )
  }

  private fun <T: AllWishesAction> Flow<T>.processResult(): Flow<AllWishesResult> {
      val getAllWishes = flow {
          emit(getWishesListUseCase.execute())
      }
          .map {
              AllWishesResult.GetAllWishesResult.Success(it) as AllWishesResult.GetAllWishesResult
          }
          .onStart {
              emit(AllWishesResult.GetAllWishesResult.Loading)
          }
          .catch {
              emit(AllWishesResult.GetAllWishesResult.Failure(it))
          }

      return merge(
          filterIsInstance<AllWishesAction>()
              .flatMapConcat {
                  getAllWishes
              }
      )
  }
}


