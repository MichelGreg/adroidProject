package fr.uha.gm.projet.ui.calendar

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.projet.database.AppDatabase
import fr.uha.gm.projet.database.JourDao
import fr.uha.gm.projet.model.JourAvecDetails
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor (
    private val dao : JourDao
) : ViewModel() {

    val jours : LiveData<List<JourAvecDetails>> = dao.getAll().asLiveData()

    fun delete(jour: JourAvecDetails) {
        val executor : Executor = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            viewModelScope.launch {
                AppDatabase.get().getJourDao().delete(jour.day)
            }
        })
    }

    companion object {
        private val TAG = CalendarViewModel::class.java.simpleName
    }
}