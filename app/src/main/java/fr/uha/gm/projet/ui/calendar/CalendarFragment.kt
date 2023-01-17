package fr.uha.gm.projet.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.projet.databinding.FragmentDaysBinding
import fr.uha.gm.projet.model.JourAvecDetails
import fr.uha.gm.android.ui.ItemSwipeCallback

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private var _binding: FragmentDaysBinding? = null
    private var viewModel : CalendarViewModel? = null

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
            ViewModelProvider(this).get(CalendarViewModel::class.java)
        binding.list.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(binding.list.context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(divider)

        val itemTouchHelper = ItemTouchHelper(
            ItemSwipeCallback(requireContext(), ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
                object : ItemSwipeCallback.SwipeListener {
                    override fun onSwiped(direction: Int, position: Int) {
                        val day: JourAvecDetails = adapter.get(position)
                        when (direction) {
                            //ItemTouchHelper.LEFT -> onDelete(day)
                            //ItemTouchHelper.RIGHT -> onEdit(day)
                        }
                    }
                }
            )
        )
        itemTouchHelper.attachToRecyclerView(binding.list)

        adapter = JourAdapter()
        return binding.root
    }

    inner class JourAdapter : RecyclerView.Adapter<JourAdapter.ViewHolder>() {
        private var current : List<JourAvecDetails> = listOf()

        inner class ViewHolder(val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            TODO("Not yet implemented")
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            return current.size
        }

        fun setCurrent (jours : List<JourAvecDetails>) {
            current =jours
            notifyDataSetChanged()
        }

        fun get(position: Int): JourAvecDetails {
            return current[position]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}