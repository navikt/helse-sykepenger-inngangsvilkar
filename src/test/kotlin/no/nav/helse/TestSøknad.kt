package no.nav.helse

import java.time.LocalDate

fun testSøknad(
   førsteSykdomsdag: LocalDate = LocalDate.now(),
   datoForAnsettelse: LocalDate = LocalDate.now(),
   bostedlandISykdomsperiode: String = "",
   ytelser: List<String> = emptyList(),
   søknadSendt: LocalDate = LocalDate.now(),
   førsteDagSøknadGjelderFor: LocalDate = LocalDate.now()
) = Søknad(førsteSykdomsdag, datoForAnsettelse, bostedlandISykdomsperiode, ytelser, søknadSendt, førsteDagSøknadGjelderFor)
