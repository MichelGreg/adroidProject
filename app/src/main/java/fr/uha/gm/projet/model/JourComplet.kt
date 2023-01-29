package fr.uha.gm.projet.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class JourComplet (
    @Embedded
    var jour : Jour,

    @Relation(
        parentColumn = "principalId",
        entityColumn = "cid"
    )
    var principal : Conseil,

    @Relation(
        parentColumn = "jid",
        entityColumn = "cid",
        associateBy = Junction(JourConseilsAssociation::class)
    )
    var conseils : MutableList<Conseil>
) {}