package fr.uha.gm.projet.database

import androidx.room.*
import fr.uha.gm.projet.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM taches")
    fun getAll () : Flow<List<Task>>

    @Query("SELECT * FROM taches WHERE tid = :id")
    fun getById (id : Long) : Flow<Task>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create (task: Task) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update (task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert (task: Task)

    @Delete
    suspend fun delete (task : Task)
}