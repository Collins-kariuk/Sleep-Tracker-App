package com.example.sleeptrackerapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.CalendarToday
//import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
        composable("new_sleep_entry") { NewSleepEntryScreen(navController) }
    }
}
@Composable
fun NewSleepEntryScreen(navController: NavController) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // States for date and time pickers
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var sleepEntryDate by remember { mutableStateOf("") }
    var sleepEntryTime by remember { mutableStateOf("") }
    var wakeUpTime by remember { mutableStateOf("") }
    var sleepDuration by remember { mutableStateOf("") }

    // Formatters for date and time
    val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    // ...

    // Use Dialogs for picking date and time
    // When date is selected from the DatePickerDialog, update the date state variable
    val onDateSelected = { year: Int, month: Int, dayOfMonth: Int ->
        calendar.set(year, month, dayOfMonth)
        sleepEntryDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(calendar.time)
    }

    // When time is selected from the TimePickerDialog, update the time state variable
    val onTimeSelected = { hourOfDay: Int, minute: Int ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        sleepEntryTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time)
    }

    // Show the DatePickerDialog
    if (showDatePicker) {
        showDatePicker = false
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth -> onDateSelected(year, month, dayOfMonth) },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Show the TimePickerDialog
    if (showTimePicker) {
        showTimePicker = false
        TimePickerDialog(
            context,
            { _, hourOfDay, minute -> onTimeSelected(hourOfDay, minute) },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true // Use 24-hour format
        ).show()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Display date, time of sleep, and wake up time fields
        OutlinedTextField(
            value = sleepEntryDate,
            onValueChange = { },
            label = { Text("Date of sleep") },
            readOnly = true, // Makes the field not editable; only clickable
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
//                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = "Select Date",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            }
        )

        OutlinedTextField(
            value = sleepEntryTime,
            onValueChange = { },
            label = { Text("Time of sleep") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
//                    imageVector = Icons.Default.AccessTime,
                    contentDescription = "Select Time",
                    modifier = Modifier.clickable { showTimePicker = true }
                )
            }
        )

        OutlinedTextField(
            value = wakeUpTime?.let { timeFormatter.format(it) } ?: "",
            onValueChange = {},
            label = { Text("Wake up time") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable { /* Show TimePickerDialog here */ }
                )
            }
        )

        // Display the calculated sleep duration
        Text(text = "Sleep duration: $sleepDuration")

        // Buttons for Reset, Cancel, Submit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Handle Reset */ }) {
                Text("Reset")
            }
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }
            Button(onClick = { /* Handle Submit */ }) {
                Text("Submit")
            }
        }
    }
}

// Calculate the sleep duration based on the time of sleep and wake up time
fun calculateSleepDuration(timeOfSleep: Time?, wakeUpTime: Time?): String {
    if (timeOfSleep != null && wakeUpTime != null) {
        // Calculate duration here
    }
    return ""
}

// Functions to show DatePickerDialog and TimePickerDialog
fun showDatePicker(context: Context, dateSetListener: DatePickerDialog.OnDateSetListener) {
    // Implementation
}

fun showTimePicker(context: Context, timeSetListener: TimePickerDialog.OnTimeSetListener) {
    // Implementation
}

//fun SaveSleepEntry(date: String, timeOfSleep: String, wakeUpTime: String, context: Context) {
//    // This is where you'll save the sleep data to the database
//    val sharedPref = context.getSharedPreferences("SleepData", context.MODE_PRIVATE)
//    with(sharedPref.edit()) {
//        putString("DATE_KEY", date)
//        putString("SLEEP_TIME_KEY", timeOfSleep)
//        putString("WAKE_UP_TIME_KEY", wakeUpTime)
//        apply()
//    }
//}

@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
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
//            // Button for USER LOGIN
//            Button(
//                onClick = { /* TODO: Add navigation to login screen here */ }
//            ) {
//                Text(text = "Login to Track Sleep")
//            }

            // Add a button or other UI element that when clicked, navigates to the new sleep entry screen
            Button(onClick = { navController.navigate("new_sleep_entry") }) {
                Text("Add New Sleep Entry")
            }

            // New Sign Up Button
            Button(
                onClick = {
                    context.startActivity(Intent(context, SignInActivity::class.java))
                },
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
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up Page", style = MaterialTheme.typography.titleLarge)
        // Here, you'll add the logic or UI for Google Sign-In or custom authentication
//        Button(onClick = { initiateGoogleSignIn() }) {
//            Text(text = "Sign In with Google")
        }
    }

@Preview(showBackground = true)
@Composable
fun SleepTrackerPreview() {
    SleepTrackerAppTheme {
//        BackgroundImage(modifier = Modifier.fillMaxSize())
//        InitialScreenText(
//            welcome = "Welcome to Better Sleep",
//            ready = "Are you ready to better your sleep?",
//            modifier = Modifier
//        )

        // Previewing HomeScreen with a fake NavController for illustration
        HomeScreen(navController = rememberNavController())

        // Preview the SleepBenefitsScreen for a change
//        SleepBenefitsScreen(navController = rememberNavController())

        // Preview the SignUpScreen for a change
//        SignUpScreen(navController = rememberNavController())

        // Preview the NewSleepEntryScreen for a change
        NewSleepEntryScreen(navController = rememberNavController())
    }
}