package com.example.bottomsheetexample

import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import java.math.BigDecimal
import java.text.DecimalFormatSymbols
import java.util.*
import java.util.regex.Pattern

/**
 * Created by PhongBM on 03/04/2020
 *
 * Refer: https://www.techcompose.com/how-to-set-minimum-and-maximum-value-in-edittext-in-android-app-development/
 */

class NumberFormatInputFilter(
    private val locale: Locale,
    private val min: BigDecimal,
    private val max: BigDecimal,
    private val digitsBeforeZero: Int,
    private val digitsAfterZero: Int
) : InputFilter {
    companion object {
        private const val TAG = "NumberFormatInputFilter"

        fun quantity(locale: Locale) =
            NumberFormatInputFilter(locale, BigDecimal.ZERO, 99999.toBigDecimal(), 5, 2)

        fun number(locale: Locale, min: BigDecimal, max: BigDecimal): NumberFormatInputFilter {
            val digitsAfterZero = 2
            return NumberFormatInputFilter(locale, min, max, 14, digitsAfterZero)
        }

        fun percent(locale: Locale) =
            NumberFormatInputFilter(locale, BigDecimal.ZERO, 100.toBigDecimal(), 3, 2)
    }

    private lateinit var groupingSeparator: String
    private lateinit var decimalSeparator: String

    private lateinit var pattern: Pattern

    init {
        initializeData()
    }

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        return try {
            // Remove the string out of destination that is to be replaced
            var textRaw = dest.toString().substring(0, dstart) + dest.toString()
                .substring(dend, dest.toString().length)
            // Add the new string in
            textRaw = textRaw.substring(0, dstart) + source.toString() + textRaw.substring(
                dstart,
                textRaw.length
            )

            Log.d(TAG, "filter()... source: $source")
            Log.d(TAG, "filter()... taxRaw: $textRaw")

            if (isIgnoreInput(source)) {
                return ""
            }
            if (isIgnoreFilter(textRaw)) {
                return null
            }

            doFilter(textRaw)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    // ---------------------------------------------------------------------------------------------
    private fun initializeData() {
        val decimalFormatSymbols = DecimalFormatSymbols.getInstance(locale)
        groupingSeparator = decimalFormatSymbols.groupingSeparator.toString()
        decimalSeparator = decimalFormatSymbols.decimalSeparator.toString()

        val numberRegex = if (digitsAfterZero == 0) {
            "-?([0-9]{0,$digitsBeforeZero})*"
        } else {
            "-?([0-9]{0,$digitsBeforeZero})*((\\.[0-9]{0,$digitsAfterZero})?)||(\\.)?"
        }

        pattern = Pattern.compile(numberRegex)
    }

    private fun isIgnoreInput(source: CharSequence): Boolean {
        if (source == "+") {
            return true
        }
        if (source == "-" && min >= BigDecimal.ZERO) {
            return true
        }
        if (source == groupingSeparator) { // Can't enter grouping separator
            return true
        }
        if (source == decimalSeparator && digitsAfterZero == 0) {
            return true
        }
        return false
    }

    private fun isIgnoreFilter(textRaw: String): Boolean {
        if (textRaw in arrayOf("", "+", ",", "-", ".")) {
            return true
        }
        return false
    }

    @Throws(Exception::class)
    private fun doFilter(textRaw: String): CharSequence? {
        val numberText = textRaw
            .replace(groupingSeparator, "")
            .replace(decimalSeparator, ".")

        val isMatches = pattern.matcher(numberText).matches()
        if (isMatches) {
            val number = numberText.toBigDecimal()
            val isInRange = isInRange(number, min, max)

            if (isInRange) {
                return null
            }
        }

        return ""
    }

    private fun isInRange(value: BigDecimal, min: BigDecimal, max: BigDecimal) = value in min..max

}