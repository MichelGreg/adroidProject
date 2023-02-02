package fr.uha.gm.projet.ui.conseils

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.projet.BR
import fr.uha.gm.projet.R
import fr.uha.gm.projet.databinding.FragmentConseilBinding
import fr.uha.gm.projet.databinding.TaskItemBinding
import fr.uha.gm.projet.model.Task
import fr.uha.gm.projet.ui.TaskPickerFragment


@AndroidEntryPoint
class ConseilFragment : Fragment() {

    private var _binding: FragmentConseilBinding? = null
    private var conseilViewModel : ConseilViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parentFragmentManager.setFragmentResultListener(TASK, this, FragmentResultListener {
                requestKey, result ->
            val taskId : Long = result.getLong(TaskPickerFragment.TASK)
            conseilViewModel!!.addTask(taskId)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        conseilViewModel =
            ViewModelProvider(this).get(ConseilViewModel::class.java)

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_conseil, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = conseilViewModel

        binding.add.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(ConseilFragmentDirections.actionNavigationConseilToTask(
                TASK
            ))
        }

        val menuHost : MenuHost = requireActivity()

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

        val id = ConseilFragmentArgs.fromBundle(requireArguments()).id
        if (id == 0L) {
            conseilViewModel!!.createConseil()
        } else {
            conseilViewModel!!.setId(id)
        }
    }

    inner class TaskAdapter : RecyclerView.Adapter<ConseilFragment.TaskAdapter.ViewHolder>() {
        private  var current : List<Task> = listOf()

        inner class ViewHolder(val binding : ViewDataBinding) : RecyclerView.ViewHolder(binding.root), View.OnLongClickListener {
            override fun onLongClick(p0: View?): Boolean {
                val task : Task = current[adapterPosition]
                onRemove(task)
                return true
            }

            init {
                binding.root.setOnLongClickListener(this)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConseilFragment.TaskAdapter.ViewHolder {
            val layout : TaskItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.task_item, parent, false)
            layout.lifecycleOwner = viewLifecycleOwner
            return ViewHolder(layout)
        }

        override fun onBindViewHolder(holder: ConseilFragment.TaskAdapter.ViewHolder, position: Int) {
            val task : Task = current[position]
            holder.binding.setVariable(BR.task, task)
        }

        override fun getItemCount(): Int {
            return current.size
        }

        fun setCurrent (tasks : List<Task>) {
            current = tasks
            notifyDataSetChanged()
        }

        fun get(position: Int): Task {
            return current[position]
        }
    }

    private fun onRemove(task: Task) {
        conseilViewModel!!.removeTask(task.tid)
    }

    fun onSave () : Boolean {
        conseilViewModel?.save ()
        NavHostFragment.findNavController(this).popBackStack()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val JOUR: String = "JOUR"
        private val TASK: String = "TASK"
    }
}