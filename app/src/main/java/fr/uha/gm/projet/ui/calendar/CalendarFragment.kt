package fr.uha.gm.projet.ui.calendar

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.projet.model.JourAvecDetails
import fr.uha.gm.android.ui.ItemSwipeCallback
import fr.uha.gm.projet.BR
import fr.uha.gm.projet.R
import fr.uha.gm.projet.databinding.DayItemBinding
import fr.uha.gm.projet.databinding.FragmentCalendarBinding
import java.util.*

@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
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

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        binding.add.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_list_to_day)
        }
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
                            ItemTouchHelper.RIGHT -> onEdit(day)
                        }
                    }
                }
            )
        )
        itemTouchHelper.attachToRecyclerView(binding.list)

        adapter = JourAdapter()
        binding.list.adapter = adapter

        viewModel!!.jours.observe(viewLifecycleOwner) {
            adapter.setCurrent(it)
        }
        val menuHost : MenuHost = requireActivity()

        /*menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.populate_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_populate -> DatabaseFeed().feed()
                }
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)*/
        return binding.root
    }

    private fun onEdit(jour: JourAvecDetails) {
        val action = CalendarFragmentDirections.actionNavigationListToDay()
        action.id = jour.day.jid
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun onDelete(jour: JourAvecDetails) {
        viewModel!!.delete(jour)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class JourAdapter : RecyclerView.Adapter<JourAdapter.ViewHolder>() {
        private var current : List<JourAvecDetails> = listOf()

        inner class ViewHolder(val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {}

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

        fun setCurrent (jours : List<JourAvecDetails>) {
            current =jours
            notifyDataSetChanged()
        }

        fun get(position: Int): JourAvecDetails {
            return current[position]
        }
    }
}