package fr.uha.gm.projet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conseils")
class Conseil (
    @PrimaryKey(autoGenerate = true)
    val cid : Long = 0,
    val titre : String = "",
    val description : String = "",
    )
{
}