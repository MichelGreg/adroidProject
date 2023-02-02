package fr.uha.gm.projet.ui.conseils

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.android.ui.ItemSwipeCallback
import fr.uha.gm.projet.R
import fr.uha.gm.projet.BR
import fr.uha.gm.projet.database.DatabaseFeed
import fr.uha.gm.projet.databinding.ActivityMainBinding.inflate
import fr.uha.gm.projet.databinding.ConseilItemBinding
import fr.uha.gm.projet.databinding.FragmentListConseilsBinding
import fr.uha.gm.projet.model.Conseil
import fr.uha.gm.projet.model.ConseilAvecDetails

@AndroidEntryPoint
class ListConseilsFragment : Fragment() {

    private var _binding: FragmentListConseilsBinding? = null
    private var viewModel : ListConseilsViewModel? = null

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
            ViewModelProvider(this).get(ListConseilsViewModel::class.java)

        _binding = FragmentListConseilsBinding.inflate(inflater, container, false)
        binding.add.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.action_navigation_list_to_conseil)
        }
        binding.list.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(binding.list.context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(divider)
        val itemTouchHelper = ItemTouchHelper(
            ItemSwipeCallback(requireContext(), ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
            object : ItemSwipeCallback.SwipeListener {
                override fun onSwiped(direction: Int, position: Int) {
                    val conseil: ConseilAvecDetails = adapter.get(position)
                    when (direction) {
                        ItemTouchHelper.LEFT -> onDelete(conseil)
                        ItemTouchHelper.RIGHT -> onEdit(conseil)
                    }
                }
            })
        )
        itemTouchHelper.attachToRecyclerView(binding.list)

        adapter = ConseilAdapter()
        binding.list.adapter = adapter

        viewModel!!.conseils.observe(viewLifecycleOwner) {
            adapter.setCurrent(it)
        }
        val menuHost : MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.populate_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_populate -> DatabaseFeed().feed()
                }
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    private fun onEdit(conseil: ConseilAvecDetails) {
        val action = ListConseilsFragmentDirections.actionNavigationListToConseil()
        action.id = conseil.conseil.cid
        NavHostFragment.findNavController(this).navigate(action)
    }

    private fun onDelete(conseil: ConseilAvecDetails) {
        viewModel!!.delete(conseil.conseil)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class ConseilAdapter : RecyclerView.Adapter<ConseilAdapter.ViewHolder>() {
        private var current : List<ConseilAvecDetails> = listOf()

        inner class ViewHolder(val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layout : ConseilItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.conseil_item, parent, false)
            layout.lifecycleOwner = viewLifecycleOwner
            return ViewHolder(layout)
        }

        override fun onBindViewHolder(holder: ConseilAdapter.ViewHolder, position: Int) {
            val conseil : Conseil = current[position].conseil
            holder.binding.setVariable(BR.conseil, conseil)
        }

        override fun getItemCount(): Int {
            return current.size
        }

        fun setCurrent (conseils : List<ConseilAvecDetails>) {
            current = conseils
            notifyDataSetChanged()
        }

        fun get(position: Int): ConseilAvecDetails {
            return current[position]
        }
    }
}