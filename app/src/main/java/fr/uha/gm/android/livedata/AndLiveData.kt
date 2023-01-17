package fr.uha.gm.android.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class AndLiveData(vararg liveDataCollection: LiveData<Boolean>) : MediatorLiveData<Boolean>() {

    private val combiner: Observer<Boolean> = Observer {
        var valid = true
        for (ld in liveDataCollection) {
            valid = valid && ld.value != null && ld.value!!
        }
        value = valid
    }

    init {
        value = false
        for (ld in liveDataCollection) {
            addSource(ld, combiner)
        }
    }

}