package no.nav.helse

import org.junit.jupiter.api.Test
import java.time.LocalDate

class InngangsvilkårTest {

   @Test
   fun `søker må oppfylle samtlige inngangsvilkår`() {
      val førsteSykdomsdag = LocalDate.parse("2019-01-29")
      val datoForAnsettelse = LocalDate.parse("2019-01-01")
      val bostedLandISykdomsperiode = "Norge"
      val søknadSendt = LocalDate.parse("2019-04-30")
      val førsteDagSøknadGjelderFor = LocalDate.parse("2019-01-29")

      val soknad = Søknad(førsteSykdomsdag, datoForAnsettelse, bostedLandISykdomsperiode, emptyList(), søknadSendt, førsteDagSøknadGjelderFor)


      assertJa(inngangsvilkår.evaluer(soknad))
   }
}
