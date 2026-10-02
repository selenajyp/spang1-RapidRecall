package com.example.spang1_rapidrecall
//TEXT STYLING: https://developer.android.com/develop/ui/compose/text/fonts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spang1_rapidrecall.ui.theme.Spang1RapidRecallTheme

@Composable
fun AttemptSummaryDisplay(
    totalAttempts: Int,
    numCorrectAttempts: Int,
    onNavigateToHome: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        //set up a separate box to hold the back button in top left
        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Button(
                onClick = onNavigateToHome,
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text("Home")
            }
        }
        //the main stuff
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Attempt Summary Screen"
            )

            Spacer(modifier = Modifier.height(16.dp))

            val accuracyPercentage = if (totalAttempts > 0) {
                "${(numCorrectAttempts * 100 / totalAttempts)}%"
            } else {
                "0%"
            }

            Text(
                text = "Total Attempts: $totalAttempts"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Correct Attempts: $numCorrectAttempts"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Accuracy Percentage: $accuracyPercentage"
            )
        }
    }
}


