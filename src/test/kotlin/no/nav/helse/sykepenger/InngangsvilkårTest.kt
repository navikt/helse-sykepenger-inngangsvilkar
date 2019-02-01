package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import java.time.LocalDate
import kotlin.test.Test

class InngangsvilkårTest {

   @Test
   fun `søker_må_oppfylle_samtlige_inngangsvilkår`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-29")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      val bostedLandISykdomsperiode = "Norge"
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")
      val alder = 35
      val aktuellMånedsinntekt = 38000L
      val rapportertMånedsinntekt = 400000L
      val fastsattÅrsinntekt = aktuellMånedsinntekt * 12
      val grunnbeløp = 96883L
      val harVurdertInntekt = false

      val soknad = Søknad(førsteSykdomsdag, datoForAnsettelse, alder, bostedLandISykdomsperiode, emptyList(), søknadSendt,
         førsteDagSøknadGjelderFor, aktuellMånedsinntekt, rapportertMånedsinntekt, fastsattÅrsinntekt, grunnbeløp, harVurdertInntekt)


      assertJa(inngangsvilkår.evaluer(soknad))
   }
}
