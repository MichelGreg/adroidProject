package fr.uha.gm.projet.ui.conseils

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import fr.uha.gm.projet.R
import fr.uha.gm.projet.databinding.FragmentConseilBinding

@AndroidEntryPoint
class ConseilFragment : Fragment() {

    private var _binding: FragmentConseilBinding? = null
    private var conseilViewModel : ConseilViewModel? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

    fun onSave () : Boolean {
        conseilViewModel?.save ()
        NavHostFragment.findNavController(this).popBackStack()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}