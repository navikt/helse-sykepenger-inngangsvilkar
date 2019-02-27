package no.nav.helse.sykepenger.vilkar

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test

class AlderTest {

   @Test
   fun `søker er ikke for gammel når han er under 67`() {
      assertNei(søkerErForGammel(66))
   }

   @Test
   fun `søker er kanskje for gammel når han er 67, 68 eller 69 år`() {
      assertKanskje(søkerErForGammel(67))
      assertKanskje(søkerErForGammel(68))
      assertKanskje(søkerErForGammel(69))
   }

   @Test
   fun `søker er for gammel dersom han er fylt 70 år`() {
      assertJa(søkerErForGammel(70))
   }
}
