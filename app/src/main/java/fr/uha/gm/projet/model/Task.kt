package fr.uha.gm.projet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taches")
class Task (
    @PrimaryKey(autoGenerate = true)
    val tid : Long = 0,
    val status : Boolean = false,
    val nom : String = "",
    )
{}