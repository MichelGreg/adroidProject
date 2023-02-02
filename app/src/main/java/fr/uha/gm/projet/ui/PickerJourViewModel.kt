package fr.uha.gm.projet.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.projet.database.JourDao
import fr.uha.gm.projet.model.Jour
import fr.uha.gm.projet.model.JourAvecDetails
import javax.inject.Inject

@HiltViewModel
class PickerJourViewModel @Inject constructor (
    private val dao : JourDao
) : ViewModel() {

    val jours : LiveData<List<JourAvecDetails>> = dao.getAll().asLiveData()

    companion object {
        private val TAG = PickerJourViewModel::class.java.simpleName
    }

}