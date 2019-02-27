package no.nav.helse.sykepenger.vilkar

import java.time.LocalDate

fun testSøknad(
   opptjeningstid: Int = 0,
   alder: Int = 0,
   erMedlem: Boolean = true,
   ytelser: List<String> = emptyList(),
   søknadSendt: LocalDate = LocalDate.now(),
   førsteDagSøknadGjelderFor: LocalDate = LocalDate.now(),
   fastsattÅrsinntekt: Long = 0,
   grunnbeløp: Long = 0
) = Vilkårsgrunnlag(opptjeningstid, alder, erMedlem, ytelser, søknadSendt, førsteDagSøknadGjelderFor,
    fastsattÅrsinntekt, grunnbeløp)
