package com.example.bottomsheetexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.FragmentManager
import com.example.bottomsheetexample.databinding.DialogAddTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Created by phucpt on 3/29/2022
 */

class MyBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {
    companion object {
        fun show(
            fragmentManager: FragmentManager,
            assetId: String,
            isCurrentYear: Boolean,
            onAddButtonClicked: () -> Unit
        ) {
            val dialog = MyBottomSheet()
            dialog.apply {
                this.onAddButtonClicked = onAddButtonClicked
            }
            dialog.show(fragmentManager, MyBottomSheet::javaClass.name)
        }
    }

    lateinit var binding: DialogAddTransactionBinding

    private lateinit var onAddButtonClicked: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTheme() = R.style.CustomModalBottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        dialog?.setOnShowListener {
//            val dialog = it as BottomSheetDialog
//            val bottomSheet =
//                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
//            bottomSheet?.let {
//                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
//                it.parent.parent.requestLayout()
//            }
//        }
//
//        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        initializeView()
        initializeComponents()
        initializeEvents()
        initializeData()
    }

    private fun initializeView() {
    }

    private fun initializeComponents() {
        binding.calculatorKeyboard.registerEditText(binding.edtLifeTime)
        binding.calculatorKeyboard.registerEditText(binding.edtSalvage)
    }

    private fun initializeEvents() {
        binding.btnCancel.setOnClickListener(this)
        binding.btnResetNow.setOnClickListener(this)
    }

    private fun initializeData() {}


    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnCancel -> dismiss()
        }
    }
}