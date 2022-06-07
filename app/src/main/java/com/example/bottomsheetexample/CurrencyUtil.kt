package com.example.bottomsheetexample

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object CurrencyUtil {
    val ROUNDING_MODE = RoundingMode.HALF_UP

    fun format(value: BigDecimal, noSymbol: Boolean = true): String {
        val currencyCode = "VND"
        return formatByCurrencyCode(value, noSymbol, currencyCode)
    }

    fun formatByCurrencyCode(value: BigDecimal, noSymbol: Boolean, currencyCode: String): String {
        val locale = Locale.US
        val numberFormat = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
        numberFormat.currency = Currency.getInstance(currencyCode)
        numberFormat.maximumFractionDigits = if (currencyCode == "VND") 0 else 2

        val currencySymbol = numberFormat.decimalFormatSymbols
        if (noSymbol) {
            currencySymbol.currencySymbol = ""
        }
        numberFormat.decimalFormatSymbols = currencySymbol

        return numberFormat.format(value)
            .trim()
            .replace(Regex("\\s*"), "")
    }

    fun formatByLocalCurrencyCode(
        value: BigDecimal,
        noSymbol: Boolean,
        currencyCode: String
    ): String {
        val locale = if (currencyCode == "VND") {
            Locale("vi", "VN")
        } else Locale.US
        return coreFormat(value, noSymbol, locale, currencyCode)
    }

    private fun coreFormat(
        value: BigDecimal,
        noSymbol: Boolean,
        locale: Locale,
        currencyCode: String
    ): String {
        val numberFormat = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
        numberFormat.currency = Currency.getInstance(currencyCode)
        numberFormat.maximumFractionDigits = 2

        val currencySymbol = numberFormat.decimalFormatSymbols
        if (noSymbol) {
            currencySymbol.currencySymbol = ""
        }
        numberFormat.decimalFormatSymbols = currencySymbol

        return numberFormat.format(value)
            .trim()
            .replace(Regex("\\s*"), "")
    }

    fun formatCurrencyCode2FractionDigits(
        value: BigDecimal, noSymbol: Boolean = true, language: String = "en",
        currencyCode: String = Const.VN_CURRENCY
    ): String {
        val formattedNumber = value.setScale(2, RoundingMode.HALF_UP)
        return format(formattedNumber, noSymbol)
//        var maximumFractionDigits = 2
//        val locale = if (currencyCode == Const.VN_CURRENCY) {
//            maximumFractionDigits = 0
//            Locale(language, "VN")
//        } else {
//            Locale(language, "US")
//        }
//        val numberFormat = NumberFormat.getCurrencyInstance(locale)
//        numberFormat.currency = Currency.getInstance(currencyCode)
//        numberFormat.maximumFractionDigits = maximumFractionDigits
//        if (noSymbol) {
//            val currencySymbol = (numberFormat as DecimalFormat).decimalFormatSymbols
//            currencySymbol.currencySymbol = ""
//            numberFormat.decimalFormatSymbols = currencySymbol
//        }
//
//        return if (currencyCode == Const.VN_CURRENCY) {
//            numberFormat.format(value.setScale(0, ROUNDING_MODE)).trim()
//        } else {
//            numberFormat.format(value.setScale(2, ROUNDING_MODE)).trim()
//        }
    }

    fun formatNumberWith2FractionDigits(
        value: BigDecimal, noSymbol: Boolean = true,
        currencyCode: String = Const.VN_CURRENCY
    ): String {
        val maximumFractionDigits = 2
        val locale = if (currencyCode == Const.VN_CURRENCY) {
            Locale("vi", "VN")
        } else {
            Locale.US
        }
        val numberFormat = NumberFormat.getCurrencyInstance(locale)
        numberFormat.currency = Currency.getInstance(currencyCode)
        numberFormat.maximumFractionDigits = maximumFractionDigits
        if (noSymbol) {
            val currencySymbol = (numberFormat as DecimalFormat).decimalFormatSymbols
            currencySymbol.currencySymbol = ""
            numberFormat.decimalFormatSymbols = currencySymbol
        }

        return numberFormat.format(value.setScale(2, ROUNDING_MODE)).trim()
    }

//    fun format2StringCurrency(value: BigDecimal): String {
//        val currencyCode = AccountManager.account.currentBranch.currencyCode
//        val language = AccountManager.account.currentStaff.languageCode ?: LanguageType.ENGLISH.key
//        val amount = formatCurrencyCode2FractionDigits(value, true, language, currencyCode)
//        return "$currencyCode $amount"
//    }

//    fun formatToStringCurrency(amount: BigDecimal, currencyCode: String = Const.VN_CURRENCY): String {
//        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
//        val currency = Currency.getInstance(currencyCode)
//        formatter.currency = currency
//        //TODO english = 2
//        formatter.maximumFractionDigits = 0
//        val currencySymbol = (formatter as DecimalFormat).decimalFormatSymbols
//        currencySymbol.currencySymbol = ""
//        formatter.decimalFormatSymbols = currencySymbol
//        return formatter.format(amount)
//    }
//
//    fun formatToStringCurrencyWithCurrencySymbol(amount: BigDecimal, currencyCode: String = Const.VN_CURRENCY): String {
//        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
//        val currency = Currency.getInstance(currencyCode)
//        formatter.currency = currency
//        //TODO english = 2
//        formatter.maximumFractionDigits = 0
//        return formatter.format(amount)
//    }
//
//    fun format(currency: BigDecimal): String {
//        val locale = Locale("vi", "VN")
//        val numberFormat = NumberFormat.getCurrencyInstance(locale)
//        numberFormat.currency = Currency.getInstance("VND")
//        return numberFormat.format(currency)
//    }
//
//    fun parseStringToCurrencyAmount(stringAmount: String, currencyCode: String = Const.VN_CURRENCY, containCurrencySymbol: Boolean = false): BigDecimal {
//        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
//        val currency = Currency.getInstance(currencyCode)
//        formatter.currency = currency
//        //TODO english = 2
//        formatter.maximumFractionDigits = 0
//        if (!containCurrencySymbol) {
//            val currencySymbol = (formatter as DecimalFormat).decimalFormatSymbols
//            currencySymbol.currencySymbol = ""
//            formatter.decimalFormatSymbols = currencySymbol
//        }
//        return BigDecimal(formatter.parse(stringAmount).toString())
//    }

//    fun parseStringToCurrencyAmount(stringAmount: String, containCurrencySymbol: Boolean = false): BigDecimal {
//        val locale: Locale
//        val maximumFractionDigits: Int
//        val currencyCode: String
//
//        if (AccountManager.account.currentBranch.currencyCode == Const.VN_CURRENCY) {
//            locale = Locale.US
//            maximumFractionDigits = 2
//            currencyCode = Const.US_CURRENCY
//        } else {
//            locale = Locale.US
//            maximumFractionDigits = 2
//            currencyCode = Const.US_CURRENCY
//        }
//        val formatter = NumberFormat.getCurrencyInstance(locale)
//        val currency = Currency.getInstance(currencyCode)
//        formatter.currency = currency
//        formatter.maximumFractionDigits = maximumFractionDigits
//        if (!containCurrencySymbol) {
//            val currencySymbol = (formatter as DecimalFormat).decimalFormatSymbols
//            currencySymbol.currencySymbol = ""
//            formatter.decimalFormatSymbols = currencySymbol
//        }
//        return BigDecimal(formatter.parse(stringAmount, ParsePosition(0)).toString())
//    }

//    fun parseStringToCurrencyAmount(stringAmount: String): BigDecimal {
//        val string = stringAmount.replace(".", "").replace(",", "")
//        return if (string.isBlank()) BigDecimal.ZERO else string.toLong().toBigDecimal()
//    }

//    fun parseCurrencyStringToAmount(stringAmount: String, currencyCode: String = CurrencyCode.USD.name): BigDecimal {
//        val amountString = stringAmount.replace(".", "").replace(",", "")
//        val locale = AccountManager.getLocale()
//        val numberFormat = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
//        numberFormat.currency = Currency.getInstance(currencyCode)
//        val currencySymbol = numberFormat.decimalFormatSymbols.currencySymbol
//
//        return BigDecimal(amountString.replace(currencySymbol, "").trim())
//    }

//    fun formatNumberToPercent(number: BigDecimal): String {
//        return "$number%"
//    }

}