package com.example.bottomsheetexample

/**
 * Created by PhongBM on 01/03/2019
 */

interface Const {

    companion object {
        const val OS_ANDROID = "ANDROID"
        const val DOWNLOAD_FILE = "DOWNLOAD_FILE"
        const val UNZIP_FILE = "UNZIP_FILE"
        const val SAVE_DATA = "SAVE_DATA"
        const val FORCE_SYNC = "FORCE_SYNC"
        const val IS_FIRST_SYNC = "IS_FIRST_SYNC"
        const val SAVE_DATA_FIRST_SYNC_FILE = "SAVE_DATA_FIRST_SYN_FILE"
        const val SAVE_DATA_FORCE_SYNC = "SAVE_DATA_FORCE_SYNC"
        const val DELETE_FILE_ZIP = "DELETE_FILE_ZIP"
        const val ANDROID_KEY_STORE = "AndroidKeyStore"
        const val UUID_ZERO = "00000000-0000-0000-0000-000000000000"
        const val HELP_INVOICE_SCREEN = "InvoiceComposeFragment"
        const val SEND_SMS = "send_sms"
        const val SEND_SMS_DATA = "send_sms_data"

        const val DELAY_TIME_MILLIS: Long = 2000
        const val VERBOSE_NOTIFICATION_CHANNEL_NAME: String =
                "Verbose WorkManager Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
                "Shows notifications whenever work starts"
        const val NOTIFICATION_TITLE: String = "WorkRequest Starting"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        val NOTIFICATION_ID = 1

        const val DATE_FORMAT_WEEKDAY = "EEE"
        const val DATE_FORMAT_YEAR_MONTH_DAY_WITH_DASH = "yyyy-MM-dd"
        const val DATE_FORMAT_YEAR_MONTH_DAY_WITH_SLASH = "yyyy/MM/dd"
        const val DATE_FORMAT_MONTH_DAY_YEAR_WITH_DASH = "MM-dd-yyyy"
        const val DATE_FORMAT_MONTH_DAY_YEAR_WITH_SLASH = "MM/dd/yyyy"
        const val DATE_FORMAT_DAY_MONTH_YEAR_WITH_DASH = "dd-MM-yyyy"
        const val DATE_FORMAT_DAY_MONTH_YEAR_WITH_SLASH = "dd/MM/yyyy"
        const val DATE_FORMAT_HOUR_MINUTE_MONTH_DAY_YEAR_WITH_SLASH = "HH:mm MM/dd/yyyy"
        const val DATE_FORMAT_HOUR_MINUTE_DAY_MONTH_YEAR_WITH_SLASH = "HH:mm dd/MM/yyyy"
        const val DATE_FORMAT_MONTH_DAY_YEAR_HOUR_MINUTE_WITH_SLASH = "dd/MM/yyyy HH:mm "
        const val DATE_FORMAT_MONTH_DAY_YEAR_HOUR_MINUTE_12_HOUR_WITH_SLASH = "dd/MM/yyyy hh:mm a"
        const val DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECONDS_WITH_DASH = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECONDS_WITH_DASH_UTC = "yyyy-MM-dd'T'HH:mm:ss"
        const val DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_WITH_DASH = "yyyy-MM-dd HH:mm"
        const val DATE_FORMAT_HOUR_MINUTE_24_HOURS = "HH:mm"
        const val DATE_FORMAT_HOUR_MINUTE_12_HOURS = "HH:mm"
        const val DATE_FORMAT_TIME_DAY_DATE = "HH:mm EEEEE, MM/dd/yyyy"

        const val VN_CURRENCY = "VND"
        const val US_CURRENCY = "USD"

        const val VN_COUNTRY_CODE = "VN"
        const val UK_COUNTRY_CODE = "GB"
        const val US_COUNTRY_CODE = "US"

        const val NUM_IMAGES: Int = 15
        const val NUM_CURRENCY_DIGITS = 14

        const val HEIGHT_BANNER = 218
        const val WIDTH_BANNER = 640

        const val WIDTH_BARCODE = 720
        const val HEIGHT_BARCODE = 60

        const val QRCODE_WIDTH = 180

        const val KEY_EVENT_TYPE_JOURNAL = "journal_label"

        const val FORMAT_BALANCE_TEXT = "%25s"
        const val HOURS_ROUND_FORMAT = "%.2f"
        const val DECIMAL_FORMAT = "#.##"

        const val ACTION_APP_LANGUAGE_CHANGE = "action_app_language_change"
        const val ACTION_APP_SYNC_DATA = "action_app_sync_data"

        const val DISABLE_ALPHA = 0.5f
        const val ENABLE_ALPHA = 1f

        const val INTERNAL_CUSTOMER = "INTERNAL_CUSTOMER"
        const val MISC_SUPPLIER = "MISC_SUPPLIER"
        const val MOOLA_SUPPLIER = "MOOLA_SUPPLIER"
        const val CASH_CUSTOMER = "CASH_CUSTOMER"

        const val FLAVOR_PRO = "pro"

        const val LOST_SALES_REASON_TOTAL = "reason.total"
        const val LOST_SALES_REASON_OTHER = "00000000-0000-0000-0000-000000000000"
        const val LOST_SALES_REASON_IN_USE = "00000000-0000-0000-0000-000000000001"
        const val LOST_SALES_REASON_DONT_HAVE = "00000000-0000-0000-0000-000000000002"
        const val LOST_SALES_REASON_CHANGE_MIND = "00000000-0000-0000-0000-000000000003"
        const val LOST_SALES_REASON_TOO_MUCH_MONEY = "00000000-0000-0000-0000-000000000004"
        const val CONTENT_FILE_TYPE_PDF = "application/pdf"
        const val CONTENT_FILE_TYPE_EXCEL = "application/vnd.ms-excel"
        const val PAGE_SIZE = 100
        const val PAGE_INDEX = 0
        const val BENEFIT_COMMISSION_KEY = "Commission"

        const val MOOLA_VN_WEBSITE = "http://moolapp.com/"
        const val MOOLA_WEBSITE = "https://moolapro.co/"
        const val FOLDER_NAME_LOCAL = "Moola"
        const val CONTENT_TYPE_VIDEO = "video/mp4"
        const val CONTENT_TYPE_PDF = "application/pdf"
        const val DECIMAL_DIGITS_PERCENT = 4

        const val RENTAL_PRICE_OVERCHARGE = "OVERCHARGE"
        const val RENTAL_PRICE_FLAT = "FLAT"
        const val RENTAL_PRICE_HOUR = "HOUR"
        const val RENTAL_PRICE_DAY = "DAY"
        const val RENTAL_PRICE_WEEK = "WEEK"
        const val RENTAL_PRICE_MONTH = "MONTH"
        const val RENTAL_PRICE_WEEKEND = "WEEKEND"
        const val RENTAL_PRICE_5_WEEKDAY = "FIVE_WEEKDAY"
        const val RENTAL_METER_KM = "km"
        const val RENTAL_METER_HOUR = "hour"
        const val RENTAL_METER_MILE = "mile"

        const val KEY_FILTER_DURATION = 4 * 60 * 60 * 1000 // 4h

        const val UNIT_SECTION_INFO = "INFO"
        const val UNIT_SECTION_RENTAL = "RENTAL"
        const val UNIT_SECTION_DEPRECIATION = "DEPRECIATION"
        const val UNIT_SECTION_FLOORING = "FLOORING"

        const val UNIT_BLOCK_TAG = "Tag"
        const val UNIT_BLOCK_CONDITION = "Condition"
        const val UNIT_BLOCK_METER = "Meter"
        const val UNIT_BLOCK_PRICE = "Price"
        const val UNIT_BLOCK_STOCK_NUMBER = "Stock"
        const val UNIT_BLOCK_SPECIFICATION = "Specification"
        const val UNIT_BLOCK_SERIAL_NUMBER = "Serial"
        const val UNIT_BLOCK_HISTORY = "History"
        const val UNIT_BLOCK_LICENSE_PLATE = "LicensePlate"
        const val UNIT_BLOCK_REGISTRATION = "Registration"
        const val UNIT_BLOCK_ATTACHMENT = "Attachment"
        const val UNIT_BLOCK_WARRANTY = "Warranty"
        const val UNIT_BLOCK_UPSELL = "UpSell"
        const val UNIT_BLOCK_RENTAL_PRICE = "RentalPrice"
        const val UNIT_BLOCK_TRAINING = "Training"
        const val UNIT_BLOCK_CHECK_IN_OUT = "CheckInOut"
        const val UNIT_BLOCK_RENTAL_ANALYTIC = "RentalAnalytic"
        const val UNIT_BLOCK_DEPRECIATION_METHOD = "DepreciationMethod"
        const val UNIT_BLOCK_DEPRECIATION_DETAIL = "DepreciationDetail"
        const val UNIT_BLOCK_DEPRECIATION_ASSIGNED = "DepreciationAssigned"

        const val UNIT_SHOW_FIELD_MAKE = "Make"
        const val UNIT_SHOW_FIELD_MODEL = "Model"
        const val UNIT_SHOW_FIELD_STOCK = "Stock"

        const val EQUIPMENT_LINE_QR_CODE = "QrCode"
        const val EQUIPMENT_LINE_BAR_CODE = "BarCode"
        const val EQUIPMENT_LINE_UPC_CODE = "UpcCode"
        const val EQUIPMENT_LINE_TAG = "Tag"
        const val EQUIPMENT_LINE_COLOR = "Color"
    }

}