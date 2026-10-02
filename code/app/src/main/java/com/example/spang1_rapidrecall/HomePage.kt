package com.example.spang1_rapidrecall
//TEXT STYLING: https://developer.android.com/develop/ui/compose/text/fonts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spang1_rapidrecall.ui.theme.Spang1RapidRecallTheme

//the following code is based off of https://developer.android.com/codelabs/basic-android-kotlin-compose-navigation#3
enum class Screen(val route: String) {
    Home("home"),
    Start("start"),
    Game("game"),
    Log("log"),
    Summary("summary")
}

@Composable
fun MainApp() {
    val navController = rememberNavController()  //for navigating between routes/screens
    //the following variables are important - mostly so we can use them later
    var selectedSequenceLength by remember { mutableIntStateOf(5) } //user's selected length for sequence
    val attemptLogs = remember { mutableStateListOf<AttemptRecord>() }
    val totalAttempts = attemptLogs.size
    val numCorrectAttempts = attemptLogs.count { it.isCorrect }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomePageDisplay(
                    //displaying the three places a user can go to from homepage
                    onNavigateToStart = { navController.navigate(Screen.Start.route) },
                    onNavigateToLog = { navController.navigate(Screen.Log.route) },
                    onNavigateToSummary = { navController.navigate(Screen.Summary.route) }
                )
            }
            composable(Screen.Start.route) {
                //navigate to StartScreen process
                StartScreenDisplay(
                    onNavigateToHome = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) } },
                    onStartGame = { length ->   //saving the user's selected sequence length so game screen can use it later
                        selectedSequenceLength = length
                        navController.navigate(Screen.Game.route)   //navigate to game screen later
                    }
                )
            }
            composable(Screen.Game.route) {
                //navigate to GameScreen process
                GameScreenDisplay(
                    sequenceLength = selectedSequenceLength,    //passing user's sequence length down
                    onNavigateToHome = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) } },
                    onAttemptSubmitted = { record ->    //this is for the logs and summary screen. we gotta append/add records everytime
                        attemptLogs.add(record)
                    }
                )
            }
            composable(Screen.Log.route) {
                //navigate to LogScreen process
                LogScreenDisplay(
                    attemptLogs = attemptLogs,  //my logs that are needed to display, well, the logs
                    onNavigateToHome = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) } }
                )
            }
            composable(Screen.Summary.route) {
                AttemptSummaryDisplay(
                    //information needed for the attempt summary page
                    totalAttempts = totalAttempts,
                    numCorrectAttempts = numCorrectAttempts,
                    onNavigateToHome = { navController.navigate(Screen.Home.route) { popUpTo(Screen.Home.route) } }
                )
            }
        }
    }
}

@Composable
fun HomePageDisplay(    //the visible ui stuff
    //my 3 main pages/routes/screens ... :D
    onNavigateToStart: () -> Unit = {},
    onNavigateToLog: () -> Unit = {},
    onNavigateToSummary: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // header
            Text(
                text = "Rapid Recall",
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA6C9),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // column of buttons to navigate to diff pages
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(max = 300.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onNavigateToStart,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Start Game",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Button(
                    onClick = onNavigateToLog,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Attempt Logs",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Button(
                    onClick = onNavigateToSummary,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Attempt Summary",
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePageDisplayPreview() {
    Spang1RapidRecallTheme {
        HomePageDisplay()
    }
}
