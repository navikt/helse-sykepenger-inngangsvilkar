package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import no.nav.helse.sykepenger.vilkar.kontrakt.Arbeidsforhold
import no.nav.helse.sykepenger.vilkar.kontrakt.Inntekt
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class MaksDatoTest {

   @Test
   fun `maksdato nåes ikke i perioden gir ja`() {
      val søknad = testSøknad(
         førsteDagSøknadGjelderFor = LocalDate.now(),
         sisteDagSøknadGjelderFor = LocalDate.now().plusWeeks(2),
         sisteMuligeSykepengedag = LocalDate.now().plusWeeks(2).plusDays(1))
      assertJa(maksAntallSykepengedagerErIkkeBruktOpp.evaluer(søknad))
   }

   @Test
   fun `maksdato er siste dag i perioden gir ja`() {
      val søknad = testSøknad(
         førsteDagSøknadGjelderFor = LocalDate.now(),
         sisteDagSøknadGjelderFor = LocalDate.now().plusWeeks(2),
         sisteMuligeSykepengedag = LocalDate.now().plusWeeks(2))
      assertJa(maksAntallSykepengedagerErIkkeBruktOpp.evaluer(søknad))
   }

   @Test
   fun `maksdato var allerede før perioden gir nei`() {
      val søknad = testSøknad(
         førsteDagSøknadGjelderFor = LocalDate.now(),
         sisteDagSøknadGjelderFor = LocalDate.now().plusWeeks(2),
         sisteMuligeSykepengedag = LocalDate.now().minusDays(1))
      assertNei(maksAntallSykepengedagerErIkkeBruktOpp.evaluer(søknad))
   }

   @Test
   fun `maksdato nåes i perioden gir kanskje`() {
      val søknad = testSøknad(
         førsteDagSøknadGjelderFor = LocalDate.now(),
         sisteDagSøknadGjelderFor = LocalDate.now().plusWeeks(2),
         sisteMuligeSykepengedag = LocalDate.now().plusWeeks(1))
      assertKanskje(maksAntallSykepengedagerErIkkeBruktOpp.evaluer(søknad))
   }

   @Test
   fun `søker som oppfyller samtlige inngangsvilkår men med maksdato i perioden skal bli kanskje`() {
      val soknad = ellersOkSøknad(
         førsteDagSøknadGjelderFor = LocalDate.now(),
         sisteDagSøknadGjelderFor = LocalDate.now().plusWeeks(2),
         sisteMuligeSykepengedag = LocalDate.now().plusWeeks(1))
      assertKanskje(inngangsvilkår.evaluer(soknad))
   }

   @Test
   fun `søker som oppfyller samtlige inngangsvilkår men med før perioden skal også bli kanskje (toBeDecided)`() {
      val soknad = ellersOkSøknad(
         førsteDagSøknadGjelderFor = LocalDate.now(),
         sisteDagSøknadGjelderFor = LocalDate.now().plusWeeks(2),
         sisteMuligeSykepengedag = LocalDate.now().minusDays(1))
      assertKanskje(inngangsvilkår.evaluer(soknad))
   }

   @Test
   fun `søker som oppfyller samtlige inngangsvilkår med maksdato etter perioden skal bli ja`() {
      val soknad = ellersOkSøknad(
         førsteDagSøknadGjelderFor = LocalDate.now(),
         sisteDagSøknadGjelderFor = LocalDate.now().plusWeeks(2),
         sisteMuligeSykepengedag = LocalDate.now().plusWeeks(4))
      assertJa(inngangsvilkår.evaluer(soknad))
   }



   internal fun ellersOkSøknad(
      opptjeningstid: Int = 28,
      alder: Int = 35,
      erMedlem: Boolean = true,
      ytelser: List<String> = emptyList(),
      søknadSendt: LocalDate = LocalDate.now(),
      førsteDagSøknadGjelderFor: LocalDate = LocalDate.now(),
      sisteDagSøknadGjelderFor: LocalDate = LocalDate.now().plusWeeks(2),
      sisteMuligeSykepengedag: LocalDate = LocalDate.now().plusWeeks(4),
      fastsattÅrsinntekt: Long = 38000L * 12,
      grunnbeløp: Long = 96883L
   ) = Vilkårsgrunnlag(listOf(
      Inntekt(BigDecimal(2000)) to listOf(
         Arbeidsforhold(
            startdato = LocalDate.now().minusDays(opptjeningstid.toLong()),
            ferie = emptyList(),
            ugyldigFravær = emptyList()
         )
      )), LocalDate.now(), opptjeningstid, alder, erMedlem, ytelser, søknadSendt, førsteDagSøknadGjelderFor,
      sisteDagSøknadGjelderFor, sisteMuligeSykepengedag, fastsattÅrsinntekt, grunnbeløp)
}
