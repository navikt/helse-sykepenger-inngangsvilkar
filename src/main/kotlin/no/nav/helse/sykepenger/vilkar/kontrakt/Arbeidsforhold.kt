package no.nav.helse.sykepenger.vilkar.kontrakt

import java.time.LocalDate

data class Arbeidsforhold(val startdato: LocalDate, val ferie: List<Fravær>, val ugyldigFravær: List<Fravær>)
