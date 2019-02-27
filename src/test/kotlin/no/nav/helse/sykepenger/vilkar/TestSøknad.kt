package no.nav.helse.sykepenger.vilkar

import no.nav.helse.sykepenger.vilkar.Vilkårsgrunnlag
import java.time.LocalDate

fun testSøknad(
   førsteSykdomsdag: LocalDate = LocalDate.now(),
   datoForAnsettelse: LocalDate = LocalDate.now(),
   alder: Int = 0,
   erMedlem: Boolean = true,
   ytelser: List<String> = emptyList(),
   søknadSendt: LocalDate = LocalDate.now(),
   førsteDagSøknadGjelderFor: LocalDate = LocalDate.now(),
   aktuellMånedsinntekt: Long = 0,
   rapportertMånedsinntekt: Long = 0,
   fastsattÅrsinntekt: Long = 0,
   grunnbeløp: Long = 0,
   harVurdertInntekt: Boolean = false
) = Vilkårsgrunnlag(førsteSykdomsdag, datoForAnsettelse, alder, erMedlem, ytelser, søknadSendt, førsteDagSøknadGjelderFor,
   aktuellMånedsinntekt, rapportertMånedsinntekt, fastsattÅrsinntekt, grunnbeløp, harVurdertInntekt)
