package fr.uha.gm.android.ui.picker
/*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.DatePicker.OnDateChangedListener
import android.widget.TimePicker.OnTimeChangedListener
import androidx.fragment.app.DialogFragment
import java.util.*

class DateTimePickerFragment : DialogFragment(), OnDateChangedListener, OnTimeChangedListener {
    private lateinit var requestKey: String
    private var year = 0
    private var month = 0
    private var day = 0
    private var hour = 0
    private var minute = 0
    private var show = 0
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var okButton: Button
    private lateinit var cancelButton: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        datePicker = DatePicker(context)
        datePicker.setVisibility(View.GONE)
        layout.addView(datePicker, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        timePicker = TimePicker(context)
        timePicker.setVisibility(View.GONE)
        layout.addView(timePicker, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        val timeButtons = LinearLayout(context)
        timeButtons.orientation = LinearLayout.HORIZONTAL
        layout.addView(timeButtons, -1, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT))
        okButton = Button(context)
        okButton.setText(android.R.string.ok)
        okButton.setEnabled(false)
        okButton.setOnClickListener(View.OnClickListener { applyShow(SHOW_DONE) })
        timeButtons.addView(okButton, -1, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0F))
        cancelButton = Button(context)
        cancelButton.setText(android.R.string.cancel)
        cancelButton.setEnabled(false)
        cancelButton.setOnClickListener(View.OnClickListener { applyShow(SHOW_CANCEL) })
        timeButtons.addView(cancelButton, -1, LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0F))
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = DateTimePickerFragmentArgs.fromBundle(arguments!!)
        requestKey = arg.requestKey
        if (savedInstanceState == null) {
            val initial = Date(arg.date)
            val calendar = GregorianCalendar.getInstance()
            calendar.time = initial
            year = calendar[Calendar.YEAR]
            month = calendar[Calendar.MONTH]
            day = calendar[Calendar.DAY_OF_MONTH]
            hour = calendar[Calendar.HOUR_OF_DAY]
            minute = calendar[Calendar.MINUTE]
            show = SHOW_STARTED
        } else {
            restoreInstanceState(savedInstanceState)
        }
        applyShow(SHOW_DAY_PICKER)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceState(outState)
    }

    private fun saveInstanceState(outState: Bundle) {
        outState.putInt(YEAR, datePicker.getYear())
        outState.putInt(MONTH, datePicker.getMonth())
        outState.putInt(DAY, datePicker.getDayOfMonth())
        outState.putInt(HOUR, timePicker.getHour())
        outState.putInt(MINUTE, timePicker.getMinute())
        outState.putInt(SHOW, show)
    }

    private fun restoreInstanceState(savedInstanceState: Bundle) {
        year = savedInstanceState.getInt(YEAR)
        month = savedInstanceState.getInt(MONTH)
        day = savedInstanceState.getInt(DAY)
        hour = savedInstanceState.getInt(HOUR)
        minute = savedInstanceState.getInt(MINUTE)
        show = savedInstanceState.getInt(SHOW)
    }

    private fun applyShow(nextShow: Int) {
        when (nextShow) {
            SHOW_STARTED -> {
            }
            SHOW_DAY_PICKER -> {
                okButton.setEnabled(false)
                cancelButton.setEnabled(true)
                datePicker.setVisibility(View.VISIBLE)
                timePicker.setVisibility(View.GONE)
                datePicker.init(year, month, day, this)
            }
            SHOW_TIME_PICKER -> {
                okButton.setEnabled(true)
                cancelButton.setEnabled(true)
                datePicker.setVisibility(View.GONE)
                timePicker.setVisibility(View.VISIBLE)
                timePicker.setIs24HourView(true)
                timePicker.setHour(hour)
                timePicker.setMinute(minute)
            }
            SHOW_DONE -> {
                hour = timePicker.getHour()
                minute = timePicker.getMinute()
                val calendar = GregorianCalendar.getInstance()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = month
                calendar[Calendar.DAY_OF_MONTH] = day
                calendar[Calendar.HOUR_OF_DAY] = hour
                calendar[Calendar.MINUTE] = minute
                calendar[Calendar.MILLISECOND] = 0
                val date = calendar.timeInMillis
                val result = Bundle()
                result.putLong(DATE, date)
                parentFragmentManager.setFragmentResult(requestKey, result)
                dismiss()
            }
            SHOW_CANCEL -> {
                val arg = DateTimePickerFragmentArgs.fromBundle(arguments!!)
                val date2 = arg.date
                val result2 = Bundle()
                result2.putLong(DATE, date2)
                parentFragmentManager.setFragmentResult(requestKey, result2)
                dismiss()
            }
        }
        show = nextShow
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        this.year = year
        month = monthOfYear
        day = dayOfMonth
        applyShow(SHOW_TIME_PICKER)
    }

    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        hour = hourOfDay
        this.minute = minute
        applyShow(SHOW_DONE)
    }

    companion object {
        const val DATE: String = "date"
        private const val YEAR: String = "year"
        private const val MONTH: String = "month"
        private const val DAY: String = "day"
        private const val HOUR: String = "hour"
        private const val MINUTE: String = "minute"
        private const val SHOW: String = "show"
        private const val SHOW_STARTED = 0
        private const val SHOW_DAY_PICKER = 1
        private const val SHOW_TIME_PICKER = 2
        private const val SHOW_DONE = 3
        private const val SHOW_CANCEL = 4
    }
}

 */