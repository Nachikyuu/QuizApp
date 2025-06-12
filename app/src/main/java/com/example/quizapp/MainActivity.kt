package com.example.quizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quizapp.ui.screens.LandingScreen
import com.example.quizapp.ui.screens.QuestionScreen
import com.example.quizapp.ui.screens.ScoreScreen
import com.example.quizapp.data.sampleQuestions
import com.example.quizapp.ui.theme.QuizAppTheme

//Screens Konstanten, mit denen man navigiert, anhand Compose Navigation Implementation
object AppDestinations {
    const val LANDING_SCREEN = "landing"
    const val QUESTION_SCREEN = "question"
    const val SCORE_SCREEN = "score/{userScore}" // Route with argument
    const val SCORE_ARG = "userScore" // Key for the argument
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuizAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //ruft das Composable auf, damit überhaupt etwas ausgeführt wird
                    QuizAppNavigation()
                }
            }
        }
    }
}

// Composable für die Gesamtstruktur, Zusammenhänge zwischen den Screens werden festgelegt
@Composable
fun QuizAppNavigation() {
    val navController: NavHostController = rememberNavController()
    //Composable, das den Navigationsgraphen festlegt
    NavHost(
        navController = navController,
        startDestination = AppDestinations.LANDING_SCREEN
    ) { //definiert, wie von LandingScreen navigiert wird
        composable(AppDestinations.LANDING_SCREEN) {
            LandingScreen(
                //das passiert, wenn man auf den Los geht's! - Button klickt
                onStartQuizClicked = {
                    navController.navigate(AppDestinations.QUESTION_SCREEN) {
                        // User kann nicht zurückkehren zur LandingScreen
                        popUpTo(AppDestinations.LANDING_SCREEN) { inclusive = true }
                    }
                }
            )
        }
        //definiert, wie von QuestionScreen navigiert wird
        composable(AppDestinations.QUESTION_SCREEN) {
            QuestionScreen(
                onQuizFinished = { finalScore ->
                    // {userScore} wird mit dem jeweiligen Score ersetzt, wenn man zum ScoreScreen kommt
                    navController.navigate("${AppDestinations.SCORE_SCREEN.substringBefore("/{")}/$finalScore") {
                        popUpTo(AppDestinations.QUESTION_SCREEN) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = AppDestinations.SCORE_SCREEN,
            arguments = listOf(navArgument(AppDestinations.SCORE_ARG) { type = NavType.IntType })
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt(AppDestinations.SCORE_ARG) ?: 0
            ScoreScreen(
                score = score,
                //für den Gesamtscore wird die Gesamtzahl der Fragen genommen
                totalQuestions = sampleQuestions.size,
                // das passiert, wenn man auf den "Nochmal!" - Button klickt
                onPlayAgainClicked = {
                        // ScoreScreen und QuestionScreen werden frei und man navigiert direkt wieder zurück zur ersten Frage
                         navController.navigate(AppDestinations.QUESTION_SCREEN) {
                         popUpTo(AppDestinations.SCORE_SCREEN.substringBefore("/{")) { inclusive = true }
                    }
                }
            )
        }
    }
}