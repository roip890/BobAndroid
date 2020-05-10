package com.aptenobytes.bob.feature.wish.presentation.setwishstatus

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.aptenobytes.bob.feature.wish.R
import com.aptenobytes.bob.feature.wish.domain.enums.wishstatus.WishStatusType
import com.aptenobytes.bob.feature.wish.domain.model.wish.WishDomainModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.WishStatusViewHolder
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.WishStatusViewModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.toViewModel
import com.aptenobytes.bob.feature.wish.presentation.setwishstatus.recyclerview.wishStatusAdapter
import com.aptenobytes.bob.library.base.extensions.ui.action
import com.aptenobytes.bob.library.base.extensions.ui.snack
import com.aptenobytes.bob.library.base.presentation.fragment.BaseContainerFragment
import com.aptenobytes.bob.library.base.recyclerview.adapter.RecyclerViewAdapter
import com.aptenobytes.bob.library.base.recyclerview.builder.recycleView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.fragment_wish_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import org.kodein.di.generic.instance

class SetWishStatusFragment(
    val wish: WishDomainModel,
    val onChangeStatusListener: ((status: WishDomainModel?) -> Unit)? = null
) : BaseContainerFragment(), SetWishStatusView {

    @ExperimentalCoroutinesApi
    @FlowPreview
    private val viewModel: SetWishStatusViewModel by instance<SetWishStatusViewModel>()

    override val layoutResourceId = R.layout.fragment_set_wish_status

    private lateinit var adapter: RecyclerViewAdapter<WishStatusViewModel, WishStatusViewHolder>

    private val stateObserver = Observer<SetWishStatusViewState> { render(it) }

    companion object{
        val TAG = "SET_WISHES"
        fun newInstance(
            wish: WishDomainModel,
            onChangeStatusListener: ((status: WishDomainModel?) -> Unit)? = null
        ): SetWishStatusFragment {
            return SetWishStatusFragment(wish = wish, onChangeStatusListener = onChangeStatusListener)
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
        setupWishesList()
        progressBar.visible = false
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
    private fun setupWishesList() {
        val context = requireContext()
        adapter = wishStatusAdapter(
            lifecycleOwner = viewLifecycleOwner,
            onDebouncedClickListener = {status ->
                status?.let {
                    flowOf(SetWishStatusIntent.SetStatusIntent(wish = wish, status = status))
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
        adapter.items = listOf(
            WishStatusType.WAITING.toViewModel(),
            WishStatusType.PENDING.toViewModel(),
            WishStatusType.IN_PROGRESS.toViewModel(),
            WishStatusType.DONE.toViewModel()
        )
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    private fun bind() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, stateObserver)
    }

    @ExperimentalCoroutinesApi
    override fun intents() = merge(
        flowOf<SetWishStatusIntent>()
    )

    private fun render(viewState: SetWishStatusViewState) {
        progressBar.visible = viewState.isLoading
        recyclerView.visible = viewState.error == null && !viewState.isLoading
        if (viewState.error != null) {
            this.view?.snack(message = "Failed to changed status!", length = Snackbar.LENGTH_LONG) {
                this.view.setBackgroundColor(ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.faded_red))
                action("Close", ContextCompat.getColor(requireContext(), com.aptenobytes.bob.R.color.white)) {
                    this.dismiss()
                }
            }
            this.dismiss()
        }
        viewState.wish?.let { wish ->
            onChangeStatusListener?.invoke(wish)
//            val c: ConstraintLayout? = activity?.findViewById(com.aptenobytes.bob.R.id.navHostActivityContainer)
//            c?.let { Snackbar.make(it, "Status changed!", Snackbar.LENGTH_LONG) }?.show()
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
