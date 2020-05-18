package com.aptenobytes.bob.feature.profile.presentation.setuserstatus

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.app.domain.enums.userstatus.UserStatusType
import com.aptenobytes.bob.app.domain.model.user.UserDomainModel
import com.aptenobytes.bob.feature.profile.R
import com.aptenobytes.bob.feature.profile.presentation.setuserstatus.recyclerview.UserStatusViewHolder
import com.aptenobytes.bob.feature.profile.presentation.setuserstatus.recyclerview.UserStatusViewModel
import com.aptenobytes.bob.feature.profile.presentation.setuserstatus.recyclerview.toViewModel
import com.aptenobytes.bob.feature.profile.presentation.setuserstatus.recyclerview.userStatusAdapter
import com.aptenobytes.bob.library.base.extensions.ui.action
import com.aptenobytes.bob.library.base.extensions.ui.snack
import com.aptenobytes.bob.library.base.presentation.bottomsheetdialogfragment.BaseContainerBottomSheetDialogFragment
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.presentation.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.presentation.recyclerview.builder.recycleView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_set_user_status.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance

class SetUserStatusFragment(
    val userId: Long,
    val onChangeStatusListener: ((status: UserDomainModel?) -> Unit)? = null
) : BaseContainerBottomSheetDialogFragment(), SetUserStatusView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: SetUserStatusViewModel by instance<SetUserStatusViewModel>()

    override val layoutResourceId = R.layout.fragment_set_user_status

    private lateinit var adapter: RecyclerViewAdapter<UserStatusViewModel, UserStatusViewHolder>

    private val stateObserver = Observer<SetUserStatusViewState> { render(it) }

    companion object{
        val TAG = "SET_USERS"
        fun newInstance(
            userId: Long,
            onChangeStatusListener: ((status: UserDomainModel?) -> Unit)? = null
        ): SetUserStatusFragment {
            return SetUserStatusFragment(userId = userId, onChangeStatusListener = onChangeStatusListener)
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NO_FRAME,0);
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireContext()
        setupUsersStatusList()
        progressBar.visibility = GONE
        bind()
    }

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // setup full height
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog: DialogInterface ->
            val bottomSheet =
                (dialog as BottomSheetDialog).findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
        }
        return bottomSheetDialog

    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun setupUsersStatusList() {
        val context = requireContext()
        adapter = userStatusAdapter(
            lifecycleOwner = viewLifecycleOwner,
            onDebouncedClickListener = {status ->
                status?.let {
                    flowOf(SetUserStatusIntent.SetStatusIntent(userId = userId, status = status))
                        .onEach { intent -> viewModel.processIntent(intent) }
                        .launchIn(lifecycleScope)
                }
            }
        )
        recycleView(
            recyclerView,
            LinearLayoutManager(context),
            adapter
        ).apply {
            setHasFixedSize(true)
        }
        adapter.items = arrayListOf(
            UserStatusType.ACTIVE.toViewModel(),
            UserStatusType.INACTIVE.toViewModel()
        )
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf<SetUserStatusIntent>()
    )

    private fun render(viewState: SetUserStatusViewState) {
        progressBar.visibility = if (viewState.isLoading) View.VISIBLE else GONE
        recyclerView.visibility = if (viewState.error == null && !viewState.isLoading) View.VISIBLE else GONE
        if (viewState.error != null) {
            this.view?.snack(message = "Failed to changed status!", length = Snackbar.LENGTH_LONG) {
                this.view.setBackgroundColor(ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_red))
                action("Close", ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.white)) {
                    this.dismiss()
                }
            }
            this.dismiss()
        }
        viewState.user?.let { user ->
            onChangeStatusListener?.invoke(user)
            val rootView: View? = activity?.window?.decorView?.findViewById(android.R.id.content)
            rootView?.snack(message = "Status changed!", length = Snackbar.LENGTH_LONG) {
                this.view.setBackgroundColor(ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_green))
                action("Close", ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.white)) {
                    this.dismiss()
                }
            }
            this.dismiss()
        }
    }

}
