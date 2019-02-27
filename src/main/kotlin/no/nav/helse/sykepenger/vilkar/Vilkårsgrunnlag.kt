package no.nav.helse.sykepenger.vilkar

import java.time.LocalDate

data class Vilkårsgrunnlag(val opptjeningstid: Int,
                           val alder: Int,
                           val erMedlem: Boolean,
                           val ytelser: List<String>,
                           val søknadSendt: LocalDate,
                           val førsteDagSøknadGjelderFor: LocalDate,
                           val fastsattÅrsinntekt: Long,
                           val grunnbeløp: Long)
