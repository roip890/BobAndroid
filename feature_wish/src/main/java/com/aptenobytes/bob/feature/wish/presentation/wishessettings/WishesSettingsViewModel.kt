package com.aptenobytes.bob.feature.wish.presentation.wishessettings

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.usecase.GetDepartmentsListUseCase
import com.aptenobytes.bob.feature.wish.domain.model.wishessettings.WishesSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishesSettingsUseCase
import com.aptenobytes.bob.feature.wish.domain.usecase.SetWishesSettingsUseCase
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
    private val getDepartmentsListUseCase: GetDepartmentsListUseCase,
    private val getWishesSettingsUseCase: GetWishesSettingsUseCase,
    private val setWishesSettingsUseCase: SetWishesSettingsUseCase
) : BaseViewModel<WishesSettingsViewState, WishesSettingsAction>(WishesSettingsViewState.initial()) {

    private val intentChannel = BroadcastChannel<WishesSettingsIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: WishesSettingsIntent) {
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

    private fun <T: WishesSettingsIntent> Flow<T>.toActionFlow(): Flow<WishesSettingsAction> {
        return merge(
            filterIsInstance<WishesSettingsIntent.GetDepartmentsListIntent>()
                .flatMapConcat { flow {
                    logAction(WishesSettingsAction.GetDepartmentsListAction)
                    emit(WishesSettingsAction.GetDepartmentsListAction)
                } },
            filterIsInstance<WishesSettingsIntent.GetWishesSettingsIntent>()
                .flatMapConcat { flow {
                    logAction(WishesSettingsAction.GetWishesSettingsAction)
                    emit(WishesSettingsAction.GetWishesSettingsAction)
                } },
            filterIsInstance<WishesSettingsIntent.SetWishesSettingsIntent>()
                .flatMapConcat { flow {
                    logAction(WishesSettingsAction.SetWishesSettingsAction(wishesSettings = it.wishesSettings))
                    emit(WishesSettingsAction.SetWishesSettingsAction(wishesSettings = it.wishesSettings))
                } }
        )
    }

    private fun <T: WishesSettingsAction> Flow<T>.processResult(): Flow<WishesSettingsResult> {
        return merge(
            filterIsInstance<WishesSettingsAction.GetDepartmentsListAction>()
                .flatMapConcat {
                    processGetDepartmentsList()
                },
            filterIsInstance<WishesSettingsAction.GetWishesSettingsAction>()
                .flatMapConcat {
                    processGetWishesSettings()
                },
            filterIsInstance<WishesSettingsAction.SetWishesSettingsAction>()
                .flatMapConcat {
                    processSetWishesSettings(it.wishesSettings)
                }
        )
    }

    private fun processGetDepartmentsList(): Flow<WishesSettingsResult.GetDepartmentsListResult> {
        return flow {
            emit(getDepartmentsListUseCase.execute())
        }
            .map {
                WishesSettingsResult.GetDepartmentsListResult.Success(it) as WishesSettingsResult.GetDepartmentsListResult
            }
            .onStart {
                emit(WishesSettingsResult.GetDepartmentsListResult.Loading)
            }
            .catch {
                emit(WishesSettingsResult.GetDepartmentsListResult.Failure(it))
            }
    }

    private fun processGetWishesSettings(): Flow<WishesSettingsResult.GetWishesSettingsResult> {
        return flow {
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
    }

    private fun processSetWishesSettings(wishesSettings: WishesSettingsDomainModel): Flow<WishesSettingsResult.SetWishesSettingsResult> {
        return flow {
            emit(setWishesSettingsUseCase.execute(wishesSettings))
        }
            .map {
                WishesSettingsResult.SetWishesSettingsResult.Success(it) as WishesSettingsResult.SetWishesSettingsResult
            }
            .onStart {
                emit(WishesSettingsResult.SetWishesSettingsResult.Loading)
            }
            .catch {
                emit(WishesSettingsResult.SetWishesSettingsResult.Failure(it))
            }
    }

    private fun <T: WishesSettingsResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                // get departments
                is WishesSettingsResult.GetDepartmentsListResult.Loading -> state.copy(
                    isLoading = true,
                    departments = listOf<DepartmentDomainModel>(),
                    error = null
                )
                is WishesSettingsResult.GetDepartmentsListResult.Success -> state.copy(
                    isLoading = false,
                    departments = it.departments,
                    error = null
                )
                is WishesSettingsResult.GetDepartmentsListResult.Failure -> state.copy(
                    isLoading = false,
                    departments = listOf<DepartmentDomainModel>(),
                    error = it.error
                )
                // get settings
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
                // set settings
                is WishesSettingsResult.SetWishesSettingsResult.Loading -> state.copy(
                    isLoading = true,
                    wishesSettings = WishesSettingsDomainModel(),
                    error = null
                )
                is WishesSettingsResult.SetWishesSettingsResult.Success -> state.copy(
                    isLoading = false,
                    wishesSettings = it.wishesSettings,
                    error = null
                )
                is WishesSettingsResult.SetWishesSettingsResult.Failure -> state.copy(
                    isLoading = false,
                    wishesSettings = WishesSettingsDomainModel(),
                    error = it.error
                )
                else -> state
            }
        }
    }
}

