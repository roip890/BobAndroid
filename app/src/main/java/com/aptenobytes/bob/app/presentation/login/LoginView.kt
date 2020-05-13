package com.aptenobytes.bob.app.presentation.login

import kotlinx.coroutines.flow.Flow

interface LoginView {
    fun intents(): Flow<LoginIntent>
  }
