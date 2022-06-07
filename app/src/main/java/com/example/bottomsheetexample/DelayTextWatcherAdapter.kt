package com.example.bottomsheetexample

import android.text.Editable
import android.text.TextWatcher
import kotlinx.coroutines.*

/**
 * Created by PhongBM on 09/05/2019
 */

abstract class DelayTextWatcherAdapter(private val delayTime: Long = 250) : TextWatcher {
    private var countDownJob: Job? = null

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        if (delayTime <= 0L) {
            onDelayAfterTextChanged(s.toString())
            return
        }

        if (countDownJob != null) {
            countDownJob!!.cancel()
        }

        countDownJob = GlobalScope.launch(Dispatchers.Main) {
            delay(delayTime)

            onDelayAfterTextChanged(s.toString())
            countDownJob = null
        }
    }

    abstract fun onDelayAfterTextChanged(text: String)

}