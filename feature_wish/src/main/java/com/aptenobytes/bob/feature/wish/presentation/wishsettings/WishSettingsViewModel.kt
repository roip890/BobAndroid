package com.aptenobytes.bob.feature.wish.presentation.wishsettings

import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.app.domain.model.department.DepartmentDomainModel
import com.aptenobytes.bob.app.domain.usecase.GetDepartmentsListUseCase
import com.aptenobytes.bob.feature.wish.domain.model.wishsettings.WishSettingsDomainModel
import com.aptenobytes.bob.feature.wish.domain.usecase.GetWishSettingsUseCase
import com.aptenobytes.bob.feature.wish.domain.usecase.SetWishSettingsUseCase
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
class WishSettingsViewModel(
    private val getDepartmentsListUseCase: GetDepartmentsListUseCase,
    private val getWishSettingsUseCase: GetWishSettingsUseCase,
    private val setWishSettingsUseCase: SetWishSettingsUseCase
) : BaseViewModel<WishSettingsViewState, WishSettingsAction>(WishSettingsViewState.initial()) {

    private val intentChannel = BroadcastChannel<WishSettingsIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: WishSettingsIntent) {
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

    private fun <T: WishSettingsIntent> Flow<T>.toActionFlow(): Flow<WishSettingsAction> {
        return merge(
            filterIsInstance<WishSettingsIntent.GetDepartmentsListIntent>()
                .flatMapConcat { flow {
                    logAction(WishSettingsAction.GetDepartmentsListAction)
                    emit(WishSettingsAction.GetDepartmentsListAction)
                } },
            filterIsInstance<WishSettingsIntent.GetWishSettingsIntent>()
                .flatMapConcat { flow {
                    logAction(WishSettingsAction.GetWishSettingsAction)
                    emit(WishSettingsAction.GetWishSettingsAction)
                } },
            filterIsInstance<WishSettingsIntent.SetWishSettingsIntent>()
                .flatMapConcat { flow {
                    logAction(WishSettingsAction.SetWishSettingsAction(wishSettings = it.wishSettings))
                    emit(WishSettingsAction.SetWishSettingsAction(wishSettings = it.wishSettings))
                } }
        )
    }

    private fun <T: WishSettingsAction> Flow<T>.processResult(): Flow<WishSettingsResult> {
        return merge(
            filterIsInstance<WishSettingsAction.GetDepartmentsListAction>()
                .flatMapConcat {
                    processGetDepartmentsList()
                },
            filterIsInstance<WishSettingsAction.GetWishSettingsAction>()
                .flatMapConcat {
                    processGetWishSettings()
                },
            filterIsInstance<WishSettingsAction.SetWishSettingsAction>()
                .flatMapConcat {
                    processSetWishSettings(it.wishSettings)
                }
        )
    }

    private fun processGetDepartmentsList(): Flow<WishSettingsResult.GetDepartmentsListResult> {
        return flow {
            emit(getDepartmentsListUseCase.execute())
        }
            .map {
                WishSettingsResult.GetDepartmentsListResult.Success(it) as WishSettingsResult.GetDepartmentsListResult
            }
            .onStart {
                emit(WishSettingsResult.GetDepartmentsListResult.Loading)
            }
            .catch {
                emit(WishSettingsResult.GetDepartmentsListResult.Failure(it))
            }
    }

    private fun processGetWishSettings(): Flow<WishSettingsResult.GetWishSettingsResult> {
        return flow {
            emit(getWishSettingsUseCase.execute())
        }
            .map {
                WishSettingsResult.GetWishSettingsResult.Success(it) as WishSettingsResult.GetWishSettingsResult
            }
            .onStart {
                emit(WishSettingsResult.GetWishSettingsResult.Loading)
            }
            .catch {
                emit(WishSettingsResult.GetWishSettingsResult.Failure(it))
            }
    }

    private fun processSetWishSettings(wishSettings: WishSettingsDomainModel): Flow<WishSettingsResult.SetWishSettingsResult> {
        return flow {
            emit(setWishSettingsUseCase.execute(wishSettings))
        }
            .map {
                WishSettingsResult.SetWishSettingsResult.Success(it) as WishSettingsResult.SetWishSettingsResult
            }
            .onStart {
                emit(WishSettingsResult.SetWishSettingsResult.Loading)
            }
            .catch {
                emit(WishSettingsResult.SetWishSettingsResult.Failure(it))
            }
    }

    private fun <T: WishSettingsResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                // get departments
                is WishSettingsResult.GetDepartmentsListResult.Loading -> state.copy(
                    isLoading = true,
                    departments = listOf<DepartmentDomainModel>(),
                    error = null
                )
                is WishSettingsResult.GetDepartmentsListResult.Success -> state.copy(
                    isLoading = false,
                    departments = it.departments,
                    error = null
                )
                is WishSettingsResult.GetDepartmentsListResult.Failure -> state.copy(
                    isLoading = false,
                    departments = listOf<DepartmentDomainModel>(),
                    error = it.error
                )
                // get settings
                is WishSettingsResult.GetWishSettingsResult.Loading -> state.copy(
                    isLoading = true,
                    wishSettings = WishSettingsDomainModel(),
                    error = null
                )
                is WishSettingsResult.GetWishSettingsResult.Success -> state.copy(
                    isLoading = false,
                    wishSettings = it.wishSettings,
                    error = null
                )
                is WishSettingsResult.GetWishSettingsResult.Failure -> state.copy(
                    isLoading = false,
                    wishSettings = WishSettingsDomainModel(),
                    error = it.error
                )
                // set settings
                is WishSettingsResult.SetWishSettingsResult.Loading -> state.copy(
                    isLoading = true,
                    wishSettings = WishSettingsDomainModel(),
                    error = null
                )
                is WishSettingsResult.SetWishSettingsResult.Success -> state.copy(
                    isLoading = false,
                    wishSettings = it.wishSettings,
                    error = null
                )
                is WishSettingsResult.SetWishSettingsResult.Failure -> state.copy(
                    isLoading = false,
                    wishSettings = WishSettingsDomainModel(),
                    error = it.error
                )
                else -> state
            }
        }
    }
}

