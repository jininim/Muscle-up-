package com.example.teamproject_hometrainingassistant_app.ui.home.myroutine

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.teamproject_hometrainingassistant_app.R

class MyRoutineDialog : DialogFragment() {

    private lateinit var onDialogResultListener: OnDialogResultListener

    interface OnDialogResultListener{
        fun onTimeEdited(count: String, set: String)
    }

    companion object{
        private const val ARG_COUNT = "arg_count"
        private const val ARG_SET = "arg_set"

        fun newInstance(count: String, set: String): MyRoutineDialog{
            val args = Bundle().apply {
                putString(ARG_COUNT, count)
                putString(ARG_SET, set)
            }
            val fragment = MyRoutineDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val count = arguments?.getString(ARG_COUNT) ?: ""
        val set = arguments?.getString(ARG_SET) ?: ""

        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.fragment_edit_time_dialog)

        val countEditText = dialog.findViewById<EditText>(R.id.countEditText)
        val setEditText = dialog.findViewById<EditText>(R.id.setEditText)
        val confirmButton = dialog.findViewById<Button>(R.id.dialogSaveButton)

        countEditText.setText(count)
        setEditText.setText(set)

        confirmButton.setOnClickListener {
            val editedCount = countEditText.text.toString()
            val editedSet = setEditText.text.toString()
            onDialogResultListener.onTimeEdited(editedCount, editedSet)
            dismiss()
        }

        dialog.window?.apply {
            setGravity(Gravity.CENTER)
            setBackgroundDrawableResource(R.drawable.count_set_dialog)
        }

        return dialog
    }

    fun setOnDialogResultListener(listener: OnDialogResultListener){
        this.onDialogResultListener = listener
    }
}