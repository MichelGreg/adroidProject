package fr.uha.gm.projet.ui.conseils

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.android.livedata.Transformations
import fr.uha.gm.projet.database.ConseilDao
import fr.uha.gm.projet.model.Conseil
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConseilViewModel @Inject constructor (
    private val dao : ConseilDao
) : ViewModel() {

    private val id = MutableLiveData<Long> ()
    private val _conseil : LiveData<Conseil> = Transformations.switchMap(id) {
        id -> dao.getById(id).asLiveData()
    }

    var titre : MutableLiveData<String> = Transformations.map(_conseil) { it.titre }
    var description : MutableLiveData<String> = Transformations.map(_conseil) { it.description }

    val conseil : LiveData<Conseil> = _conseil

    fun save() = viewModelScope.launch {
        val toSave = Conseil(
            cid=id.value!!,
            titre = titre.value!!,
            description = description.value!!
        )
        dao.update(toSave)
    }

    fun setId (id : Long)  {
        this.id.value = id
    }

    fun createConseil() = viewModelScope.launch {
        val person = Conseil(0, "", "")
        val cid = dao.create(person)
        setId(cid)
    }

    companion object {
        private val TAG = ConseilViewModel::class.java.simpleName
    }
}