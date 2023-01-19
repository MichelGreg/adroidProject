package fr.uha.gm.projet.ui.conseils

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.projet.database.AppDatabase
import fr.uha.gm.projet.database.ConseilDao
import fr.uha.gm.projet.model.Conseil
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class ListConseilsViewModel @Inject constructor (
    private val dao : ConseilDao
) : ViewModel() {

    val conseils : LiveData<List<Conseil>> = dao.getAll().asLiveData()

    fun delete(conseil: Conseil) {
        val executor : Executor = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            viewModelScope.launch {
                AppDatabase.get().getConseilDao().delete(conseil)
            }
        })
    }

    companion object {
        private val TAG = ListConseilsViewModel::class.java.simpleName
    }
}