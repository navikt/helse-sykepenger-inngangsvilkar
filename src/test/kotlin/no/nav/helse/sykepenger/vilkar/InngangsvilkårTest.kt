package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import org.junit.jupiter.api.Test
import java.time.LocalDate

class InngangsvilkårTest {

   @Test
   fun `søker må oppfylle samtlige inngangsvilkår`() {
      val opptjeningstid = 28
      val erMedlem = true
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")
      val alder = 35
      val fastsattÅrsinntekt = 38000L * 12
      val grunnbeløp = 96883L

      val soknad = Vilkårsgrunnlag(opptjeningstid, alder, erMedlem, emptyList(), søknadSendt,
         førsteDagSøknadGjelderFor, fastsattÅrsinntekt, grunnbeløp)


      assertJa(inngangsvilkår.evaluer(soknad))
   }
}
