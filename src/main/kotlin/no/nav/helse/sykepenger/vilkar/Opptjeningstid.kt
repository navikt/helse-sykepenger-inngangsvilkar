package no.nav.helse.sykepenger.vilkar

import no.nav.helse.sykepenger.vilkar.kontrakt.Arbeidsforhold
import no.nav.helse.sykepenger.vilkar.kontrakt.finnArbeidsforhold
import no.nav.helse.sykepenger.vilkar.kontrakt.finnMuligeArbeidsforhold
import no.nav.nare.core.evaluations.Evaluering
import no.nav.nare.core.specifications.Spesifikasjon
import java.time.LocalDate

private const val fireHeleUker = 28

internal val harArbeidsforholdMedOpptjening = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "§8-2_1",
   beskrivelse = "Det kreves at bruker har vært i arbeid i fire hele uker, det vil si 28 dager fra og med dagen før " +
      "vedkommende ble arbeidsufør. Dette gjelder også en person som er ansatt i et midlertidig arbeidsforhold, " +
      "med vanlige lønnsvilkår, selv om lønnen delvis finansieres gjennom tilskottsordninger fra NAV." +
      "Regler for å beregne 28 dager:\n" +
      "\n" +
      "- Det er 28 kalenderdager\n" +
      "- Et arbeidsforhold anses som påbegynt fra og med ansettelsesdatoen. Det er ikke avgjørende når arbeidet faktisk starter opp.\n" +
      "- Opptjeningstiden avbrytes først når bruker har første fraværsdag (første dagen etter sykmeldingstidspunktet når bruker skulle vært på jobb)\n" +
      "- Et arbeidsforhold anses likevel ikke påbegynt ved ferieavvikling.\n" +
      "- Har arbeidstakeren arbeidet en dag for så å ta lovbestemt ferie eller ferie som er bestemt gjennom overenskomst, anses arbeidsforholdet for påbegynt og ferien teller med ved beregningen av opptjeningstid.\n" +
      "\n" +
      "Når bruker på sykmeldingstidspunktet har to arbeidsforhold men bare vært ansatt i et av dem i mer enn 28 dager, oppfyller vedkommende kravet til opptjeningstid. Dette gjelder også når bruker bare blir sykmeldt fra arbeidsforholdet hvor vedkommende har vært ansatt i mindre 28 dager.\n" +
      "Opptjeningstiden på fire uker anses avbrutt når vedkommende blir sykmeldt." +
      "Fravær uten gyldig grunn går til fradrag ved beregning av opptjeningstiden. Med dette menes fravær som ikke er avtalt med arbeidsgiver, eller fravær fra arbeidet på grunn av sykdom som ikke er dokumentert.\n" +
      "\n" +
      "Lovlig fravær regnes derimot med i opptjeningstiden. Med lovlig fravær menes fravær som er avtalt med arbeidsgiver."
) {
   lagOpptjeningsgrunnlag(inntekterMedArbeidsforhold.finnArbeidsforhold(), tidspunktForArbeidsuførhet)
      .find { (_, opptjeningstid) ->
         opptjeningstid >= fireHeleUker
      }?.let {
         Evaluering.ja("Søker har minst ett arbeidsforhold som oppfyller krav om opptjeningstid")
      } ?: lagOpptjeningsgrunnlag(inntekterMedArbeidsforhold.finnMuligeArbeidsforhold(), tidspunktForArbeidsuførhet)
         .filter { (_, opptjeningstid) ->
            opptjeningstid >= fireHeleUker
         }.let { muligeArbeidsforholdOpptjening ->
         if (muligeArbeidsforholdOpptjening.isNotEmpty()) {
            Evaluering.kanskje("Søker har ${muligeArbeidsforholdOpptjening.size} mulige arbeidsforhold som oppfyller krav om opptjeningstid")
         } else {
            Evaluering.nei("Søker har ingen arbeidsforhold som oppfyller krav om opptjeningstid")
         }
      }
}

internal val harYtelserMedOpptjening = Spesifikasjon<Vilkårsgrunnlag>(
   identifikator = "§8-2_2.1",
   beskrivelse = "Likestilt med arbeid er tidsrom med ytelser som skal erstatte arbeidsinntekt . Dette kan være en av følgende ytelser:\n" +
      "\n" +
      "- Dagpenger ved arbeidsløshet\n" +
      " Hvis kapittel 4 ytelsen er tiltakspenger og bruker ikke fyller kravet til minsteinntekt i § 4-4, så gir ikke kapittel 4 ytelsen rett til sykepenger\n" +
      "- sykepenger\n" +
      "- omsorgspenger\n" +
      "- pleiepenger\n" +
      "- opplæringspenger\n" +
      "- svangerskapspenger\n" +
      "- foreldrepenger"
) {
   Evaluering.nei("Søker har ingen ytelser som oppfyller krav om opptjeningstid (ikke implementert)")
}

internal val harOppfyltOpptjeningstid = harArbeidsforholdMedOpptjening eller harYtelserMedOpptjening

private fun beregnOpptjeningstidForArbeidsforhold(arbeidsforhold: Arbeidsforhold, tidspunktForArbeidsuførhet: LocalDate) =
   beregnOpptjeningstid(arbeidsforhold.førsteArbeidsdag(), tidspunktForArbeidsuførhet, arbeidsforhold.ugyldigFravær.asSequence().map { fravær ->
      fravær.startdato to fravær.sluttdato
   })

private fun Arbeidsforhold.førsteArbeidsdag() =
   ferie.firstOrNull { ferie ->
      ferie.startdato == startdato
   }?.let { ferie ->
      ferie.sluttdato.plusDays(1)
   } ?: startdato

private fun lagOpptjeningsgrunnlag(arbeidsforholdliste: List<Arbeidsforhold>, tidspunktForArbeidsuførhet: LocalDate) =
   arbeidsforholdliste.map { arbeidsforhold ->
      arbeidsforhold to beregnOpptjeningstidForArbeidsforhold(arbeidsforhold, tidspunktForArbeidsuførhet)
   }

private fun beregnOpptjeningstid(startdatoForBeregning: LocalDate, sluttdatoForBeregning: LocalDate, perioderTilFradrag: Sequence<Pair<LocalDate, LocalDate>>) =
   fastsettOpptjeningsperiode(startdatoForBeregning, sluttdatoForBeregning, perioderTilFradrag)
      .count()

private fun fastsettOpptjeningsperiode(startdatoForBeregning: LocalDate, sluttdatoForBeregning: LocalDate, perioderTilFradrag: Sequence<Pair<LocalDate, LocalDate>>) =
   startdatoForBeregning.datesUntil(sluttdatoForBeregning)
      .filter { dato ->
         perioderTilFradrag.none { (fom, tom) ->
            fom <= dato && dato <= tom
         }
      }
