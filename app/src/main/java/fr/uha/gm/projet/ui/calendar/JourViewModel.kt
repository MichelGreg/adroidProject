package fr.uha.gm.projet.ui.calendar

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.projet.database.ConseilDao
import fr.uha.gm.projet.database.JourDao
import fr.uha.gm.android.livedata.Transformations
import fr.uha.gm.projet.model.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JourViewModel @Inject constructor (
    private val jourDao : JourDao,
    private val conseilDao : ConseilDao
) : ViewModel() {

    private val id = MutableLiveData<Long> ()

    private val _jourcomplet : LiveData<JourComplet> = Transformations.switchMap(id)
    {
            id -> jourDao.getById(id).asLiveData()
    }

    var _jour : MutableLiveData<Jour> = Transformations.map(_jourcomplet, { it.jour })

    var name : MutableLiveData<String> = Transformations.map(_jour, { it.name })
    var principalId : MutableLiveData<Long> = Transformations.map(_jour, { it.principalId })
    var principal : MutableLiveData<ConseilComplet> = Transformations.switchMap(principalId) {
            id -> conseilDao.getById(id).asLiveData()
    }
    var conseils : MutableLiveData<List<Conseil>> = Transformations.map(_jourcomplet, { it.conseils })

    val jour: LiveData<Jour> = _jour

    fun setPrincipal (id : Long) {
        principalId.value = id
    }

    fun save() = viewModelScope.launch {
        val toSave : Jour = Jour(
            jid=id.value!!,
            name = name.value!!,
            principalId = principalId.value!!
        )
        jourDao.upsert(toSave)
    }

    fun setId (id : Long)  {
        this.id.value = id
    }

    fun createJour() = viewModelScope.launch {
        val team = Jour(0, "", 1)
        val pid = jourDao.upsert(team)
        setId(pid)
    }

    fun addConseil (conseilId : Long) = viewModelScope.launch {
        jourDao.addAssociation(
            JourConseilsAssociation(
                id.value!!,
                conseilId
            )
        )
    }

    fun removeConseil (conseilId : Long) = viewModelScope.launch {
        jourDao.deleteAssociation(
            JourConseilsAssociation(
                id.value!!,
                conseilId
            )
        )
    }

    companion object {
        private val TAG = JourViewModel::class.java.simpleName
    }
}