package fr.uha.gm.projet.model

import androidx.room.Embedded

class JourAvecDetails (
    @Embedded
    var day : Jour,
    var nomPrincipal : String,
    var conseilsCount : Int,
)