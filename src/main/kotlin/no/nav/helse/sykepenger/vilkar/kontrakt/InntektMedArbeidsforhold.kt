package no.nav.helse.sykepenger.vilkar.kontrakt

typealias InntektMedArbeidsforhold = Pair<Inntekt, List<Arbeidsforhold>>

fun InntektMedArbeidsforhold.finnArbeidsforhold() =
   if (component2().size == 1) {
      component2()[0]
   } else {
      null
   }

fun List<InntektMedArbeidsforhold>.finnArbeidsforhold() =
   mapNotNull(InntektMedArbeidsforhold::finnArbeidsforhold)

fun List<InntektMedArbeidsforhold>.finnMuligeArbeidsforhold() =
   flatMap { (_, muligeArbeidsforhold) ->
      muligeArbeidsforhold
   }
