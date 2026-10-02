package com.example.spang1_rapidrecall
//TEXT STYLING: https://developer.android.com/develop/ui/compose/text/fonts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.spang1_rapidrecall.ui.theme.Spang1RapidRecallTheme

@Composable
fun StartScreenDisplay(
    //setting us up to navigate between routes
    onNavigateToHome: () -> Unit = {},
    onStartGame: (Int) -> Unit = {}
) {
    var sequenceLengthInput by remember { mutableStateOf("") } //users input
    var invalidSequenceLength by remember { mutableStateOf(false) } //bool for validating input later

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Length of Desired Sequence (1-10)",
                fontWeight = FontWeight.Bold,
            )
            //if user input is invalid, provide error message, otherwise dont show
            if (invalidSequenceLength) {
                Text(
                    text = "Invalid Sequence Length",
                    color = Color.Red
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    //
                    value = sequenceLengthInput,
                    onValueChange = {
                        sequenceLengthInput = it
                        if (invalidSequenceLength) {
                            invalidSequenceLength = false
                        }
                    },
                    isError = invalidSequenceLength,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(120.dp)
                )

                Button(
                    onClick = {
                        /*validating input . if its an integer and between 1-10 inclusive then it returns a num and its all good, otherwise error message will show
                        This syntax was introduced by Google Gemini when prompted "How can I validate that the input is an integer in Kotlin?", I then went to find the official documentation so I did not use it incorrectly
                        toIntOrNull: https://kotlinlang.org/api/core/kotlin-stdlib/kotlin.text/to-int-or-null.html*/
                        val length = sequenceLengthInput.toIntOrNull()
                        if ((length == null) || (length !in 1..10)) { //length not in ...
                            invalidSequenceLength = true
                        } else {
                            invalidSequenceLength = false
                            onStartGame(length)
                        }
                    }
                ) {
                    Text("Start")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenDisplayPreview() {
    Spang1RapidRecallTheme {
        StartScreenDisplay()
    }
}
