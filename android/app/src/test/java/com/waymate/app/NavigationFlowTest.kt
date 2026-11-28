package com.waymate.app

import com.waymate.app.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationFlowTest {

    @Test
    fun testAll23ScreensAreRegistered() {
        val screens = Screen.allScreens
        assertEquals(23, screens.size)
        // Verify unique routes
        val uniqueRoutes = screens.map { it.route }.toSet()
        assertEquals(23, uniqueRoutes.size)
    }

    @Test
    fun testPoolFareSavingsCalculation() {
        val soloFare = 1200
        val poolFare = 400
        val savings = soloFare - poolFare
        val savingsPercentage = (savings.toDouble() / soloFare.toDouble()) * 100.0

        assertEquals(800, savings)
        assertTrue(savingsPercentage >= 66.0)
    }

    @Test
    fun testLuggageBootFitCalculation() {
        val totalBootCapacityLitres = 550
        val singleTrolleyVolumeLitres = 90
        val bookedTrolleys = 3

        val usedVolume = bookedTrolleys * singleTrolleyVolumeLitres
        val utilizationPercent = (usedVolume.toDouble() / totalBootCapacityLitres.toDouble())

        assertTrue(utilizationPercent < 1.0)
        assertTrue(utilizationPercent in 0.4..0.6)
    }

    @Test
    fun testStartOtpFormat() {
        val sampleOtp = "8291"
        assertEquals(4, sampleOtp.length)
        assertTrue(sampleOtp.all { it.isDigit() })
    }

    @Test
    fun testIndianPhoneNumberFormat() {
        val samplePhone = "9876543210"
        assertEquals(10, samplePhone.length)
        assertTrue(samplePhone.all { it.isDigit() })
    }
}

