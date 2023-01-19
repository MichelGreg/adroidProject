package fr.uha.gm.projet.database

import androidx.room.*
import fr.uha.gm.projet.model.Conseil
import kotlinx.coroutines.flow.Flow

@Dao
interface ConseilDao {

    @Query("SELECT * FROM conseils")
    fun getAll () : Flow<List<Conseil>>

    @Query("SELECT * FROM conseils WHERE cid = :id")
    fun getById (id : Long) : Flow<Conseil>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create (person: Conseil) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update (person: Conseil)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert (person: Conseil)

    @Delete
    suspend fun delete (person : Conseil)
}