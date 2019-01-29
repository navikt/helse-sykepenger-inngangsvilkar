package no.nav.helse

import org.junit.jupiter.api.Test
import java.time.LocalDate

class MedlemskapTest {

   @Test
   fun `søker oppfyller medlemskap`() {
      val bostedLandISykdomsperiode = "Norge"
      val soknad = Søknad(LocalDate.now(), LocalDate.now(), bostedLandISykdomsperiode, emptyList(), LocalDate.now(), LocalDate.now())

      assertJa(erMedlemAvFolketrygden.evaluer(soknad))
   }

   @Test
   fun `søker kan kanskje oppfylle medlemskap selv om han ikke bor i Norge`() {
      val bostedLandISykdomsperiode = "Danmark"
      val soknad = Søknad(LocalDate.now(), LocalDate.now(), bostedLandISykdomsperiode, emptyList(), LocalDate.now(), LocalDate.now())

      assertKanskje(erMedlemAvFolketrygden.evaluer(soknad))
   }

   @Test
   fun `søker må bo i Norge`() {
      assertJa(søkerBorINorge("Norge"))
   }

   @Test
   fun `søker kan ikke bo i andre land`() {
      assertNei(søkerBorINorge("norge"))
      assertNei(søkerBorINorge("Noreg"))
      assertNei(søkerBorINorge("Sverige"))
   }
}
