package fr.uha.gm.projet.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.projet.database.ConseilDao
import fr.uha.gm.projet.model.Conseil
import javax.inject.Inject

@HiltViewModel
class PickerConseilViewModel @Inject constructor (
    private val dao : ConseilDao
) : ViewModel() {

    val conseils : LiveData<List<Conseil>> = dao.getAll().asLiveData()

    companion object {
        private val TAG = PickerConseilViewModel::class.java.simpleName
    }

}