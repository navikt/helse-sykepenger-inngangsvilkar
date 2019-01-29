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
   beskrivelse = "Oppholder søker seg i Norge?",
   identitet = "§ 8-9.Opphold i Norge eller i utlandet") { søknad -> søkerBorINorge(søknad.bostedlandISykdomsperiode) }

val erMedlemAvFolketrygden = boddeINorgeISykdomsperioden eller kanskje

val harAndreYtelser = Spesifikasjon<Søknad>(
   beskrivelse = "Har søker andre ytelser?",
   identitet = "") { søknad -> søkerHarAndreYtelser(søknad.ytelser) }

val ytelser = ikke(harAndreYtelser).eller(kanskje)

val erSendtInnenTreMåneder = Spesifikasjon<Søknad>(
   beskrivelse = "er søknad sendt innen 3 måneder etter måneden for første dag i søknadsperioden",
   identitet = "") { søknad -> søkerHarSendtSøknadInnenTreMåneder(søknad.søknadSendt, søknad.førsteDagSøknadGjelderFor) }

val søknadSendtInnenforFrist = erSendtInnenTreMåneder eller kanskje

val inngangsvilkår = (harOppfyltOpptjeningstid og erMedlemAvFolketrygden og ytelser og søknadSendtInnenforFrist) eller kanskje

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
      ja("søknaden er sendt tre måneder eller mindre etter første måned i søknadsperioden")
   } else {
      nei("søknad er sendt etter tre måneder etter første måned i søknadsperioden")
   }
}

fun søkerHarAndreYtelser(ytelser: List<String>) =
   if (ytelser.isEmpty()) {
      nei("har ingen andre ytelser")
   } else {
      ja("har andre ytelser")
   }
