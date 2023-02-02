package fr.uha.gm.projet.ui.calendar

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.projet.R
import fr.uha.gm.projet.BR
import fr.uha.gm.projet.databinding.AdviceItemBinding
import fr.uha.gm.projet.databinding.ConseilItemBinding
import fr.uha.gm.projet.databinding.FragmentDayBinding
import fr.uha.gm.projet.model.Conseil
import fr.uha.gm.projet.ui.ConseilPickerFragment

@AndroidEntryPoint
class JourFragment : Fragment() {

    private var _binding: FragmentDayBinding? = null
    private var jourViewModel : JourViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter : AdviceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener(PRINCIPAL, this, FragmentResultListener {
                requestKey, result ->
            val principalId : Long = result.getLong(ConseilPickerFragment.CONSEIL)
            jourViewModel!!.setPrincipal(principalId)
        })
        parentFragmentManager.setFragmentResultListener(ADVICE, this, FragmentResultListener {
                requestKey, result ->
            val conseilId : Long = result.getLong(ConseilPickerFragment.CONSEIL)
            jourViewModel!!.addConseil(conseilId)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        jourViewModel =
            ViewModelProvider(this).get(JourViewModel::class.java)

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_day, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setChangePrincipal {
            var action = JourFragmentDirections.actionNavigationDayToPersonPickerFragment(
                jourViewModel!!.principal.value!!.conseil.cid,
                PRINCIPAL
            )
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.setAddConseil {
            var action = JourFragmentDirections.actionNavigationDayToPersonPickerFragment(
                0,
                ADVICE
            )
            NavHostFragment.findNavController(this).navigate(action)
        }
        jourViewModel!!.principal.observe(viewLifecycleOwner) {
            binding.principal.conseil = it.conseil
        }

        binding.principal
        binding.list.layoutManager = LinearLayoutManager(binding.root.context, RecyclerView.VERTICAL, false)
        val divider = DividerItemDecoration(binding.list.context, DividerItemDecoration.VERTICAL)
        binding.list.addItemDecoration(divider)

        adapter = AdviceAdapter()
        binding.list.adapter = adapter

        binding.vm = jourViewModel
        val menuHost : MenuHost = requireActivity()

        jourViewModel!!.conseils.observe(viewLifecycleOwner) {
            adapter.setCurrent(it)
        }

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.save_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_save -> return onSave()
                }
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = JourFragmentArgs.fromBundle(requireArguments()).id
        if (id == 0L) {
            jourViewModel!!.createJour()
        } else {
            jourViewModel!!.setId(id)
        }
    }

    inner class AdviceAdapter : RecyclerView.Adapter<AdviceAdapter.ViewHolder>() {
        private var current : List<Conseil> = listOf()

        inner class ViewHolder(val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                val conseil : Conseil = current[adapterPosition]
                onRemove(conseil)
                return true
            }

            init {
                binding.root.setOnLongClickListener(this)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdviceAdapter.ViewHolder {
            val layout : AdviceItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.advice_item, parent, false)
            layout.lifecycleOwner = viewLifecycleOwner
            return ViewHolder(layout)
        }

        override fun onBindViewHolder(holder: AdviceAdapter.ViewHolder, position: Int) {
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

    private fun onRemove(conseil: Conseil) {
        jourViewModel!!.removeConseil(conseil.cid)
    }

    fun onSave () : Boolean {
        jourViewModel?.save ()
        NavHostFragment.findNavController(this).popBackStack()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val PRINCIPAL: String = "PRINCIPAL"
        private val ADVICE: String = "ADVICE"
    }
}