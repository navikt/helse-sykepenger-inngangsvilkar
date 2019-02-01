package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import kotlin.test.Test

class MedlemskapTest {

   @Test
   fun `søker_oppfyller_medlemskap`() {
      val søknad = testSøknad(bostedlandISykdomsperiode = "Norge")
      assertJa(harOppfyltMedlemskap.evaluer(søknad))
   }

   @Test
   fun `søker_kan_kanskje_oppfylle_medlemskap_selv_om_han_ikke_bor_i_Norge`() {
      val søknad = testSøknad(bostedlandISykdomsperiode = "Danmark")
      assertKanskje(harOppfyltMedlemskap.evaluer(søknad))
   }

   @Test
   fun `søker_må_bo_i_Norge`() {
      assertJa(søkerBorINorge("Norge"))
   }

   @Test
   fun `søker_kan_ikke_bo_i_andre_land`() {
      assertNei(søkerBorINorge("norge"))
      assertNei(søkerBorINorge("Noreg"))
      assertNei(søkerBorINorge("Sverige"))
   }
}
