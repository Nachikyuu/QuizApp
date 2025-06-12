package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.ui.theme.QuizAppTheme

@Composable
fun ScoreScreen(
    score: Int,
    totalQuestions: Int,
    onPlayAgainClicked: () -> Unit,
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
            text = "Gratulation",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Danke für die Teilnahme",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            //finaler Score wird angezeigt mit "Anzahl an richtig beantworteten Fragen" / "Gesamtzahl der Fragen"
            text = "Dein Score: $score / $totalQuestions",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(32.dp))

        //Navigation durch den Button und Route definiert wie im QuizAppNavigation-Composable in MainActivity (zurück zum QuestionScreen)
        Button(onClick = onPlayAgainClicked) {
            Text("Nochmal!")
        }
    }
}

//Preview Composable mit Beispiel-Score
@Preview(showBackground = true)
@Composable
fun ScoreScreenPreview() {
    QuizAppTheme {
        ScoreScreen(score = 7, totalQuestions = 10, onPlayAgainClicked = {})
    }
}