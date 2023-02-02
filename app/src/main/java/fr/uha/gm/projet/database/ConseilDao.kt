package fr.uha.gm.projet.database

import androidx.room.*
import fr.uha.gm.projet.model.Conseil
import fr.uha.gm.projet.model.ConseilAvecDetails
import fr.uha.gm.projet.model.ConseilComplet
import fr.uha.gm.projet.model.TaskConseilsAssociation
import kotlinx.coroutines.flow.Flow

@Dao
interface ConseilDao {

    @Query("SELECT * "
            + ", (SELECT COUNT(*) FROM tcas TCA WHERE TCA.cid = C.cid) AS tasksCount"
            + " FROM conseils as C")
    fun getAll () : Flow<List<ConseilAvecDetails>>

    @Query("SELECT * FROM conseils WHERE cid = :id")
    fun getById (id : Long) : Flow<ConseilComplet>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create (person: Conseil) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update (person: Conseil)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert (person: Conseil)

    @Delete
    suspend fun delete (person : Conseil)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAssociation (a: TaskConseilsAssociation)

    @Delete
    suspend fun deleteAssociation (a: TaskConseilsAssociation)
}