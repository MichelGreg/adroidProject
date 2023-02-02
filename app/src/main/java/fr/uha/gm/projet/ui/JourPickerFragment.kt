package fr.uha.gm.projet.ui

/*
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.projet.BR
import fr.uha.gm.projet.R
import fr.uha.gm.projet.databinding.DayItemBinding
import fr.uha.gm.projet.databinding.PickerJourBinding
import fr.uha.gm.projet.model.JourAvecDetails
import java.util.*

@AndroidEntryPoint
class JourPickerFragment : DialogFragment() {

    private lateinit var requestKey: String
    private var initial: Long = 0

    private var _binding: PickerJourBinding? = null
    private var viewModel : PickerJourViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter : JourAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(PickerJourViewModel::class.java)

        _binding = PickerJourBinding.inflate(inflater, container, false)
        binding.list.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(binding.list.context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(divider)

        adapter = JourAdapter()
        binding.list.adapter = adapter

        viewModel!!.jours.observe(viewLifecycleOwner) {
            adapter.setCurrent(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = JourPickerFragmentArgs.fromBundle(requireArguments())
        requestKey = arg.requestKey
        initial = arg.principal
    }

    private fun onSelect(jour: JourAvecDetails) {
        val result = Bundle()
        result.putLong(JOUR, jour.day.jid)
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

    inner class JourAdapter : RecyclerView.Adapter<JourAdapter.ViewHolder>() {

        private var current : List<JourAvecDetails> = listOf()

        inner class ViewHolder(val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

            override fun onClick(p0: View?) {
                val jour : JourAvecDetails = current[adapterPosition]
                onSelect(jour)
            }

            init {
                binding.root.setOnClickListener(this)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JourAdapter.ViewHolder {
            val layout : DayItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.day_item, parent, false)
            layout.lifecycleOwner = viewLifecycleOwner
            return ViewHolder(layout)
        }

        override fun onBindViewHolder(holder: JourAdapter.ViewHolder, position: Int) {
            val jour : JourAvecDetails = current[position]
            holder.binding.setVariable(BR.jour, jour)
        }

        override fun getItemCount(): Int {
            return current.size
        }

        fun setCurrent (jours: List<JourAvecDetails>) {
            current = jours
            notifyDataSetChanged()
        }

        fun get(position: Int): JourAvecDetails {
            return current[position]
        }

    }

    companion object {
        const val JOUR: String = "JOUR"
    }
}
*/