package com.example.spang1_rapidrecall
//TEXT STYLING: https://developer.android.com/develop/ui/compose/text/fonts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spang1_rapidrecall.ui.theme.Spang1RapidRecallTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LogScreenDisplay(
    attemptLogs: List<AttemptRecord> = emptyList(),
    onNavigateToHome: () -> Unit = {}
) {
    //format the date in a certain way - i remember seeing it on stack overflow  or a blog somewhere but i cant find it
    val dateFormatter = remember { SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) }

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
        //main stuff here
        Column( //making sure everything is centered
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Attempt Log Screen",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA6C9)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (attemptLogs.isEmpty()) {    //no attempt logs logic
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No attempts logged yet"
                    )
                }
            } else {
                LazyColumn( //lazy column to show all logs/records
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(attemptLogs) { attempt ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Gray
                            )
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (attempt.isCorrect) "CORRECT" else "INCORRECT",
                                        fontWeight = FontWeight.Bold,
                                        color = if (attempt.isCorrect) Color.Green else Color.Red
                                    )
                                    Text(
                                        text = dateFormatter.format(Date(attempt.timestamp))
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Sequence Length: ${attempt.sequenceLength}"
                                )
                                Text(
                                    text = "Target Sequence: ${attempt.targetSequence}"
                                )
                                Text(
                                    text = "User's Guess: ${attempt.userGuess}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


