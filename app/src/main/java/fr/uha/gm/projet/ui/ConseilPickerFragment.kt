package fr.uha.gm.projet.ui

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
import fr.uha.gm.projet.databinding.ConseilItemBinding
import fr.uha.gm.projet.databinding.PickerConseilBinding
import fr.uha.gm.projet.model.Conseil
import java.util.*

@AndroidEntryPoint
class ConseilPickerFragment : DialogFragment() {

    private lateinit var requestKey: String
    private var initial: Long = 0

    private var _binding: PickerConseilBinding? = null
    private var viewModel : PickerConseilViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter : ConseilAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this).get(PickerConseilViewModel::class.java)

        _binding = PickerConseilBinding.inflate(inflater, container, false)
        binding.list.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(binding.list.context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(divider)

        adapter = ConseilAdapter()
        binding.list.adapter = adapter

        viewModel!!.conseils.observe(viewLifecycleOwner) {
            adapter.setCurrent(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = ConseilPickerFragmentArgs.fromBundle(requireArguments())
        requestKey = arg.requestKey
        initial = arg.principal
    }

    private fun onSelect(conseil: Conseil) {
        val result = Bundle()
        result.putLong(CONSEIL, conseil.cid)
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

    inner class ConseilAdapter : RecyclerView.Adapter<ConseilAdapter.ViewHolder>() {

        private var current : List<Conseil> = listOf()

        inner class ViewHolder(val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

            override fun onClick(p0: View?) {
                val conseil : Conseil = current[adapterPosition]
                onSelect(conseil)
            }

            init {
                binding.root.setOnClickListener(this)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConseilAdapter.ViewHolder {
            val layout : ConseilItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.conseil_item, parent, false)
            layout.lifecycleOwner = viewLifecycleOwner
            return ViewHolder(layout)
        }

        override fun onBindViewHolder(holder: ConseilAdapter.ViewHolder, position: Int) {
            val conseil : Conseil = current[position]
            holder.binding.setVariable(BR.conseil, conseil)
        }

        override fun getItemCount(): Int {
            return current.size
        }

        fun setCurrent (conseils : List<Conseil>) {
            current = conseils
            notifyDataSetChanged()
        }

        fun get(position: Int): Conseil {
            return current[position]
        }

    }

    companion object {
        const val CONSEIL: String = "conseil"
    }
}
