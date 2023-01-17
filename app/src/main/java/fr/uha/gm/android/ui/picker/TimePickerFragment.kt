package fr.uha.gm.android.ui.picker
/*
import android.app.Dialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment(), OnTimeSetListener {

    private lateinit var requestKey: String
    private lateinit var initial: Date

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val arg = TimePickerFragmentArgs.fromBundle(arguments!!)
        requestKey = arg.requestKey
        initial = Date(arg.time)
        val calendar = GregorianCalendar.getInstance()
        calendar.time = initial
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val dialog = TimePickerDialog(requireContext(), this, hour, minute, true)
        if (arg.title != 0) {
            dialog.setTitle(arg.title)
        }
        return dialog
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        val calendar = GregorianCalendar.getInstance()
        calendar.time = initial
        calendar[Calendar.HOUR_OF_DAY] = hour
        calendar[Calendar.MINUTE] = minute
        val date = calendar.timeInMillis
        val result = Bundle()
        result.putLong(TIME, date)
        parentFragmentManager.setFragmentResult(requestKey, result)
    }

    companion object {
        const val TIME: String = "time"
    }
}

 */