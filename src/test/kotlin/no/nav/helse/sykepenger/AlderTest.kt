package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import kotlin.test.Test

class AlderTest {

   @Test
   fun `søker_er_ikke_for_gammel_når_han_er_under_67`() {
      assertNei(søkerErForGammel(66))
   }

   @Test
   fun `søker_er_kanskje_for_gammel_når_han_er_67_68_eller_69_år`() {
      assertKanskje(søkerErForGammel(67))
      assertKanskje(søkerErForGammel(68))
      assertKanskje(søkerErForGammel(69))
   }

   @Test
   fun `søker_er_for_gammel_dersom_han_er_fylt_70_år`() {
      assertJa(søkerErForGammel(70))
   }
}
