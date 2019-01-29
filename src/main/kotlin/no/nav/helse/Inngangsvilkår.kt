package no.nav.helse

import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.evaluations.Evaluering.Companion.ja
import no.nav.nare.core.evaluations.Evaluering.Companion.nei
import no.nav.nare.core.specifications.Spesifikasjon
import no.nav.nare.core.specifications.ikke
import java.time.LocalDate

private val kanskje = Spesifikasjon<Søknad>(
   beskrivelse = "Vi har ikke nok informasjon til å kunne gi et entydig svar.",
   identitet = "") { søknad -> Evaluering.kanskje("Vi har ikke nok informasjon til å kunne gi et entydig svar.") }

val harVærtIArbeidIMinstFireUker = Spesifikasjon<Søknad>(
   beskrivelse = "Har søker vært i arbeid i minst fire uker?",
   identitet = "§ 8-2.Opptjeningstid") { søknad -> søkerHarVærtIArbeid(søknad.førsteSykdomsdag, søknad.datoForAnsettelse) }

val harOppfyltOpptjeningstid = harVærtIArbeidIMinstFireUker eller kanskje

val boddeINorgeISykdomsperioden = Spesifikasjon<Søknad>(
   beskrivelse = "Bodde søker i Norge da han eller hun ble syk?",
   identitet = "§ 2-1.Personer som er bosatt i Norge") { søknad -> søkerBorINorge(søknad.bostedlandISykdomsperiode) }

val harOppfyltMedlemskap = boddeINorgeISykdomsperioden eller kanskje

val harAndreYtelser = Spesifikasjon<Søknad>(
   beskrivelse = "Har søker andre ytelser?",
   identitet = "") { søknad -> søkerHarAndreYtelser(søknad.ytelser) }

val harIngenYtelserSomIkkeKanKombineresMedSykepenger = ikke(harAndreYtelser).eller(kanskje)

val erSendtInnenTreMåneder = Spesifikasjon<Søknad>(
   beskrivelse = "Er søknad sendt innen 3 måneder etter måneden for første dag i søknadsperioden",
   identitet = "§ 22-13.Frister for framsetting av krav – etterbetaling") { søknad -> søkerHarSendtSøknadInnenTreMåneder(søknad.søknadSendt, søknad.førsteDagSøknadGjelderFor) }

val erKravetFremsattInnenFrist = erSendtInnenTreMåneder eller kanskje

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
