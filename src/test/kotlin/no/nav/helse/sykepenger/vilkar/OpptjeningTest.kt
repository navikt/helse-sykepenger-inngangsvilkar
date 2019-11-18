package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import no.nav.helse.sykepenger.vilkar.kontrakt.Arbeidsforhold
import no.nav.helse.sykepenger.vilkar.kontrakt.Inntekt
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class OpptjeningTest {

   @Test
   fun `søker oppfyller opptjeningstid`() {
      val opptjeningstidSlutt = LocalDate.now()
      val opptjeningstidStart = opptjeningstidSlutt.minusDays(28)

      val vilkårsgrunnlag = testSøknad(
         inntekterMedArbeidsforhold = listOf(
            Inntekt(BigDecimal(1000)) to listOf(
               Arbeidsforhold(opptjeningstidStart, emptyList(), emptyList())
            )
         ),
         tidspunktForArbeidsuførhet = opptjeningstidSlutt
      )

      assertJa(harArbeidsforholdMedOpptjening.evaluer(vilkårsgrunnlag))
   }

   @Test
   fun `søker har ikke oppfylt opptjeningstid når den har jobbet mindre enn 28 dager`() {
      val opptjeningstidSlutt = LocalDate.now()
      val opptjeningstidStart = opptjeningstidSlutt.minusDays(27)

      val vilkårsgrunnlag = testSøknad(
         inntekterMedArbeidsforhold = listOf(
            Inntekt(BigDecimal(1000)) to listOf(
               Arbeidsforhold(opptjeningstidStart, emptyList(), emptyList())
            )
         ),
         tidspunktForArbeidsuførhet = opptjeningstidSlutt
      )

      assertNei(harArbeidsforholdMedOpptjening.evaluer(vilkårsgrunnlag))
   }

   @Test
   fun `søker som har inntekt med to mulige arbeidsforhold oppfyller kanskje opptjeningstid`() {
      val opptjeningstidSlutt = LocalDate.now()

      val vilkårsgrunnlag = testSøknad(
         inntekterMedArbeidsforhold = listOf(
            Inntekt(BigDecimal(1000)) to listOf(
               Arbeidsforhold(opptjeningstidSlutt.minusDays(27), emptyList(), emptyList()),
               Arbeidsforhold(opptjeningstidSlutt.minusDays(28), emptyList(), emptyList())
            )
         ),
         tidspunktForArbeidsuførhet = opptjeningstidSlutt
      )

      assertKanskje(harArbeidsforholdMedOpptjening.evaluer(vilkårsgrunnlag))
   }

   @Test
   fun `søker som har inntekt med to mulige arbeidsforhold, men uten nok opptjeningstid, oppfyller ikke opptjeningstid`() {
      val opptjeningstidSlutt = LocalDate.now()

      val vilkårsgrunnlag = testSøknad(
         inntekterMedArbeidsforhold = listOf(
            Inntekt(BigDecimal(1000)) to listOf(
               Arbeidsforhold(opptjeningstidSlutt.minusDays(27), emptyList(), emptyList()),
               Arbeidsforhold(opptjeningstidSlutt.minusDays(27), emptyList(), emptyList())
            )
         ),
         tidspunktForArbeidsuførhet = opptjeningstidSlutt
      )

      assertNei(harArbeidsforholdMedOpptjening.evaluer(vilkårsgrunnlag))
   }

   @Test
   fun `søker har ikke oppfylt opptjeningstid når den har ingen inntekter`() {
      val opptjeningstidSlutt = LocalDate.now()

      val vilkårsgrunnlag = testSøknad(
         inntekterMedArbeidsforhold = emptyList(),
         tidspunktForArbeidsuførhet = opptjeningstidSlutt
      )

      assertNei(harArbeidsforholdMedOpptjening.evaluer(vilkårsgrunnlag))
   }

   @Test
   fun `søker har ikke oppfylt opptjeningstid når den har ingen arbeidsforhold`() {
      val opptjeningstidSlutt = LocalDate.now()

      val vilkårsgrunnlag = testSøknad(
         inntekterMedArbeidsforhold = listOf(
            Inntekt(BigDecimal(1000)) to emptyList()
         ),
         tidspunktForArbeidsuførhet = opptjeningstidSlutt
      )

      assertNei(harArbeidsforholdMedOpptjening.evaluer(vilkårsgrunnlag))
   }
}
