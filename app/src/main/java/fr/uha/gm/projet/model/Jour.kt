package fr.uha.gm.projet.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jours")
data class Jour (
    @PrimaryKey(autoGenerate = true)
    val jid : Long = 0,
    val name : String = "",
    val principalId : Long
) {
}