package id.gagahib.core.ext

import android.app.Service
import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import id.gagahib.core.utils.Event
import id.gagahib.core.R

fun View.showKeyboard() {
    (context?.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    (context?.getSystemService(Service.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.showError(message: String){
    this.error = message
    this.setSelection(this.editableText.length)
}

fun View.toVisible() {
    this.visibility = View.VISIBLE
}

fun View.toGone() {
    this.visibility = View.GONE
}

fun View.toInvisible() {
    this.visibility = View.INVISIBLE
}


/**
 * Transforms static java function Snackbar.make() to an extension function on View.
 */
fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).run {
        addCallback(object : Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {
            }

            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            }
        })
        show()
    }
}

/**
 * Triggers a snackbar message when the value contained by snackbarTaskMessageLiveEvent is modified.
 */
fun View.setupSnackbar(
        lifecycleOwner: LifecycleOwner,
        snackbarEvent: LiveData<String>,
        timeLength: Int
) {
    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        showSnackbar(event, timeLength)
    })
}

fun View.showToast(
    lifecycleOwner: LifecycleOwner,
    snackbarEvent: LiveData<Event<Any>>,
    timeLength: Int
) {

    snackbarEvent.observe(lifecycleOwner, Observer { event ->
        event.getContentIfNotHandled()?.let {
            when (it) {
                is String -> Toast.makeText(context, it, timeLength).show()
                is Int -> Toast.makeText(context, context.getString(it), timeLength).show()
                else -> {
                }
            }
        }
    })
}

fun ImageView.loadImage(@DrawableRes resId: Int) =
    Glide.with(context)
        .load(resId)
        .placeholder(R.color.colorPlaceHolder)
        .into(this)
fun ImageView.loadImage(url: String) =
    Glide.with(context)
        .load(url)
        .placeholder(R.color.colorPlaceHolder)
        .into(this)

fun AppCompatTextView.setTextFutureExt(text: String) =
        setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                        text,
                        TextViewCompat.getTextMetricsParams(this),
                        null
                )
        )

fun AppCompatEditText.setTextFutureExt(text: String) =
        setText(
                PrecomputedTextCompat.create(text, TextViewCompat.getTextMetricsParams(this))
        )

fun TextView.setCustomFonts(context: Context) {
    this.typeface = Typeface.createFromAsset(context.assets, "fonts/PoynterText_Regular.ttf")
}

