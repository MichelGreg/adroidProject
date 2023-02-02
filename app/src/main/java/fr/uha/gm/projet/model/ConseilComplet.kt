package fr.uha.gm.projet.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

class ConseilComplet (
    @Embedded
    var conseil: Conseil,

    @Relation(
        parentColumn = "cid",
        entityColumn = "tid",
        associateBy = Junction(TaskConseilsAssociation::class)
    )
    var tasks : MutableList<Task>
) {}