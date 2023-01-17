package fr.uha.gm.projet.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.uha.gm.projet.database.JourDao
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor (
    private val dao : JourDao
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is calendar Fragment"
    }
    val text: LiveData<String> = _text
}