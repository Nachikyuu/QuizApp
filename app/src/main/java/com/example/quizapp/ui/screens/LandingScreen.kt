package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.theme.QuizAppTheme


//Screen, auf dem man zunächst willkommen geheißen wird
@Composable
fun LandingScreen(
    onStartQuizClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Willkommen beim Quiz!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        //Navigation durch den Button und Route definiert wie im QuizAppNavigation-Composable in MainActivity (zum QuestionScreen)
        Button(onClick = onStartQuizClicked) {
            Text("Los geht's ->")
        }
    }
}

//Preview Composable
@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    QuizAppTheme {
        LandingScreen(onStartQuizClicked = {})
    }
}