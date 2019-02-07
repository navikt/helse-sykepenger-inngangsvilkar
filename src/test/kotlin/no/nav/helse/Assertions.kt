package no.nav.helse

import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Resultat
import org.junit.jupiter.api.Assertions.assertEquals

fun assertJa(evaluering: Evaluering) = assertEquals(Resultat.JA, evaluering.resultat, evaluering.begrunnelse)
fun assertNei(evaluering: Evaluering) = assertEquals(Resultat.NEI, evaluering.resultat, evaluering.begrunnelse)
fun assertKanskje(evaluering: Evaluering) = assertEquals(Resultat.KANSKJE, evaluering.resultat, evaluering.begrunnelse)
