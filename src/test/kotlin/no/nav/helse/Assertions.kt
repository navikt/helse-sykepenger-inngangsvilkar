package no.nav.helse

import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Resultat
import org.junit.jupiter.api.Assertions.assertEquals

fun assertJa(evaluering: Evaluering) = assertEquals(evaluering.resultat, Resultat.JA)
fun assertNei(evaluering: Evaluering) = assertEquals(evaluering.resultat, Resultat.NEI)
fun assertKanskje(evaluering: Evaluering) = assertEquals(evaluering.resultat, Resultat.KANSKJE)
