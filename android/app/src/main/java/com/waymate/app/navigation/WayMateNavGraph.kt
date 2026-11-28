package com.waymate.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.waymate.app.components.WayMateBottomBar
import com.waymate.app.screens.*
import com.waymate.app.theme.SurfaceBeige

@Composable
fun WayMateApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Splash.route

    // Show bottom bar only on top-level tabs
    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.MyTrips.route,
        Screen.Notifications.route,
        Screen.Profile.route
    )

    Scaffold(
        containerColor = SurfaceBeige,
        bottomBar = {
            if (showBottomBar) {
                WayMateBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = Screen.Splash.route
            ) {
                composable(Screen.Splash.route) {
                    SplashScreen(
                        onGetStarted = { navController.navigate(Screen.Onboarding.route) }
                    )
                }
                composable(Screen.Onboarding.route) {
                    OnboardingScreen(
                        onContinue = { navController.navigate(Screen.LoginOtp.route) }
                    )
                }
                composable(Screen.LoginOtp.route) {
                    LoginOtpScreen(
                        onLoginSuccess = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Splash.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Screen.Home.route) {
                    HomeDashboardScreen(
                        onSelectRideMode = { isShared ->
                            navController.navigate(Screen.FlightDetails.route)
                        },
                        onViewTrips = { navController.navigate(Screen.MyTrips.route) },
                        onViewPayments = { navController.navigate(Screen.PaymentRazorpay.route) },
                        onViewProfile = { navController.navigate(Screen.Profile.route) },
                        onViewUpcomingPool = { navController.navigate(Screen.PoolAvailable.route) }
                    )
                }
                composable(Screen.FlightDetails.route) {
                    FlightDetailsScreen(
                        onBack = { navController.popBackStack() },
                        onNext = { navController.navigate(Screen.FinalDestination.route) }
                    )
                }
                composable(Screen.FinalDestination.route) {
                    FinalDestinationScreen(
                        onBack = { navController.popBackStack() },
                        onNext = { navController.navigate(Screen.PassengerDetails.route) }
                    )
                }
                composable(Screen.PassengerDetails.route) {
                    PassengerDetailsScreen(
                        onBack = { navController.popBackStack() },
                        onNext = { navController.navigate(Screen.LuggageDetails.route) }
                    )
                }
                composable(Screen.LuggageDetails.route) {
                    LuggageDetailsScreen(
                        onBack = { navController.popBackStack() },
                        onNext = { navController.navigate(Screen.LanguagePreference.route) }
                    )
                }
                composable(Screen.LanguagePreference.route) {
                    LanguagePreferenceScreen(
                        onBack = { navController.popBackStack() },
                        onFindPool = { navController.navigate(Screen.FindingPool.route) }
                    )
                }
                composable(Screen.FindingPool.route) {
                    FindingPoolScreen(
                        onCancel = { navController.popBackStack() },
                        onPoolFound = { navController.navigate(Screen.PoolAvailable.route) }
                    )
                }
                composable(Screen.PoolAvailable.route) {
                    PoolAvailableScreen(
                        onBack = { navController.popBackStack() },
                        onViewVehicle = { navController.navigate(Screen.VehicleDetails.route) },
                        onViewPassengers = { navController.navigate(Screen.PoolPassengers.route) },
                        onViewDriver = { navController.navigate(Screen.DriverDetails.route) },
                        onConfirmPool = { navController.navigate(Screen.PoolConfirmation.route) }
                    )
                }
                composable(Screen.VehicleDetails.route) {
                    VehicleDetailsScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.PoolPassengers.route) {
                    PoolPassengersScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.DriverDetails.route) {
                    DriverDetailsScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.PoolConfirmation.route) {
                    PoolConfirmationScreen(
                        onTrackCab = { navController.navigate(Screen.LiveTracking.route) }
                    )
                }
                composable(Screen.LiveTracking.route) {
                    LiveTrackingScreen(
                        onBack = { navController.popBackStack() },
                        onStartJourney = { navController.navigate(Screen.JourneyProgress.route) }
                    )
                }
                composable(Screen.JourneyProgress.route) {
                    JourneyProgressScreen(
                        onReachDestination = { navController.navigate(Screen.RideCompleted.route) }
                    )
                }
                composable(Screen.RideCompleted.route) {
                    RideCompletedScreen(
                        onProceedToPayment = { navController.navigate(Screen.PaymentRazorpay.route) }
                    )
                }
                composable(Screen.PaymentRazorpay.route) {
                    PaymentRazorpayScreen(
                        onBack = { navController.popBackStack() },
                        onPaymentSuccess = { navController.navigate(Screen.RatingFeedback.route) }
                    )
                }
                composable(Screen.RatingFeedback.route) {
                    RatingFeedbackScreen(
                        onSubmit = {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                            }
                        }
                    )
                }
                composable(Screen.MyTrips.route) {
                    MyTripsScreen(
                        onBack = { navController.popBackStack() },
                        onViewActiveTrip = { navController.navigate(Screen.PoolAvailable.route) }
                    )
                }
                composable(Screen.Notifications.route) {
                    NotificationsScreen(onBack = { navController.popBackStack() })
                }
                composable(Screen.Profile.route) {
                    ProfileScreen(
                        onBack = { navController.popBackStack() },
                        onSignOut = {
                            navController.navigate(Screen.LoginOtp.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}

