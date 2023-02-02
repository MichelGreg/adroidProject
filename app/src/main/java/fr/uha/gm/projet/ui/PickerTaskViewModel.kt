package fr.uha.gm.projet.ui

import androidx.lifecycle.*
import fr.uha.gm.android.livedata.Transformations
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.projet.database.TaskDao
import fr.uha.gm.projet.model.Task
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickerTaskViewModel @Inject constructor (
    private val dao : TaskDao
) : ViewModel() {
    lateinit var fragment: TaskPickerFragment
    private val id = MutableLiveData<Long> ()

    private val _task : LiveData<Task> = Transformations.switchMap(id)
    {
            id -> dao.getById(id).asLiveData()
    }
    var nom : MutableLiveData<String> = Transformations.map(_task, { it.nom })

    val task: LiveData<Task> = _task

    fun save() = viewModelScope.launch {
        val toSave : Task = Task(
            tid = id.value!!,
            nom = nom.value!!,
            status = false,
        )
        dao.update(toSave)
        fragment.onSave(toSave)
    }

    fun setId (id : Long)  {
        this.id.value = id
    }

    fun createTask() = viewModelScope.launch {
        val task = Task(0, false, "")
        val tid = dao.create(task)
        setId(tid)
    }

    companion object {
        private val TAG = PickerTaskViewModel::class.java.simpleName
    }

}