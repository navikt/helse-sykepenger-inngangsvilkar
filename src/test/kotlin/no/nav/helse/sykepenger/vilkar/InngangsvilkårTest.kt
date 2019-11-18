package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.sykepenger.vilkar.kontrakt.Arbeidsforhold
import no.nav.helse.sykepenger.vilkar.kontrakt.Inntekt
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate

class InngangsvilkårTest {

   @Test
   fun `søker må oppfylle samtlige inngangsvilkår`() {
      val opptjeningstid = 28
      val erMedlem = true
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")
      val sisteDagSøknadGjelderFor = LocalDate.parse("2019-02-13")
      val sisteMuligeSykepengedag = LocalDate.parse("2019-04-01")
      val alder = 35
      val fastsattÅrsinntekt = 38000L * 12
      val grunnbeløp = 96883L

      val soknad = Vilkårsgrunnlag(listOf(
         Inntekt(BigDecimal(2000)) to listOf(
            Arbeidsforhold(
               startdato = LocalDate.now().minusDays(opptjeningstid.toLong()),
               ferie = emptyList(),
               ugyldigFravær = emptyList()
            )
         )
      ), LocalDate.now(), opptjeningstid, alder, erMedlem, emptyList(), søknadSendt,
         førsteDagSøknadGjelderFor, sisteDagSøknadGjelderFor, sisteMuligeSykepengedag, fastsattÅrsinntekt, grunnbeløp)


      assertJa(inngangsvilkår.evaluer(soknad))
   }
}
