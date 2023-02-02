package fr.uha.gm.projet.model

import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "tcas",
    primaryKeys = ["cid", "tid"],
    indices = [Index("cid"), Index("tid")]
)
class TaskConseilsAssociation (
    val cid : Long,
    val tid : Long
) {}