package com.aptenobytes.bob.feature.auth.presentation.login

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.auth.domain.usecase.EmailLoginUseCase
import com.aptenobytes.bob.library.base.presentation.navigation.NavManager
import com.aptenobytes.bob.library.base.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import java.util.regex.Pattern
import java.util.regex.Pattern.compile

@FlowPreview
@ExperimentalCoroutinesApi
class LoginViewModel(
    private val navManager: NavManager,
    private val emailLoginUseCase: EmailLoginUseCase
) : BaseViewModel<LoginViewState, LoginAction>(LoginViewState.initial()) {

    val email = MutableLiveData<String>()
    val emailError = MutableLiveData<String>("")
    val password = MutableLiveData<String>()
    val passwordError = MutableLiveData<String>("")
    val displayError = MutableLiveData<String>()
    val loginFormValid = MediatorLiveData<Boolean>()

    companion object {
        val PASSWORD_PATTERN: Pattern = compile("" +
                "^(?=.*[0-9])" +
                "(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=\\S+\$)" +
                ".{8,}")
        val EMAIL_PATTERN: Pattern = compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+")
    }

    fun validateEmail(text: String?): String? {
        return if (text == null || text.isEmpty()) {
            "Email required!"
        } else if (!EMAIL_PATTERN.matcher(text).matches()) {
            "Please enter valid email!"
        } else {
            null
        }
    }

    fun validatePassword(text: String?): String? {
        return if (text == null || text.isEmpty()) {
            "Password required!"
        } else if (!PASSWORD_PATTERN.matcher(text).matches()) {
            "Please enter valid password!"
        } else {
            null
        }
    }

    fun goToBottomNav() {
        navManager.navigateToBottomNav()
    }

    private val intentChannel = BroadcastChannel<LoginIntent>(capacity = Channel.CONFLATED)
    suspend fun processIntent(intent: LoginIntent) {
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

    private fun <T: LoginIntent> Flow<T>.toActionFlow(): Flow<LoginAction> {
        return merge(
            filterIsInstance<LoginIntent.EmailLoginIntent>()
                .flatMapConcat { flow {
                    logAction(LoginAction.EmailLoginAction(user = it.user))
                    emit(LoginAction.EmailLoginAction(user = it.user))
                } }
        )
    }

    private fun <T: LoginAction> Flow<T>.processResult(): Flow<LoginResult> {
        return merge(
            filterIsInstance<LoginAction.EmailLoginAction>()
                .flatMapConcat {
                    processEmailLogin(it.user)
                }
        )
    }

    private fun processEmailLogin(user: UserDomainModel): Flow<LoginResult.EmailLoginResult> {
        return flow {
            emit(emailLoginUseCase.execute(user))
        }
            .map {
                LoginResult.EmailLoginResult.Success(it) as LoginResult.EmailLoginResult
            }
            .onStart {
                emit(LoginResult.EmailLoginResult.Loading)
            }
            .catch {
                emit(LoginResult.EmailLoginResult.Failure(it))
            }
    }

    private fun <T: LoginResult> Flow<T>.reduceToStateFlow(): Flow<T> {
        return onEach {
            state = when (it) {
                // email login
                is LoginResult.EmailLoginResult.Loading -> state.copy(
                    isLoading = true,
                    user = null,
                    error = null
                )
                is LoginResult.EmailLoginResult.Success -> state.copy(
                    isLoading = false,
                    user = it.user,
                    error = null
                )
                is LoginResult.EmailLoginResult.Failure -> state.copy(
                    isLoading = false,
                    user = null,
                    error = it.error
                )
                else -> state
            }
        }
    }
}

