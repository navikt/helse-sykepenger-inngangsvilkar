package no.nav.helse.sykepenger

import java.time.LocalDate

data class Søknad(val førsteSykdomsdag: LocalDate,
                  val datoForAnsettelse: LocalDate,
                  val alder: Int,
                  val bostedlandISykdomsperiode: String,
                  val ytelser: List<String>,
                  val søknadSendt: LocalDate,
                  val førsteDagSøknadGjelderFor: LocalDate,
                  val aktuellMånedsinntekt: Long,
                  val rapportertMånedsinntekt: Long,
                  val fastsattÅrsinntekt: Long = aktuellMånedsinntekt * 12,
                  val grunnbeløp: Long,
                  val harVurdertInntekt: Boolean)
