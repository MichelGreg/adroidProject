package fr.uha.gm.projet.ui

import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.projet.databinding.PickerTaskBinding
import fr.uha.gm.projet.model.Task
import java.util.*

@AndroidEntryPoint
class TaskPickerFragment : DialogFragment() {

    private var _binding: PickerTaskBinding? = null
    private var pickerTaskViewModel : PickerTaskViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var requestKey: String
    private var id: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        pickerTaskViewModel =
            ViewModelProvider(this).get(PickerTaskViewModel::class.java)
        pickerTaskViewModel!!.fragment = this
        _binding = PickerTaskBinding.inflate(inflater, container, false)
        binding.vm = pickerTaskViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = TaskPickerFragmentArgs.fromBundle(requireArguments())
        id = arg.id
        requestKey = arg.requestKey

        if (id == 0L) {
            pickerTaskViewModel!!.createTask()
        } else {
            pickerTaskViewModel!!.setId(id)
        }
        binding.confirmButton.setOnClickListener {
            pickerTaskViewModel!!.save()
        }
    }

    fun onSave(task: Task) {
        val result = Bundle()
        result.putLong(TASK, task.tid)
        parentFragmentManager.setFragmentResult(requestKey, result)
        dismiss()
    }

    private fun tryResize () {
        if (dialog == null) return
        val window = dialog?.getWindow()
        if (window == null) return
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics : WindowMetrics = window.getWindowManager().getCurrentWindowMetrics()
            val rect : Rect = metrics.bounds
            point.x = rect.right - rect.left
            point.y = rect.bottom - rect.top
        } else {
            val display = window.getWindowManager().defaultDisplay
            display.getSize(point)
        }
        window.setLayout((point.x * 0.75).toInt(), (point.y * 0.75).toInt())
        window.setGravity(Gravity.CENTER)
    }

    override fun onResume() {
        tryResize()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TASK: String = "TASK"
    }
}
