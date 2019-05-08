package no.nav.helse.sykepenger.vilkar

import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Evaluering.Companion.ja
import no.nav.nare.core.evaluations.Evaluering.Companion.kanskje
import no.nav.nare.core.evaluations.Evaluering.Companion.nei
import no.nav.nare.core.specifications.Spesifikasjon
import no.nav.nare.core.specifications.ikke
import java.time.LocalDate

internal val toBeDecided = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "Ufullstendig informasjonsgrunnlag",
   beskrivelse = "Vi har ikke nok informasjon til å kunne gi et entydig svar.",
   implementasjon = { kanskje("Vi har ikke nok informasjon til å kunne gi et entydig svar.") }
)

internal val harVærtIArbeidIMinstFireUker = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "§ 8-2 første ledd",
   beskrivelse = "Har søker vært i arbeid i minst fire uker?",
   implementasjon = { søkerHarVærtIArbeid(opptjeningstid) }
)

internal val harOppfyltMedlemskap = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "Kapittel 2. Medlemskap",
   beskrivelse = "Oppfyller søker krav om medlemskap?",
   implementasjon = { medlemsskapErOppfylt(erMedlem) }
)

internal val harAndreYtelser = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "",
   beskrivelse = "Har søker andre ytelser?",
   implementasjon = { søkerHarAndreYtelser(ytelser) }
)

internal val erSendtInnenTreMåneder = Spesifikasjon<Vilkårsgrunnlag>(
   beskrivelse = "Er søknad sendt innen 3 måneder etter måneden for første dag i søknadsperioden",
   identifikator = "§ 22-13 tredje ledd",
   implementasjon = { søkerHarSendtSøknadInnenTreMåneder(søknadSendt, førsteDagSøknadGjelderFor) }
)

internal val erMaksAntallSykepengedagerBruktOpp = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "§ 8-12",
   beskrivelse = "er maks antall sykepengedager brukt?",
   implementasjon = { maksAntallSykepengedagerErBruktOpp(førsteDagSøknadGjelderFor, sisteDagSøknadenGjelderFor, sisteMuligeSykepengedag) }
)

internal val erSøkerForGammel = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "§ 8-3 første ledd",
   beskrivelse = "Er søker for gammel til å motta sykepenger?",
   implementasjon = { søkerErForGammel(alder) }
)

internal val erInntektMinstHalvpartenAvGrunnbeløpet = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "§ 8-3 andre ledd",
   beskrivelse = "Er årsinntekt minst halvparten av grunnbeløpet?",
   implementasjon = { erInntektHalvpartenAvGrunnbeløpet(fastsattÅrsinntekt, grunnbeløp) }
)

internal val harOppfyltOpptjeningstid = (harVærtIArbeidIMinstFireUker eller toBeDecided).med(
   identifikator = "§ 8-2",
   beskrivelse = "Oppfyller søker krav om opptjeningstid?"
)

internal val maksAntallSykepengedagerErIkkeBruktOpp = ikke(erMaksAntallSykepengedagerBruktOpp)

internal val harIngenYtelserSomIkkeKanKombineresMedSykepenger = ikke(harAndreYtelser).eller(toBeDecided).med(
   identifikator = "Forenkling av andre ytelser",
   beskrivelse = "Har søker andre ytelser som ikke kan kombineres med sykepenger?"
)

internal val erKravetFremsattInnenFrist = (erSendtInnenTreMåneder eller toBeDecided).med(
   identifikator = "§ 22-13",
   beskrivelse = "Er kravet fremsatt innen frist?"

)

internal val tapAvPensjonsgivendeInntektOgMinsteInntekt = (erInntektMinstHalvpartenAvGrunnbeløpet og ikke(erSøkerForGammel) eller toBeDecided).med(
   identifikator = "§ 8-3",
   beskrivelse = ""
)

val inngangsvilkår = (harOppfyltOpptjeningstid og harOppfyltMedlemskap og harIngenYtelserSomIkkeKanKombineresMedSykepenger
   og erKravetFremsattInnenFrist og tapAvPensjonsgivendeInntektOgMinsteInntekt og maksAntallSykepengedagerErIkkeBruktOpp) eller toBeDecided

internal fun søkerHarVærtIArbeid(opptjeningstid: Int) =
   if (opptjeningstid >= 28) {
      ja("søker har jobbet minst 28 dager")
   } else {
      nei("søker har jobbet mindre enn 28 dager")
   }

internal fun søkerHarSendtSøknadInnenTreMåneder(søknadSendt: LocalDate, førsteDagSøknadGjelderFor: LocalDate): Evaluering {
   val treMånederTilbake = søknadSendt.minusMonths(3).withDayOfMonth(1)

   return if (treMånederTilbake <= førsteDagSøknadGjelderFor && førsteDagSøknadGjelderFor <= søknadSendt) {
      ja("søknaden er sendt opptil tre måneder etter førstse måned i søknadsperioden")
   } else {
      nei("søknaden må være sendt opptil tre måneder etter første måned i søknadsperioden")
   }
}

internal fun maksAntallSykepengedagerErBruktOpp(førsteDagSøknadGjelderFor: LocalDate, sisteDagSøknadGjelderFor: LocalDate, sisteMuligeSykepengedag: LocalDate): Evaluering {
   return if (sisteDagSøknadGjelderFor.isBefore(sisteMuligeSykepengedag) or sisteDagSøknadGjelderFor.isEqual(sisteMuligeSykepengedag)) {
      nei("siste mulige sykepengedag er etter siste dag søknaden gjelder for")
   } else {
      if (førsteDagSøknadGjelderFor.isAfter(sisteMuligeSykepengedag)) {
         ja("siste mulige sykepengedag var før første dag søknaden gjelder for")
      } else {
         kanskje("siste mulige sykepengedag vil nås i søknadsperioden dersom det tas ut sykepenger for hele perioden");
      }
   }
}

internal fun søkerHarAndreYtelser(ytelser: List<String>) =
   if (ytelser.isEmpty()) {
      nei("har ingen andre ytelser")
   } else {
      ja("har andre ytelser")
   }

internal fun erInntektHalvpartenAvGrunnbeløpet(fastsattÅrsinntekt: Long, grunnbeløp: Long) =
   if (fastsattÅrsinntekt >= 0.5 * grunnbeløp) {
      ja("Årsinntekten er minst 1/2 G")
   } else {
      nei("Årsinntekten er mindre enn 1/2 G")
   }

internal fun søkerErForGammel(alder: Int) =
   when {
      alder >= 70 -> ja("Det ytes ikke sykepenger til medlem som er fylt 70 år")
      alder >= 67 -> kanskje("Medlem mellom 67 og 70 år har en begrenset sykepengerett")
      else -> nei("Søker er yngre enn 67 år")
   }

internal fun medlemsskapErOppfylt(erMedlem: Boolean): Evaluering =
   when (erMedlem) {
      true -> ja("Medlemsskap er oppfylt")
      false -> nei("Medlemsskap er ikke oppfylt")
   }
