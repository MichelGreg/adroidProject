package fr.uha.gm.projet.database

import fr.uha.gm.projet.model.Conseil
import fr.uha.gm.projet.model.Jour
import fr.uha.gm.projet.model.JourConseilsAssociation
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class DatabaseFeed {

    fun feed () {
        val executor : Executor = Executors.newSingleThreadExecutor()
        executor.execute(Runnable {
            MainScope().launch {
                val pids = feedConseil()
                feedJour (pids)
            }
        })
    }

    private suspend fun feedConseil() : LongArray {
        val dao = AppDatabase.get().getConseilDao()
        var ids : LongArray = LongArray(7)
        ids[0] = dao.create(Conseil(0,"Conseil no 1", "Description du conseil no 1"))
        ids[1] = dao.create(Conseil(0,"Conseil no 2", "Description du conseil no 2"))
        ids[2] = dao.create(Conseil(0,"Conseil no 3", "Description du conseil no 3"))
        ids[3] = dao.create(Conseil(0,"Conseil no 4", "Description du conseil no 4"))
        ids[4] = dao.create(Conseil(0,"Conseil no 5", "Description du conseil no 5"))
        ids[5] = dao.create(Conseil(0,"Conseil no 6", "Description du conseil no 6"))
        ids[6] = dao.create(Conseil(0,"Conseil no 7", "Description du conseil no 7"))
        return ids
    }

    private suspend fun feedJour( conseils : LongArray) {
        val dao = AppDatabase.get().getJourDao()
        val lun : Long = dao.upsert(Jour(0, "Lundi", conseils[0]))
        val mar : Long = dao.upsert(Jour(0, "Mardi", conseils[1]))
        val mer : Long = dao.upsert(Jour(0, "Mercredi", conseils[2]))
        val jeu : Long = dao.upsert(Jour(0, "Jeudi", conseils[3]))
        val ven : Long = dao.upsert(Jour(0, "Vendredi", conseils[4]))
        val sam : Long = dao.upsert(Jour(0, "Samedi", conseils[5]))
        val dim : Long = dao.upsert(Jour(0, "Dimanche", conseils[6]))
        dao.addAssociation (JourConseilsAssociation(lun, conseils[0]))
        dao.addAssociation (JourConseilsAssociation(lun, conseils[2]))
        dao.addAssociation (JourConseilsAssociation(mar, conseils[1]))
        dao.addAssociation (JourConseilsAssociation(mar, conseils[3]))
        dao.addAssociation (JourConseilsAssociation(mer, conseils[2]))
        dao.addAssociation (JourConseilsAssociation(mer, conseils[5]))
        dao.addAssociation (JourConseilsAssociation(jeu, conseils[3]))
        dao.addAssociation (JourConseilsAssociation(jeu, conseils[6]))
        dao.addAssociation (JourConseilsAssociation(ven, conseils[4]))
        dao.addAssociation (JourConseilsAssociation(ven, conseils[2]))
        dao.addAssociation (JourConseilsAssociation(sam, conseils[5]))
        dao.addAssociation (JourConseilsAssociation(sam, conseils[4]))
        dao.addAssociation (JourConseilsAssociation(dim, conseils[1]))
        dao.addAssociation (JourConseilsAssociation(dim, conseils[6]))
    }

}