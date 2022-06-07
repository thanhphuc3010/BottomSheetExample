package com.example.bottomsheetexample

import android.content.Context
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor

/**
 * Created by phucpt on 4/6/2022
 */

class NumberFormatEditText
@JvmOverloads
constructor(context: Context,
            attrs: AttributeSet? = null) : AppCompatEditText(context, attrs) {
    companion object {
        private const val TAG = "NumberFormatEditText"

        val NUMBER_MIN = BigDecimal("-99999999999999")
        val NUMBER_MAX = BigDecimal("99999999999999")
    }

    lateinit var locale: Locale
        private set
    private lateinit var decimalFormat: DecimalFormat
    private lateinit var percentDecimalFormat: DecimalFormat
    private lateinit var groupingSeparator: String
    private lateinit var decimalSeparator: String

    private lateinit var onNumberChanged: (number: BigDecimal) -> Unit
    private lateinit var onNumberNullCallback: () -> Unit
    private lateinit var onKeyBackPressed: () -> Unit
    private var onInputFocusChangedList = ArrayList<(Boolean) -> Unit>()

    var formatWithCurrencySymbol = false
    var isNumberAutofill = false
    var isNumberNull = false
    var isChangeTextColorByNumber = false
    var isPercentTaxInput = false

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberFormatEditText)
        try {
            isPercentTaxInput = typedArray.getBoolean(R.styleable.NumberFormatEditText_isPercentTax, false)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            typedArray.recycle()
        }
        initializeData()

        keyListener = DigitsKeyListener.getInstance("+,-.0123456789")

        setOnFocusChangeListener { _, hasFocus ->
            invokeOnInputFocusChangedList(hasFocus)
        }

        addNumberFormatTextWatcher()
        addNumberChangeTextWatcher()
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            invokeOnKeyBackPressed()
        }
        return super.onKeyPreIme(keyCode, event)
    }

    private fun bindDataNumberOrigin(isModeEdit: Boolean, number: BigDecimal?, min: BigDecimal, max: BigDecimal) {
        if (isModeEdit) {
            if (min == BigDecimal.ZERO) {
                applyPositiveNumberInputFilter(max)
            } else {
                applyNumberInputFilter(min, max)
            }

            setCurrencyText(number, false)
            selectAll()
        } else {
            clearAllInputFilter()
            setCurrencyText(number, true)
        }
    }

    fun bindDataPositiveCurrency(isModeEdit: Boolean, number: BigDecimal?, max: BigDecimal = NUMBER_MAX) {
        bindDataNumberOrigin(isModeEdit, number, BigDecimal.ZERO, max)
    }

    fun bindDataCurrency(isModeEdit: Boolean, number: BigDecimal?, min: BigDecimal = NUMBER_MIN, max: BigDecimal = NUMBER_MAX) {
        bindDataNumberOrigin(isModeEdit, number, min, max)
    }

    fun bindDataPositiveQuantity(isModeEdit: Boolean, number: Double?) {
        if (isModeEdit) {
            applyQuantityInputFilter()
        } else {
            clearAllInputFilter()
        }

        setNumberText(number)
        if (isModeEdit) {
            selectAll()
        }
    }

    fun bindDataPositiveQuantity(
        isModeEdit: Boolean,
        number: Double?,
        max: BigDecimal = BigDecimal(99999),
        digitsBeforeZero: Int = 5,
        digitsAfterZero: Int = 2
    ) {
        if (isModeEdit) {
            applyQuantityFilter(BigDecimal.ZERO, max, digitsBeforeZero, digitsAfterZero)
        } else {
            clearAllInputFilter()
        }

        setNumberText(number)
        if (isModeEdit) {
            selectAll()
        }
    }

    fun bindDataQuantity(isModeEdit: Boolean, number: Double?, digitsBeforeZero: Int = 5, digitsAfterZero: Int = 2) {
        if (isModeEdit) {
            applyQuantityFilter(digitsBeforeZero, digitsAfterZero)
        } else {
            clearAllInputFilter()
        }

        setNumberText(number)
        if (isModeEdit) {
            selectAll()
        }
    }

    fun bindDataQuantityBigDecimal(isModeEdit: Boolean, number: BigDecimal?, digitsBeforeZero: Int = 5, digitsAfterZero: Int = 2) {
        if (isModeEdit) {
            applyQuantityFilter(digitsBeforeZero, digitsAfterZero)
        } else {
            clearAllInputFilter()
        }
        decimalFormat.maximumFractionDigits = digitsAfterZero

        setNumberTextBigDecimal(number)
        if (isModeEdit) {
            selectAll()
        }
    }

    fun bindDataPercent(isModeEdit: Boolean, number: Double?) {
        if (isModeEdit) {
            applyPercentInputFilter()
        } else {
            clearAllInputFilter()
        }

        setNumberText(number)
        if (isModeEdit) {
            selectAll()
        }
    }

    fun bindDataCommonNumber(isModeEdit: Boolean, value: BigDecimal?) {
        if (isModeEdit) {
            filters = arrayOf(NumberFormatInputFilter(locale, NUMBER_MIN, NUMBER_MAX, 14, 2))
        } else {
            clearAllInputFilter()
        }

        setNumberTextBigDecimal(value)
        if (isModeEdit) {
            selectAll()
        }
    }

    fun bindNumber2FractionDigits(isModeEdit: Boolean, number: BigDecimal, isNegative: Boolean = true, max: BigDecimal = NUMBER_MAX,
                                  currencyCode: String = Const.VN_CURRENCY) {
        if (isModeEdit) {
            applyNumber2FractionDigits(isNegative = isNegative, max = max)
        } else {
            clearAllInputFilter()
        }

        setNumber2FractionDigits(number, currencyCode = currencyCode)
        if (isModeEdit) {
            selectAll()
        }
    }

    private fun setNumber2FractionDigits(value: BigDecimal, symbol: Boolean = false,
                                         currencyCode: String = Const.VN_CURRENCY) {
        val number = getNumberBigDecimal2FractionDigits(value)
        val hasCurrencySymbol = formatWithCurrencySymbol && symbol
        val numberText = CurrencyUtil.formatNumberWith2FractionDigits(number, !hasCurrencySymbol, currencyCode)
        setText(numberText)
    }

    private fun setCurrencyText(value: BigDecimal?, symbol: Boolean) {
        if (value == null) {
            text = null
            return
        }
        val number = getNumberBigDecimal(value)
        setColorWhenNegative(number)
        val hasCurrencySymbol = formatWithCurrencySymbol && symbol
        val numberText = CurrencyUtil.format(number, !hasCurrencySymbol)
        setText(numberText)
    }

    private fun setNumberText(value: Double?) {
        if (value == null) {
            text = null
            return
        }
        val number = getNumberIntOrDouble(value)
        val numberText = percentDecimalFormat.format(number)
        setText(numberText)
    }

    private fun setNumberTextBigDecimal(value: BigDecimal?) {
        if (value == null) {
            text = null
            return
        }
        val numberText = decimalFormat.format(value)
        setText(numberText)
    }

    fun applyPositiveNumberInputFilter(max: BigDecimal = NUMBER_MAX) {
        filters = arrayOf(NumberFormatInputFilter.number(locale, BigDecimal.ZERO, max))
    }

    fun applyNumberInputFilter(min: BigDecimal = NUMBER_MIN, max: BigDecimal = NUMBER_MAX) {
        filters = arrayOf(NumberFormatInputFilter.number(locale, min, max))
    }

    fun applyQuantityInputFilter() {
        filters = arrayOf(NumberFormatInputFilter.quantity(locale))
    }

    fun applyPercentInputFilter() {
        filters = if (isPercentTaxInput) {
            arrayOf(NumberFormatInputFilter(locale, BigDecimal.ZERO, 100.toBigDecimal(), 3, 4))
        } else {
            arrayOf(NumberFormatInputFilter.percent(locale))
        }
    }

    fun applyQuantityFilter(digitsBeforeZero: Int, digitsAfterZero: Int) {
        applyQuantityFilter(BigDecimal(-99999), BigDecimal(99999), digitsBeforeZero, digitsAfterZero)
    }

    fun applyQuantityFilter(min: BigDecimal, max: BigDecimal, digitsBeforeZero: Int, digitsAfterZero: Int) {
        filters = arrayOf(NumberFormatInputFilter(locale, min, max, digitsBeforeZero, digitsAfterZero))
    }

    fun applyPercentFilter(isNegativePercentAllowable: Boolean, digitsBeforeZero: Int, digitsAfterZero: Int) {
        val min = if (isNegativePercentAllowable) {
            (-100).toBigDecimal()
        } else {
            BigDecimal.ZERO
        }
        filters = arrayOf(NumberFormatInputFilter(locale, min, 100.toBigDecimal(), digitsBeforeZero, digitsAfterZero))
    }

    fun applyNumber2FractionDigits(isNegative: Boolean = true, max: BigDecimal = NUMBER_MAX) {
        filters = if (isNegative) {
            arrayOf(NumberFormatInputFilter(locale, NUMBER_MIN, max, 14, 2))
        } else {
            arrayOf(NumberFormatInputFilter(locale, BigDecimal.ZERO, max, 14, 2))
        }
    }

    fun applyNumberFilter(isNegative: Boolean) {
        filters = if (isNegative) {
            arrayOf(NumberFormatInputFilter.number(locale, NUMBER_MIN, NUMBER_MAX))
        } else {
            arrayOf(NumberFormatInputFilter.number(locale, BigDecimal.ZERO, NUMBER_MAX))
        }
    }

    private fun clearAllInputFilter() {
        filters = arrayOf()
    }

    fun getNumber(): BigDecimal = try {
        val regex = Regex("[^+,-.\\d]")
        val text = text.toString().replace(regex, "")

        getNumber(text)
    } catch (e: Exception) {
        BigDecimal.ZERO
    }

    private fun getTextLength() = text?.length ?: 0

    fun setOnNumberChangeListener(onNumberChanged: (number: BigDecimal) -> Unit) {
        this.onNumberChanged = onNumberChanged
    }

    fun setOnNumberNullCallback(onNumberNullCallback: () -> Unit) {
        this.onNumberNullCallback = onNumberNullCallback
    }

    fun setOnKeyBackListener(onKeyBackPressed: () -> Unit) {
        this.onKeyBackPressed = onKeyBackPressed
    }

    fun addOnInputFocusChangeListener(onInputFocusChanged: (hasFocus: Boolean) -> Unit) {
        onInputFocusChangedList.add(onInputFocusChanged)
    }

    // ---------------------------------------------------------------------------------------------
    private fun initializeData() {
        locale = Locale.US
        initializeDataLocale()
    }

    private fun initializeDataLocale() {
        decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat

        percentDecimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        val percentDecimalFormatSymbols = percentDecimalFormat.decimalFormatSymbols
        val percentDecimalSeparator = percentDecimalFormatSymbols.decimalSeparator
        if (isPercentTaxInput) {
            percentDecimalFormat.applyLocalizedPattern("##0${percentDecimalSeparator}####")
        } else {
            percentDecimalFormat.applyLocalizedPattern("##0${percentDecimalSeparator}##")
        }

        val decimalFormatSymbols = decimalFormat.decimalFormatSymbols
        groupingSeparator = decimalFormatSymbols.groupingSeparator.toString()
        decimalSeparator = decimalFormatSymbols.decimalSeparator.toString()
    }

    private fun addNumberFormatTextWatcher() {
        addTextChangedListener(object : DelayTextWatcherAdapter(0) {
            override fun onDelayAfterTextChanged(text: String) {
                Log.d(TAG, "addNumberFormatTextWatcher()... onDelayAfterTextChanged()... $text")

                if (isNumberAutofill && text.isEmpty()) {
                    setText("0")
                    setSelection(1)
                    return
                }
                if (isIgnoreFormatNumber()) {
                    return
                }
                doFormatNumber(this, text)
            }
        })
    }

    private fun addNumberChangeTextWatcher() {
        addTextChangedListener(object : DelayTextWatcherAdapter() {
            override fun onDelayAfterTextChanged(text: String) {
                Log.d(TAG, "addNumberChangeTextWatcher()... onDelayAfterTextChanged()... $text")

                if (isIgnoreNumberChange()) {
                    return
                }

                if (isNumberNull && text.isEmpty() && ::onNumberNullCallback.isInitialized) {
                    onNumberNullCallback.invoke()
                } else {
                    val number = getNumber(text)
                    setColorWhenNegative(number)
                    onNumberChanged.invoke(number)
                }
            }
        })
    }

    private fun isIgnoreFormatNumber(): Boolean {
        if (!hasFocus()) {
            return true
        }
        if (selectionStart == 0 && selectionEnd == getTextLength()) {
            return true
        }
        return false
    }

    private fun doFormatNumber(numberFormatTextWatcher: DelayTextWatcherAdapter, text: String) {
        Log.d(TAG, "doFormatNumber()...")

        removeTextChangedListener(numberFormatTextWatcher)

        try {
            val oldTextLength = getTextLength()
            val oldSelectionStartPosition = selectionStart

            val endWithDecimalSeparator = text.endsWith(decimalSeparator)
            val endWithDecimalSeparatorZero = text.contains(decimalSeparator) && text.endsWith('0')

            val number = parseBigDecimal(text)

            var formattedText = if (isPercentTaxInput) {
                percentDecimalFormat.format(number)
            } else {
                decimalFormat.format(number)
            }

            if (endWithDecimalSeparator) {
                formattedText += decimalSeparator
            }

            if (endWithDecimalSeparatorZero) {
                val zeros = text.takeLastWhile { it == '0' }
                formattedText += if (formattedText.contains(decimalSeparator)) {
                    zeros
                } else {
                    "${decimalSeparator}$zeros"
                }
            }

            if (number.compareTo(BigDecimal.ZERO) == 0) {
                formattedText = text
            }
            setText(formattedText)

            updateCursorPosition(oldTextLength, oldSelectionStartPosition)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        addTextChangedListener(numberFormatTextWatcher)
    }

    private fun updateCursorPosition(oldTextLength: Int, oldSelectionStartPosition: Int) {
        val textLength = getTextLength()
        var cursorPosition = oldSelectionStartPosition + (textLength - oldTextLength)
        if (cursorPosition !in 0..textLength) {
            cursorPosition = textLength
        }
        setSelection(cursorPosition)
    }

    private fun isIgnoreNumberChange(): Boolean {
        if (!hasFocus()) {
            return true
        }
        if (!::onNumberChanged.isInitialized) {
            return true
        }
        return false
    }

    private fun getNumber(text: String): BigDecimal {
        if (text in arrayOf("", "+", ",", "-", ".")) {
            return BigDecimal.ZERO
        }

        return try {
            parseBigDecimal(text)
        } catch (e: Exception) {
            e.printStackTrace()
            BigDecimal.ZERO
        }
    }

    private fun parseBigDecimal(textNumber: String) = textNumber
        .replace(groupingSeparator, "")
        .replace(decimalSeparator, ".")
        .toBigDecimal()

    private fun invokeOnKeyBackPressed() {
        if (::onKeyBackPressed.isInitialized) {
            onKeyBackPressed.invoke()
        }
    }

    private fun invokeOnInputFocusChangedList(hasFocus: Boolean) {
        onInputFocusChangedList.forEach {
            it.invoke(hasFocus)
        }
    }

    // ---------------------------------------------------------------------------------------------
    private fun getNumberBigDecimal(value: BigDecimal): BigDecimal {
        val scale = 2
        return value.setScale(scale, CurrencyUtil.ROUNDING_MODE)
    }

    private fun getNumberBigDecimal2FractionDigits(value: BigDecimal): BigDecimal {
        return value.setScale(2, CurrencyUtil.ROUNDING_MODE)
    }

    private fun getNumberIntOrDouble(value: Double): Any = if (value == floor(value)) {
        value.toInt()
    } else {
        value
    }

    private fun setColorWhenNegative(number: BigDecimal) {
        if (isChangeTextColorByNumber) {
            if (number < BigDecimal.ZERO) {
                setTextColor(ContextCompat.getColor(context, R.color.textColorRedCrimson))
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.colorTextPrimary))
            }
        }
    }
}