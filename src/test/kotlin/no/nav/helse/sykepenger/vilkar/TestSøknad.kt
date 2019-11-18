package no.nav.helse.sykepenger.vilkar

import no.nav.helse.sykepenger.vilkar.kontrakt.InntektMedArbeidsforhold
import java.time.LocalDate

fun testSøknad(
   inntekterMedArbeidsforhold: List<InntektMedArbeidsforhold> = emptyList(),
   tidspunktForArbeidsuførhet: LocalDate = LocalDate.now(),
   opptjeningstid: Int = 0,
   alder: Int = 0,
   erMedlem: Boolean = true,
   ytelser: List<String> = emptyList(),
   søknadSendt: LocalDate = LocalDate.now(),
   førsteDagSøknadGjelderFor: LocalDate = LocalDate.now(),
   sisteDagSøknadGjelderFor: LocalDate = LocalDate.now().plusWeeks(2),
   sisteMuligeSykepengedag: LocalDate = LocalDate.now().plusWeeks(4),
   fastsattÅrsinntekt: Long = 0,
   grunnbeløp: Long = 0
) = Vilkårsgrunnlag(inntekterMedArbeidsforhold, tidspunktForArbeidsuførhet, opptjeningstid, alder, erMedlem, ytelser, søknadSendt, førsteDagSøknadGjelderFor,
   sisteDagSøknadGjelderFor, sisteMuligeSykepengedag, fastsattÅrsinntekt, grunnbeløp)
