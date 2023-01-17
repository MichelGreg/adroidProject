package fr.uha.gm.projet.model

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "jcas",
    primaryKeys = ["cid", "jid"],
    indices = [Index("cid"), Index("jid")]
)
class JourConseilsAssociation (
    val cid : Long,
    val jid : Long
) {

}