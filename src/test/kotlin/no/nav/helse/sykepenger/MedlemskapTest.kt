package no.nav.helse.sykepenger

import no.nav.helse.assertJa
import no.nav.helse.assertKanskje
import no.nav.helse.assertNei
import org.junit.jupiter.api.Test

class MedlemskapTest {

   @Test
   fun `søker oppfyller medlemskap`() {
      val søknad = testSøknad(bostedlandISykdomsperiode = "Norge")
      assertJa(harOppfyltMedlemskap.evaluer(søknad))
   }

   @Test
   fun `søker kan kanskje oppfylle medlemskap selv om han ikke bor i Norge`() {
      val søknad = testSøknad(bostedlandISykdomsperiode = "Danmark")
      assertKanskje(harOppfyltMedlemskap.evaluer(søknad))
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
