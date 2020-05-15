package com.aptenobytes.bob.feature.auth.presentation.login

import kotlinx.coroutines.flow.Flow

interface LoginView {
    fun intents(): Flow<LoginIntent>
  }
