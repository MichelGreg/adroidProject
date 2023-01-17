package fr.uha.gm.android.ui.picker

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), OnDateSetListener {

    private lateinit var requestKey: String
    private lateinit var initial: Date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val arg = DatePickerFragmentArgs.fromBundle(requireArguments())
        requestKey = arg.requestKey
        initial = Date(arg.date)
        val calendar = GregorianCalendar.getInstance()
        calendar.time = initial
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val dialog = DatePickerDialog(requireContext(), this, year, month, day)
        if (arg.title != 0) {
            dialog.setTitle(arg.title)
        }
        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = GregorianCalendar.getInstance()
        calendar.time = initial
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val date = calendar.timeInMillis
        val result = Bundle()
        result.putLong(DATE, date)
        parentFragmentManager.setFragmentResult(requestKey, result)
    }

    companion object {
        const val DATE: String = "date"
    }
}
