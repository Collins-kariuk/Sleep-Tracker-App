package com.example.sleeptrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sleeptrackerapp.ui.theme.SleepTrackerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SleepTrackerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BackgroundImage()
                    AppNavigation()
                    InitialScreenText(
                        welcome = "Welcome to Better Sleep",
                        ready = "Are you ready to better your sleep?",
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun InitialScreenText(welcome: String, ready: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp), // Add padding at the top
        verticalArrangement = Arrangement.Top // Align the content to the top
    ) {
        Text(
            text = welcome,
            fontSize = 24.sp, // Reduced font size
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth() // Ensure the text is centered
                .padding(16.dp)
        )
        Text(
            text = ready,
            fontSize = 18.sp, // Reduced font size
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth() // Ensure the text is centered
                .padding(16.dp)
        )
    }
}

@Composable
fun BackgroundImage(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.blumenwiese_bei_obermaiselstein)
    Image (
        painter = image,
        contentDescription = stringResource(R.string.background_description),
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        alpha = 0.7f
    )
}

// Define a composable function named SleepBenefitsScreen. This function can be used to create
// a UI screen in the app. Composable functions can be reused throughout the app.
@Composable
fun SleepBenefitsScreen(navController: NavController, modifier: Modifier = Modifier) {

    val sleepBenefitsBackImage = painterResource(id = R.drawable.sleeping_cat)
    Image (
        painter = sleepBenefitsBackImage,
        contentDescription = "A black cat sleeping in front of a dark blue wall",
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    // Start a column layout to arrange its children vertically.
    Column(
        // Apply a modifier to the Column.
        // Modifiers allow you to manipulate the UI elements, such as changing size, adding padding,
        // or handling user input.
        modifier = modifier
            .fillMaxSize() // Make the Column fill the entire available screen space.
            .padding(16.dp), // Add padding of 16 density-independent pixels (dp) on all sides.

        // Align the children of the Column to be centered horizontally within the Column.
        horizontalAlignment = Alignment.CenterHorizontally,

        // Arrange the children of the Column to be centered vertically within the Column.
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { navController.navigateUp() }) {
            Text(text = "Back to Home")
        }
        // Create a text element with the title "Benefits of Good Sleep"
        // This will be the heading of the screen.
        Text(
            text = "Benefits of Good Sleep",
            // Apply the 'titleLarge' text style from the MaterialTheme.
            style = MaterialTheme.typography.titleLarge
        )

        // Create another text element that contains a block of placeholder text (Lorem Ipsum).
        // This serves as the body text for the screen.
        Text(
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. " +
                    "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
                    "ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit " +
                    "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur " +
                    "sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
                    "mollit anim id est laborum.",
            // Apply padding to the text to separate it from other UI elements.
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("sleep_benefits") { SleepBenefitsScreen(navController) }
        composable("sign_up") { SignUpScreen(navController) }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    // Use a Box to allow for layering of composables on top of each other
    // Used to keep the content at the bottom center of the screen.
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Apply padding around the box
        contentAlignment = Alignment.BottomCenter // Align content to the bottom center
    ) {
        // Column to stack buttons vertically
        Column(
            // Center column contents horizontally
            horizontalAlignment = Alignment.CenterHorizontally,
            // Space between the column contents
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Button to NAVIGATE TO THE BENEFITS SCREEN
            Button(
                onClick = { navController.navigate("sleep_benefits") },
                // Apply padding to only the top of the first button to push it upwards
                modifier = Modifier.padding(bottom = 1.dp)
            ) {
                Text(text = "Learn About Sleep Benefits")
            }
            // Button for USER LOGIN
            Button(
                onClick = { /* TODO: Add navigation to login screen here */ }
            ) {
                Text(text = "Login to Track Sleep")
            }

            // New Sign Up Button
            Button(
                onClick = { navController.navigate("sign_up") },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}

@Composable
fun SignUpScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up Page", style = MaterialTheme.typography.titleLarge)
        // Here, you'll add the logic or UI for Google Sign-In or custom authentication
    }
}

@Preview(showBackground = true)
@Composable
fun SleepTrackerPreview() {
    SleepTrackerAppTheme {
        BackgroundImage(modifier = Modifier.fillMaxSize())
        InitialScreenText(
            welcome = "Welcome to Better Sleep",
            ready = "Are you ready to better your sleep?",
            modifier = Modifier
        )
        // Previewing HomeScreen with a fake NavController for illustration
        HomeScreen(navController = rememberNavController())

        // Preview the SleepBenefitsScreen for a change
//        SleepBenefitsScreen(navController = rememberNavController())

        // Preview the SignUpScreen for a change
//        SignUpScreen(navController = rememberNavController())


    }
}