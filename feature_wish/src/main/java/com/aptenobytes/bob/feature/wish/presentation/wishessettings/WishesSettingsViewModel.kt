package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishesSettingsUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class WishesSettingsViewModel(
    private val getWishesSettingsUseCase: GetWishesSettingsUseCase
) : BaseViewModel<WishesSettingsViewState, WishesSettingsAction>(WishesSettingsViewState.initial()) {

  private val intentChannel = BroadcastChannel<WishesSettingsIntent>(capacity = Channel.CONFLATED)
  suspend fun processIntent(intent: WishesSettingsIntent) {
      intentChannel.send(intent)
  }

  init {
      val intentFlow = intentChannel.asFlow()
      merge(
          intentFlow.filterIsInstance<WishesSettingsIntent.InitialIntent>().take(1),
          intentFlow.filterNot {
              it is WishesSettingsIntent.InitialIntent
          }
      )
          .toActionFlow()
          .processResult()
          .reduceToStateFlow()
          .launchIn(viewModelScope)
  }

  private fun <T: WishesSettingsResult> Flow<T>.reduceToStateFlow(): Flow<T> {
      return onEach {
          state = when (it) {
              is WishesSettingsResult.GetWishesSettingsResult.Loading -> state.copy(
                  isLoading = true,
                  wishesSettings = WishesSettingsDomainModel(),
                  error = null
              )
              is WishesSettingsResult.GetWishesSettingsResult.Success -> state.copy(
                  isLoading = false,
                  wishesSettings = it.wishesSettings,
                  error = null
              )
              is WishesSettingsResult.GetWishesSettingsResult.Failure -> state.copy(
                  isLoading = false,
                  wishesSettings = WishesSettingsDomainModel(),
                  error = it.error
              )
              else -> state
          }
      }
  }

  private fun <T: WishesSettingsIntent> Flow<T>.toActionFlow(): Flow<WishesSettingsAction> {
      return merge(
          filterIsInstance<WishesSettingsIntent.InitialIntent>()
              .flatMapConcat { flow {
                  Timber.v("filterIsInstance")
                  logAction(WishesSettingsAction.GetWishesSettingsAction)
                  emit(WishesSettingsAction.GetWishesSettingsAction)
              } },
          filterIsInstance<WishesSettingsIntent.GetWishesSettingsIntent>()
              .flatMapConcat { flow {
                  logAction(WishesSettingsAction.GetWishesSettingsAction)
                  emit(WishesSettingsAction.GetWishesSettingsAction)
              } }
      )
  }

  private fun <T: WishesSettingsAction> Flow<T>.processResult(): Flow<WishesSettingsResult> {
      val getWishesSettings = flow {
          emit(getWishesSettingsUseCase.execute())
      }
          .map {
              WishesSettingsResult.GetWishesSettingsResult.Success(it) as WishesSettingsResult.GetWishesSettingsResult
          }
          .onStart {
              emit(WishesSettingsResult.GetWishesSettingsResult.Loading)
          }
          .catch {
              emit(WishesSettingsResult.GetWishesSettingsResult.Failure(it))
          }

      return merge(
          filterIsInstance<WishesSettingsAction>()
              .flatMapConcat {
                  getWishesSettings
              }
      )
  }
}


