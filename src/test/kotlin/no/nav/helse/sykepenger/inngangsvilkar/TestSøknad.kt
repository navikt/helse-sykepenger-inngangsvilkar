package no.nav.helse.sykepenger.inngangsvilkar

import no.nav.helse.sykepenger.inngangsvilkar.Vilkårsgrunnlag
import java.time.LocalDate

fun testSøknad(
   førsteSykdomsdag: LocalDate = LocalDate.now(),
   datoForAnsettelse: LocalDate = LocalDate.now(),
   alder: Int = 0,
   bostedlandISykdomsperiode: String = "",
   ytelser: List<String> = emptyList(),
   søknadSendt: LocalDate = LocalDate.now(),
   førsteDagSøknadGjelderFor: LocalDate = LocalDate.now(),
   aktuellMånedsinntekt: Long = 0,
   rapportertMånedsinntekt: Long = 0,
   fastsattÅrsinntekt: Long = 0,
   grunnbeløp: Long = 0,
   harVurdertInntekt: Boolean = false
) = Vilkårsgrunnlag(førsteSykdomsdag, datoForAnsettelse, alder, bostedlandISykdomsperiode, ytelser, søknadSendt, førsteDagSøknadGjelderFor,
   aktuellMånedsinntekt, rapportertMånedsinntekt, fastsattÅrsinntekt, grunnbeløp, harVurdertInntekt)
