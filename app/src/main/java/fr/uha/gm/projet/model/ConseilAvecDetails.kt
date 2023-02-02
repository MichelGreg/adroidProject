package fr.uha.gm.projet.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

class ConseilAvecDetails (
    @Embedded
    val conseil: Conseil,
    var tasksCount : Int
    )
{
}