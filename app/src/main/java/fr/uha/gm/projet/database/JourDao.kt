package fr.uha.gm.projet.database

import androidx.room.*
import fr.uha.gm.projet.model.Jour
import fr.uha.gm.projet.model.JourAvecDetails
import fr.uha.gm.projet.model.JourComplet
import fr.uha.gm.projet.model.JourConseilsAssociation
import kotlinx.coroutines.flow.Flow

@Dao
interface JourDao {

    @Query("SELECT * "
            + ", (SELECT COUNT(*) FROM jcas JCA WHERE JCA.jid = J.jid) AS conseilsCount"
            + ", (SELECT C.titre FROM conseils C WHERE C.cid = J.principalId) AS nomPrincipal"
            + " FROM jours as J")
    fun getAll () : Flow<List<JourAvecDetails>>

    @Transaction
    @Query("SELECT * FROM jours WHERE jid = :id")
    fun getById (id : Long) : Flow<JourComplet>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(jour: Jour): Long

    @Delete
    suspend fun delete (jour: Jour)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAssociation (a:JourConseilsAssociation)

    @Delete
    suspend fun deleteAssociation (a:JourConseilsAssociation)
}