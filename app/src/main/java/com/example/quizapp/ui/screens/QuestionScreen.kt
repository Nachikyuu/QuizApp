package com.example.quizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizapp.data.sampleQuestions
import com.example.quizapp.ui.theme.QuizAppTheme


@Composable
fun QuestionScreen(
    onQuizFinished: (Int) -> Unit, // Callback with the final score
    modifier: Modifier = Modifier
) { //die sich verändernden Variablen
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    val currentQuestion = sampleQuestions.getOrNull(currentQuestionIndex)

    // Neue state Variablen, um richtige oder falsche Fragen zu berücksichtigen
    var selectedAnswerIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    // Effect, der den answer state zurücksetzt, wenn sich die Frage ändert, nach Klick des Buttons
    LaunchedEffect(currentQuestionIndex) {
        selectedAnswerIndex = null
        isAnswered = false
    }
    // das passiert, nachdem man die letzte Frage beantwortet hat
    if (currentQuestion == null) {
        LaunchedEffect(Unit) {
            onQuizFinished(score)
        }
        Text("Es lädt... Bitte warten... :)")
        return
    }


    //Jetpack Compose Layout für den Frage-Screen
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text( //Stand, bei welcher Frage man sich gerade befindet
            text = "Frage ${currentQuestionIndex + 1}/${sampleQuestions.size}",
            style = MaterialTheme.typography.labelMedium,
        )
        // Text mit der jeweiligen Frage
        Text(
            text = currentQuestion.text,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        //legt die Farbe der Buttons fest, je nachdem ob die Antwort richtig oder falsch ist
        currentQuestion.answers.forEachIndexed { index, answerText ->
            val isCorrectAnswer = index == currentQuestion.correctAnswerIndex
            val buttonColors = when {
                !isAnswered -> ButtonDefaults.buttonColors() // Default Farben vor der Antwort
                index == selectedAnswerIndex && isCorrectAnswer -> ButtonDefaults.buttonColors(containerColor = Color.Green) // Ausgewählt und richtig
                index == selectedAnswerIndex && !isCorrectAnswer -> ButtonDefaults.buttonColors(containerColor = Color.Red) // Ausgewählt und falsch
                isAnswered && isCorrectAnswer -> ButtonDefaults.buttonColors(containerColor = Color.Green.copy(alpha = 0.7f)) // Richtig, aber nicht ausgewählt
                else -> ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
            }
            //legt die Textfarbe der Antwortmöglichkeiten fest, damit diese nicht mit dem Hintergrund des Buttons noch lesbar sind
            val textColor = when {
                !isAnswered -> MaterialTheme.colorScheme.onPrimary
                index == selectedAnswerIndex || (isCorrectAnswer) -> Color.White
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            //Button nur anklickbar, wenn noch Frage noch nicht beantwortet wurde
            //Score erhöht sich bei richtiger Antwort
            Button(
                onClick = {
                    if (!isAnswered) {
                        isAnswered = true
                        selectedAnswerIndex = index
                        if (isCorrectAnswer) {
                            score++
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = buttonColors,
                // Button wird nach Anklicken disabled
                //auskommentiert, weil die Farben sonst nicht angezeigt werden,
                //aber dadurch wird eine kurze Vorschau der nächsten Antworten sichtbar:
                //enabled = !isAnswered
            ) {
                Text(answerText, color = textColor)
            }
        }

        if (isAnswered) {
            Button(
                onClick = {
                    if (currentQuestionIndex < sampleQuestions.size - 1) {
                        currentQuestionIndex++
                        //LaunchedEffect macht den Reset
                    } else {
                        onQuizFinished(score)
                    }
                },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text(if (currentQuestionIndex < sampleQuestions.size - 1) "Nächste Frage" else "Quiz beenden")
            }
        }
        // Anzeige des Punktestands am unteren Rand des Bildschirms
        Text("Punktestand: $score", modifier = Modifier.padding(top = 16.dp))

    }
}

//Preview Composable
@Preview(showBackground = true)
@Composable
fun QuestionScreenPreview() {
    QuizAppTheme {
        QuestionScreen(onQuizFinished = {})
    }
}