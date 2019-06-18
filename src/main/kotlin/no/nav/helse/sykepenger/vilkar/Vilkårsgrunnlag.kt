package no.nav.helse.sykepenger.vilkar

import no.nav.helse.sykepenger.vilkar.kontrakt.InntektMedArbeidsforhold
import java.time.LocalDate

data class Vilkårsgrunnlag(val inntekterMedArbeidsforhold: List<InntektMedArbeidsforhold>,
                           val tidspunktForArbeidsuførhet: LocalDate,
                           val opptjeningstid: Int,
                           val alder: Int,
                           val erMedlem: Boolean,
                           val ytelser: List<String>,
                           val søknadSendt: LocalDate,
                           val førsteDagSøknadGjelderFor: LocalDate,
                           val sisteDagSøknadenGjelderFor: LocalDate,
                           val sisteMuligeSykepengedag: LocalDate,
                           val fastsattÅrsinntekt: Long,
                           val grunnbeløp: Long)
