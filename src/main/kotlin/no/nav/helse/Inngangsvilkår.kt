package no.nav.helse

import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Evaluering.Companion.ja
import no.nav.nare.core.evaluations.Evaluering.Companion.nei
import no.nav.nare.core.specifications.Spesifikasjon
import no.nav.nare.core.specifications.ikke
import java.time.LocalDate

private val kanskje = Spesifikasjon<Søknad>(
   beskrivelse = "Vi har ikke nok informasjon til å kunne gi et entydig svar.",
   identitet = "Ufullstendig informasjonsgrunnlag") { søknad -> Evaluering.kanskje("Vi har ikke nok informasjon til å kunne gi et entydig svar.") }

val harVærtIArbeidIMinstFireUker = Spesifikasjon<Søknad>(
   beskrivelse = "Har søker vært i arbeid i minst fire uker?",
   identitet = "§ 8-2 første ledd") { søknad -> søkerHarVærtIArbeid(søknad.førsteSykdomsdag, søknad.datoForAnsettelse) }

val harOppfyltOpptjeningstid = (harVærtIArbeidIMinstFireUker eller kanskje).med(
   beskrivelse = "Oppfyller søker krav om opptjeningstid?",
   identitet = "§ 8-2"
)

val boddeINorgeISykdomsperioden = Spesifikasjon<Søknad>(
   beskrivelse = "Bodde søker i Norge da han eller hun ble syk?",
   identitet = "§ 2-1 første ledd") { søknad -> søkerBorINorge(søknad.bostedlandISykdomsperiode) }

val harOppfyltMedlemskap = (boddeINorgeISykdomsperioden eller kanskje).med(
   beskrivelse = "Oppfyller søker krav om medlemskap?",
   identitet = "Kapittel 2. Medlemskap"
)

val harAndreYtelser = Spesifikasjon<Søknad>(
   beskrivelse = "Har søker andre ytelser?",
   identitet = "") { søknad -> søkerHarAndreYtelser(søknad.ytelser) }

val harIngenYtelserSomIkkeKanKombineresMedSykepenger = ikke(harAndreYtelser).eller(kanskje).med(
   beskrivelse = "Har søker andre ytelser som ikke kan kombineres med sykepenger?",
   identitet = "Forenkling av andre ytelser"
)

val erSendtInnenTreMåneder = Spesifikasjon<Søknad>(
   beskrivelse = "Er søknad sendt innen 3 måneder etter måneden for første dag i søknadsperioden",
   identitet = "§ 22-13 tredje ledd") { søknad -> søkerHarSendtSøknadInnenTreMåneder(søknad.søknadSendt, søknad.førsteDagSøknadGjelderFor) }

val erKravetFremsattInnenFrist = (erSendtInnenTreMåneder eller kanskje).med(
   beskrivelse = "Er kravet fremsatt innen frist?",
   identitet = "§ 22-13"
)

val inngangsvilkår = (harOppfyltOpptjeningstid og harOppfyltMedlemskap og harIngenYtelserSomIkkeKanKombineresMedSykepenger og erKravetFremsattInnenFrist) eller kanskje

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
