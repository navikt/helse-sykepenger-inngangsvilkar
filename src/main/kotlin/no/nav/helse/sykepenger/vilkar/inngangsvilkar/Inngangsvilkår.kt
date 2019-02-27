package no.nav.helse.sykepenger.vilkar.inngangsvilkar

import no.nav.helse.sykepenger.vilkar.Vilkårsgrunnlag
import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Evaluering.Companion.ja
import no.nav.nare.core.evaluations.Evaluering.Companion.kanskje
import no.nav.nare.core.evaluations.Evaluering.Companion.nei
import no.nav.nare.core.specifications.Spesifikasjon
import no.nav.nare.core.specifications.ikke
import java.time.LocalDate

internal val toBeDecided = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Vi har ikke nok informasjon til å kunne gi et entydig svar.",
   identitet = "Ufullstendig informasjonsgrunnlag") { Evaluering.kanskje("Vi har ikke nok informasjon til å kunne gi et entydig svar.") }

internal val harVærtIArbeidIMinstFireUker = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Har søker vært i arbeid i minst fire uker?",
   identitet = "§ 8-2 første ledd") { søkerHarVærtIArbeid(førsteSykdomsdag, datoForAnsettelse) }

internal val harOppfyltOpptjeningstid = (harVærtIArbeidIMinstFireUker eller toBeDecided).med(
   beskrivelse = "Oppfyller søker krav om opptjeningstid?",
   identitet = "§ 8-2"
)

internal val harOppfyltMedlemskap = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Oppfyller søker krav om medlemskap?",
   identitet = "Kapittel 2. Medlemskap") { medlemsskapErOppfylt(erMedlem) }

internal val harAndreYtelser = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Har søker andre ytelser?",
   identitet = "") { søkerHarAndreYtelser(ytelser) }

internal val harIngenYtelserSomIkkeKanKombineresMedSykepenger = ikke(harAndreYtelser).eller(toBeDecided).med(
   beskrivelse = "Har søker andre ytelser som ikke kan kombineres med sykepenger?",
   identitet = "Forenkling av andre ytelser"
)

internal val erSendtInnenTreMåneder = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Er søknad sendt innen 3 måneder etter måneden for første dag i søknadsperioden",
   identitet = "§ 22-13 tredje ledd") { søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor) }

internal val erKravetFremsattInnenFrist = (erSendtInnenTreMåneder eller toBeDecided).med(
   beskrivelse = "Er kravet fremsatt innen frist?",
   identitet = "§ 22-13"
)

internal val erSøkerForGammel = Spesifikasjon<Vilkårsgrunnlag>(
   identitet = "§ 8-3 første ledd",
   beskrivelse = "Er søker for gammel til å motta sykepenger?"
) { søkerErForGammel(alder) }

internal val erInntektMinstHalvpartenAvGrunnbeløpet = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Er årsinntekt minst halvparten av grunnbeløpet?",
   identitet = "§ 8-3 andre ledd"
) { erInntektHalvpartenAvGrunnbeløpet(fastsattÅrsinntekt, grunnbeløp, harVurdertInntekt) }

internal val tapAvPensjonsgivendeInntektOgMinsteInntekt = (erInntektMinstHalvpartenAvGrunnbeløpet og ikke(erSøkerForGammel) eller toBeDecided).med(
   identitet = "§ 8-3",
   beskrivelse = ""
)

val inngangsvilkår = (harOppfyltOpptjeningstid og harOppfyltMedlemskap og harIngenYtelserSomIkkeKanKombineresMedSykepenger
   og erKravetFremsattInnenFrist og tapAvPensjonsgivendeInntektOgMinsteInntekt) eller toBeDecided

fun søkerHarVærtIArbeid(førsteSykdomsdag: LocalDate, datoForAnsettelse: LocalDate) =
   if (førsteSykdomsdag.minusDays(28) >= datoForAnsettelse) {
      ja("søker har jobbet minst 28 dager")
   } else {
      nei("søker har jobbet mindre enn 28 dager")
   }

fun søkerBorINorge(bostedland: String) =
   if (bostedland == "Norge") {
      ja("Søker er bosatt i Norge.")
   } else {
      nei("Søker er ikke bostatt i Norge.")
   }

fun søkerHarSendtSøknadInnenTreMåneder(søknadSendt: LocalDate, førsteDagSøknadGjelderFor: LocalDate): Evaluering {
   val treMånederTilbake = søknadSendt.minusMonths(3).withDayOfMonth(1)

   return if (treMånederTilbake <= førsteDagSøknadGjelderFor && førsteDagSøknadGjelderFor <= søknadSendt) {
      ja("søknaden er sendt opptil tre måneder etter første måned i søknadsperioden")
   } else {
      nei("søknaden må være sendt opptil tre måneder etter første måned i søknadsperioden")
   }
}

fun søkerHarAndreYtelser(ytelser: List<String>) =
   if (ytelser.isEmpty()) {
      nei("har ingen andre ytelser")
   } else {
      ja("har andre ytelser")
   }

fun erInntektHalvpartenAvGrunnbeløpet(fastsattÅrsinntekt: Long, grunnbeløp: Long, harVurdertInntekt: Boolean) =
   if (fastsattÅrsinntekt >= 0.5 * grunnbeløp) {
      ja("Årsinntekten er minst 1/2 G")
   } else if (!harVurdertInntekt) {
      kanskje("Årsinntekten er mindre enn 1/2 G, saksbehandler må skjønnsmessig vurdere inntekt.")
   } else {
      nei("Årsinntekten er mindre enn 1/2 G")
   }

fun søkerErForGammel(alder: Int) =
   when {
      alder >= 70 -> ja("Det ytes ikke sykepenger til medlem som er fylt 70 år")
      alder >= 67 -> kanskje("Medlem mellom 67 og 70 år har en begrenset sykepengerett")
      else -> nei("Søker er yngre enn 67 år")
   }

fun medlemsskapErOppfylt(erMedlem: Boolean): Evaluering =
   when (erMedlem) {
      true -> ja("Medlemsskap er oppfylt")
      false -> nei("Medlemsskap er ikke oppfylt")
   }
