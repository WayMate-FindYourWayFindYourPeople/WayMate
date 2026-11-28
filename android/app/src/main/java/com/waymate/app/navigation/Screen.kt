package com.waymate.app.navigation

sealed class Screen(val route: String, val title: String) {
    object Splash : Screen("splash", "Splash")
    object Onboarding : Screen("onboarding", "Onboarding")
    object LoginOtp : Screen("login_otp", "Login & OTP")
    object Home : Screen("home", "Home Dashboard")
    object FlightDetails : Screen("flight_details", "Flight Details")
    object FinalDestination : Screen("final_destination", "Final Destination")
    object PassengerDetails : Screen("passenger_details", "Passenger Details")
    object LuggageDetails : Screen("luggage_details", "Luggage Details")
    object LanguagePreference : Screen("language_preference", "Language Preference")
    object FindingPool : Screen("finding_pool", "Finding Your Pool")
    object PoolAvailable : Screen("pool_available", "Pool Available")
    object VehicleDetails : Screen("vehicle_details", "Vehicle Details")
    object PoolPassengers : Screen("pool_passengers", "Co-Passengers")
    object DriverDetails : Screen("driver_details", "Driver Details")
    object PoolConfirmation : Screen("pool_confirmation", "Pool Confirmation")
    object LiveTracking : Screen("live_tracking", "Live Cab Tracking")
    object JourneyProgress : Screen("journey_progress", "Journey In Progress")
    object RideCompleted : Screen("ride_completed", "Ride Completed")
    object PaymentRazorpay : Screen("payment_razorpay", "Razorpay Payment")
    object RatingFeedback : Screen("rating_feedback", "Rating & Feedback")
    object MyTrips : Screen("trips", "My Trips")
    object Notifications : Screen("notifications", "Notifications")
    object Profile : Screen("profile", "Profile")

    companion object {
        val allScreens = listOf(
            Splash, Onboarding, LoginOtp, Home,
            FlightDetails, FinalDestination, PassengerDetails, LuggageDetails, LanguagePreference,
            FindingPool, PoolAvailable, VehicleDetails, PoolPassengers, DriverDetails, PoolConfirmation,
            LiveTracking, JourneyProgress, RideCompleted, PaymentRazorpay, RatingFeedback,
            MyTrips, Notifications, Profile
        )
    }
}

