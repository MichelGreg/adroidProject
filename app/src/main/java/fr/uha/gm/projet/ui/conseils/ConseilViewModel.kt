package fr.uha.gm.projet.ui.conseils

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.android.livedata.Transformations
import fr.uha.gm.projet.database.ConseilDao
import fr.uha.gm.projet.model.Conseil
import fr.uha.gm.projet.model.ConseilComplet
import fr.uha.gm.projet.model.Task
import fr.uha.gm.projet.model.TaskConseilsAssociation
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConseilViewModel @Inject constructor (
    private val conseilDao : ConseilDao
) : ViewModel() {

    private val id = MutableLiveData<Long> ()
    private val _conseilcomplet : LiveData<ConseilComplet> = Transformations.switchMap(id) {
        id -> conseilDao.getById(id).asLiveData()
    }

    var titre : MutableLiveData<String> = Transformations.map(_conseilcomplet) { it.conseil.titre }
    var description : MutableLiveData<String> = Transformations.map(_conseilcomplet) { it.conseil.description }
    var tasks : MutableLiveData<List<Task>> = Transformations.map(_conseilcomplet, {it.tasks})
    val conseil : LiveData<ConseilComplet> = _conseilcomplet

    fun save() = viewModelScope.launch {
        val toSave = Conseil(
            cid=id.value!!,
            titre = titre.value!!,
            description = description.value!!
        )
        conseilDao.update(toSave)
    }

    fun setId (id : Long)  {
        this.id.value = id
    }

    fun createConseil() = viewModelScope.launch {
        val person = Conseil(0, "", "")
        val cid = conseilDao.create(person)
        setId(cid)
    }

    fun addTask (taskId : Long) = viewModelScope.launch {
        conseilDao.addAssociation(
            TaskConseilsAssociation(
                id.value!!,
                taskId
            )
        )
    }

    fun removeTask (taskId : Long) = viewModelScope.launch {
        conseilDao.deleteAssociation(
            TaskConseilsAssociation(
                id.value!!,
                taskId
            )
        )
    }

    companion object {
        private val TAG = ConseilViewModel::class.java.simpleName
    }
}