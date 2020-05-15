package com.aptenobytes.bob.feature.auth.presentation.login

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph
import androidx.navigation.dynamicfeatures.fragment.DynamicNavHostFragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.aptenobytes.bob.R
import com.aptenobytes.bob.feature.auth.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.auth.presentation.NavHostActivity
import com.aptenobytes.bob.databinding.FragmentLoginBinding
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome
import com.mikepenz.iconics.utils.sizeDp
import kotlinx.android.synthetic.main.activity_nav_host.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance
import timber.log.Timber


class LoginFragment() : BaseContainerFragment(), LoginView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: LoginViewModel by instance<LoginViewModel>()

    override val layoutResourceId = R.layout.fragment_login

    lateinit var binding: FragmentLoginBinding

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val stateObserver = Observer<LoginViewState> { render(it) }

    companion object{
        val TAG = "LOGIN"
        fun newInstance(): LoginFragment{
            return LoginFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return this.view?.let {
            this.view
        } ?: run {
            binding = FragmentLoginBinding.inflate(inflater, null, false).also {
                Timber.v("onCreateView ${javaClass.simpleName}")
            }
            binding.lifecycleOwner = viewLifecycleOwner
            binding.root
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //super.onCreate(savedInstanceState)
        requireContext()
        setupForgotPassword()
        setupSocialIcons()
        setupGoToSignUp()
        setupSubmitButton()
        bindForm()
        bind()
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun setupSubmitButton() {
        loginSubmitButton.setOnClickListener {
            submitForm()
        }
    }

    private fun setupForgotPassword() {
        val spannableStringBuilder = SpannableStringBuilder()
        val forgotPasswordSpannable = SpannableString("Forgot Password?")
        val boldSpan = StyleSpan(Typeface.BOLD)
        forgotPasswordSpannable.setSpan(boldSpan, 0, forgotPasswordSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableStringBuilder.append(forgotPasswordSpannable)
        forgotPassword.text = spannableStringBuilder
    }

    private fun setupGoToSignUp() {
        val spannableStringBuilder = SpannableStringBuilder()

        val haveAccountSpannable = SpannableString("Don't have account yet?")

        val signUpSpannable = SpannableString("Sign Up")
        val boldSpan = StyleSpan(Typeface.BOLD)
        val signUpClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.isUnderlineText = false
            }
        }
        signUpSpannable.setSpan(boldSpan, 0, signUpSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        signUpSpannable.setSpan(signUpClickableSpan, 0, signUpSpannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableStringBuilder.append(haveAccountSpannable)
        spannableStringBuilder.append(" ")
        spannableStringBuilder.append(signUpSpannable)
        goToSignUp.text = spannableStringBuilder
    }

    private fun setupSocialIcons() {
        // facebook
        facebookButton.setImageDrawable(
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_facebook_f).apply {
                sizeDp = 48
            }
        )

        // twitter
        twitterButton.setImageDrawable(
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_twitter).apply {
                sizeDp = 48
            }
        )

        // facebook
        googleButton.setImageDrawable(
            IconicsDrawable(requireContext(), FontAwesome.Icon.faw_google_plus_g).apply {
                sizeDp = 48
            }
        )
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bindForm() {
        viewModel.email.observe(viewLifecycleOwner, Observer { text ->
            viewModel.emailError.postValue(viewModel.validateEmail(text))
        })
        viewModel.password.observe(viewLifecycleOwner, Observer { text ->
            viewModel.passwordError.postValue(viewModel.validatePassword(text))
        })
        viewModel.loginFormValid.addSource(viewModel.emailError) {
            viewModel.loginFormValid.value = viewModel.passwordError.value == null && viewModel.emailError.value == null
        }
        viewModel.loginFormValid.addSource(viewModel.passwordError) {
            viewModel.loginFormValid.value = viewModel.emailError.value == null && viewModel.passwordError.value == null
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        binding.viewModel = viewModel
        // dummy
        binding.viewModel?.email?.postValue("r@r.r")
        binding.viewModel?.password?.postValue("Aa111111")

        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
        intents()
            .onEach { viewModel.processIntent(it) }
            .launchIn(lifecycleScope)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf<LoginIntent>()
    )

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun render(viewState: LoginViewState) {
        viewState.user?.let {
            viewModel.goToBottomNav()
//            val activity = activity as? NavHostActivity
//            activity?.let {
//                activity.navHostFragment?.findNavController()?.navInflater?.inflate(R.navigation.bottom_nav_graph)?.let { navGraph ->
//                    activity.navHostFragment.findNavController().graph = navGraph
//                    activity.appBarLayout.visibility = VISIBLE
//                    activity.bottomNav.visibility = VISIBLE
//                }
//            }
        } ?: run {
            loginSubmitButton.visibility = if (!viewState.isLoading) VISIBLE else View.GONE
            progressBar.visibility = if (viewState.isLoading) VISIBLE else View.GONE
            loginError.visibility = if (viewState.error != null) VISIBLE else View.GONE
            viewModel.displayError.postValue(viewState.error?.localizedMessage)
        }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    @Suppress("UNCHECKED_CAST")
    private fun submitForm() {
        viewModel.loginFormValid.value?.let { isLoginFormValid ->
            if (isLoginFormValid) {

                val user = UserDomainModel(
                    email = viewModel.email.value,
                    password = viewModel.password.value
                )

                flowOf(LoginIntent.EmailLoginIntent(user = user))
                    .onEach { viewModel.processIntent(it) }
                    .launchIn(lifecycleScope)

            }
        }
    }


}
