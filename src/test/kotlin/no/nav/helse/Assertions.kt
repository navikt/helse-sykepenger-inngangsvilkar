package no.nav.helse

import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Resultat

fun assertJa(evaluering: Evaluering) = assertEquals(evaluering, Resultat.JA)
fun assertNei(evaluering: Evaluering) = assertEquals(evaluering, Resultat.NEI)
fun assertKanskje(evaluering: Evaluering) = assertEquals(evaluering, Resultat.KANSKJE)
fun assertEquals(evaluering: Evaluering, resultat: Resultat) = evaluering.resultat == resultat
