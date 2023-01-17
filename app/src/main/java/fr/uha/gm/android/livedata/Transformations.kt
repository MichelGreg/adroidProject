package fr.uha.gm.android.livedata

import androidx.annotation.MainThread
import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

object Transformations {
    // forward to the original implementation so only one import is required in the viewmodel
    fun <X, Y> map(source: LiveData<X>, mapFunction: Function<X, Y>): MediatorLiveData<Y> {
        return Transformations.map(source, mapFunction) as MediatorLiveData<Y>
    }

    @MainThread
    fun <X, Y> mapIfPreviousNull(source: LiveData<X>, mapFunction: Function<X, Y>): MediatorLiveData<Y> {
        val result = MediatorLiveData<Y>()
        result.addSource(source) { x ->
            if (result.value == null) {
                result.value = mapFunction.apply(x)
            }
        }
        return result
    }

    // forward to the original implementation so only one import is required in the viewmodel
    fun <X, Y> switchMap(source: LiveData<X>, switchMapFunction: Function<X, LiveData<Y>>): MediatorLiveData<Y> {
        return Transformations.switchMap(source, switchMapFunction) as MediatorLiveData<Y>
    }

    @MainThread
    fun <X, Y> addToList(
            source: LiveData<X>,
            switchMapFunction: Function<X, LiveData<Y>>): MediatorLiveData<MutableList<Y>> {
        val result = MediatorLiveData<MutableList<Y>>()
        result.addSource(source, object : Observer<X> {
            var mSource: LiveData<Y>? = null
            override fun onChanged(x: X) {
                val newLiveData = switchMapFunction.apply(x)
                if (mSource === newLiveData) {
                    return
                }
                if (mSource != null) {
                    result.removeSource(mSource!!)
                }
                mSource = newLiveData
                if (mSource != null) {
                    result.addSource(mSource!!, Observer { y ->
                        result.value?.add(y)
                        result.setValue(result.value)
                    })
                }
            }
        })
        return result
    }
}