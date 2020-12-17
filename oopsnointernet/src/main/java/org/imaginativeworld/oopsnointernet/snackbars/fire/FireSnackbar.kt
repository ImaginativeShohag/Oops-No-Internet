package org.imaginativeworld.oopsnointernet.snackbars.fire

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import org.imaginativeworld.oopsnointernet.databinding.SnackbarFireBinding

class FireSnackbar private constructor(
    parent: ViewGroup,
    private val binding: SnackbarFireBinding,
    callback: com.google.android.material.snackbar.ContentViewCallback
) : BaseTransientBottomBar<FireSnackbar>(parent, binding.root, callback) {

    private var hasAction: Boolean = false

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)

        val layoutParams = getView().layoutParams
        if (layoutParams is MarginLayoutParams) {
            layoutParams.setMargins(0, 0, 0, 0)
        }
        getView().layoutParams = layoutParams

        binding.btnAction.visibility = View.GONE
    }

    companion object {

        /**
         * Make a Snackbar to display a message
         *
         * <p>Snackbar will try and find a parent view to hold Snackbar's view from the value given to
         * {@code view}. Snackbar will walk up the view tree trying to find a suitable parent, which is
         * defined as a {@link CoordinatorLayout} or the window decor's content view, whichever comes
         * first.
         *
         * <p>Having a {@link CoordinatorLayout} in your view hierarchy allows Snackbar to enable certain
         * features, such as swipe-to-dismiss and automatically moving of widgets.
         *
         * @param view The view to find a parent from. This view is also used to find the anchor view when
         *     calling {@link Snackbar#setAnchorView(int)}.
         * @param text The text to show. Can be formatted text.
         * @param duration How long to display the message. Can be {@link #LENGTH_SHORT}, {@link
         *     #LENGTH_LONG}, {@link #LENGTH_INDEFINITE}, or a custom duration in milliseconds.
         */
        fun make(
            @NonNull view: View,
            @NonNull text: CharSequence,
            @Duration duration: Int
        ): FireSnackbar {

            val parent = view.findSuitableParent()
                ?: throw IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view."
                )

            val inflater = LayoutInflater.from(parent.context)
            val binding = SnackbarFireBinding.inflate(inflater, parent, false)

            val snackbar = FireSnackbar(
                parent,
                binding,
                object : com.google.android.material.snackbar.ContentViewCallback {
                    override fun animateContentIn(delay: Int, duration: Int) {
                        /* no-op */
                    }

                    override fun animateContentOut(delay: Int, duration: Int) {
                        /* no-op */
                    }

                })
            snackbar.setText(text)
            snackbar.duration = duration
            snackbar.animationMode = ANIMATION_MODE_FADE
            snackbar.addCallback(object : BaseCallback<FireSnackbar>() {
                override fun onDismissed(transientBottomBar: FireSnackbar?, event: Int) {

                }

                override fun onShown(transientBottomBar: FireSnackbar?) {

                }
            })

            return snackbar
        }

        /**
         * Find a suitable parent for the Snackbar.
         */
        private fun View?.findSuitableParent(): ViewGroup? {
            var view: View? = this
            var fallback: ViewGroup? = null
            do {
                if (view is CoordinatorLayout) {
                    // We've found a CoordinatorLayout, use it
                    return view
                } else if (view is FrameLayout) {
                    if (view.getId() == android.R.id.content) {
                        // If we've hit the decor content view, then we didn't find a CoL in the
                        // hierarchy, so use it.
                        return view
                    } else {
                        // It's not the content view but we'll use it as our fallback
                        fallback = view
                    }
                }
                if (view != null) {
                    // Else, we will loop and crawl up the view hierarchy and try to find a parent
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)

            // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
            return fallback
        }

    }

    /**
     * Update the text in this {@link Snackbar}.
     *
     * @param text The new text for this {@link BaseTransientBottomBar}.
     */
    fun setText(text: CharSequence): FireSnackbar {
        binding.tvMessage.text = text
        return this
    }

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param text Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    fun setAction(
        @Nullable text: CharSequence,
        @Nullable listener: View.OnClickListener?
    ): FireSnackbar {
        val actionView = binding.btnAction
        if (TextUtils.isEmpty(text) || listener == null) {
            binding.root.visibility = View.GONE
            actionView.setOnClickListener(null)
            hasAction = false
        } else {
            hasAction = true
            actionView.text = text
            actionView.visibility = View.VISIBLE
            actionView.setOnClickListener {
                listener.onClick(view)

                dispatchDismiss(BaseCallback.DISMISS_EVENT_ACTION)
            }
        }
        return this
    }

    /**
     * Set the action to be displayed in this {@link BaseTransientBottomBar}.
     *
     * @param text Text to display for the action
     * @param listener callback to be invoked when the action is clicked
     */
    fun setAction(
        @Nullable text: CharSequence,
        @Nullable listener: (View) -> Unit
    ): FireSnackbar {
        return setAction(
            text,
            View.OnClickListener {
                listener.invoke(view)
            }
        )
    }

}