package com.example.bottomsheetexample

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.view.isVisible
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor

/**
 * Created by PhucPt on 29/03/2022
 */

class CalculatorKeyboard
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {
    private val decimalFormat = DecimalFormat("#.##")
    private val keyValues = SparseArray<String>()
    private val locale: Locale
    private val decimalSeparator: String

    private var editText: NumberFormatEditText? = null
    private var inputConnection: InputConnection? = null

    private var valueOne = 0.0
    private var valueTwo = 0.0
    private var valueResult = 0.0
    private var zerodisp = false
    private var decdisp = false

    private lateinit var onKeyboardVisibilityChanged: (isShow: Boolean) -> Unit

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_calculator_keyboard, this, true)

        locale = Locale.US

        val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalSeparator = decimalFormat.decimalFormatSymbols.decimalSeparator.toString()

        initializeViews()
        initializeData()
    }

    override fun onClick(view: View) {

    }

    fun registerEditText(editText: NumberFormatEditText) {
        editText.showSoftInputOnFocus = false

        editText.setOnClickListener {
            showKeyboard(editText)
        }

        editText.addOnInputFocusChangeListener {
            if (it) {
                showKeyboard(editText)
            } else {
                hideKeyboard()
            }
        }
    }

    fun isKeyboardVisible() = isVisible

    fun showKeyboard(editText: NumberFormatEditText) {
        if (isVisible) {
            return
        }

        this.editText = editText
        inputConnection = editText.onCreateInputConnection(EditorInfo())


//        txtExpression.text = null
//        txtExpression.gone()

        visibility = View.VISIBLE
        isEnabled = true

        editText.requestFocus()
        hideSystemKeyboard(editText)

        invokeOnKeyboardVisibilityChanged(true)
    }

    fun hideKeyboard() {
        editText?.clearFocus()
        editText = null
        inputConnection = null

        visibility = View.GONE
        isEnabled = false

        invokeOnKeyboardVisibilityChanged(false)
    }

    fun setOnKeyboardVisibilityChangeListener(onKeyboardVisibilityChanged: (isShow: Boolean) -> Unit) {
        this.onKeyboardVisibilityChanged = onKeyboardVisibilityChanged
    }

    // ---------------------------------------------------------------------------------------------
    private fun initializeViews() {
        // btnDecimalSeparator.text = decimalSeparator
    }

    private fun initializeData() {
        keyValues.put(R.id.btnNumber0, "0")
        keyValues.put(R.id.btnNumber1, "1")
        keyValues.put(R.id.btnNumber2, "2")
        keyValues.put(R.id.btnNumber3, "3")
        keyValues.put(R.id.btnNumber4, "4")
        keyValues.put(R.id.btnNumber5, "5")
        keyValues.put(R.id.btnNumber6, "6")
        keyValues.put(R.id.btnNumber7, "7")
        keyValues.put(R.id.btnNumber8, "8")
        keyValues.put(R.id.btnNumber9, "9")
    }


    private fun setInputText(value: Double) {
        editText?.let {
            it.setText(formatValue(value))
            it.setSelection(it.text.toString().length)
        }
    }

    private fun formatValue(value: Double): String {
        return if (value == floor(value)) {
            value.toInt().toString()
        } else {
            decimalFormat.format(value)
                .replace(".", decimalSeparator)
        }
    }

    private fun hideSystemKeyboard(editText: EditText) {
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private fun invokeOnKeyboardVisibilityChanged(isShow: Boolean) {
        if (::onKeyboardVisibilityChanged.isInitialized) {
            onKeyboardVisibilityChanged.invoke(isShow)
        }
    }

}