package com.example.sleeptrackerapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

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

/**
 * Displays a column with two text elements for an initial screen.
 *
 * This composable function creates a vertical arrangement of two text elements: one for a welcome
 * message and another for a ready message.
 * The `welcome` text appears at the top of the column with a larger font size, and the `ready` text
 * appears below it with a smaller font size.
 *
 * @param welcome The welcome message to be displayed. This string is shown at the top of the column
 * with a larger font size.
 * @param ready The ready message to be displayed below the welcome message. This string is shown
 * with a slightly smaller font size.
 * @param modifier An optional [Modifier] to apply to the column.
 */
@Composable
fun InitialScreenText(welcome: String, ready: String, modifier: Modifier = Modifier
) {
    // Start a Column composable that arranges its children vertically.
    Column(
        // Apply the provided modifier, set the column to fill the maximum available size,
        // and add padding at the top.
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp),

        // Align the contents of the Column to the top.
        verticalArrangement = Arrangement.Top
    ) {
        // Text composable for displaying the 'welcome' message.
        Text(
            // Set the text to the 'welcome' string.
            text = welcome,

            // Set the font size to 24 density-independent pixels (sp).
            fontSize = 24.sp,

            // Center-align the text.
            textAlign = TextAlign.Center,

            // Apply a modifier to fill the maximum width available and add padding around the text.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Text composable for displaying the 'ready' message.
        Text(
            // Set the text to the 'ready' string.
            text = ready,

            // Set the font size to 18 density-independent pixels (sp).
            fontSize = 18.sp,

            // Center-align the text.
            textAlign = TextAlign.Center,

            // Apply a modifier to fill the maximum width available and add padding around the text.
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

/**
 * Displays a background image with optional styling.
 *
 * This composable function displays an image as a background with a specified
 * content scale and opacity. The image is loaded from resources and scaled to
 * fill the available size while maintaining its aspect ratio.
 *
 * @param modifier An optional [Modifier] to apply to the Image composable.
 */
@Composable
fun BackgroundImage(modifier: Modifier = Modifier) {
    // Load an image resource using the 'painterResource' function and store it in 'image'.
    val image = painterResource(id = R.drawable.blumenwiese_bei_obermaiselstein)

    // Create an Image composable to display the loaded image.
    Image(
        // Use the loaded image resource as the painter for the Image composable.
        painter = image,

        // Provide a content description for the image, loaded from string resources.
        contentDescription = stringResource(R.string.background_description),

        // Apply the given modifier to the Image composable, setting it to fill the maximum
        // available size.
        modifier = modifier.fillMaxSize(),

        // Set the content scaling to 'Crop', which will crop the image to fill the bounds while
        // maintaining its aspect ratio.
        contentScale = ContentScale.Crop,

        // Set the alpha (opacity) of the image to 0.7, making it slightly transparent.
        alpha = 0.7f
    )
}

/**
 * Displays a screen with information about the benefits of good sleep.
 *
 * This composable function sets up a screen with a background image and a column layout
 * that contains a button and text elements. The button allows users to navigate back to the
 * previous screen using the provided [NavController]. The column arranges its children
 * vertically and includes a title and placeholder text for the benefits of good sleep.
 *
 * @param navController The [NavController] used for navigating back to the previous screen.
 * @param modifier An optional [Modifier] to apply to the Image and Column composables.
 */
@Composable
fun SleepBenefitsScreen(navController: NavController, modifier: Modifier = Modifier) {
    // Load an image resource to be used as a background and store it in 'sleepBenefitsBackImage'.
    val sleepBenefitsBackImage = painterResource(id = R.drawable.sleeping_cat)

    // Create an Image composable to display the loaded background image.
    Image(
        // Use the loaded image resource as the painter for the Image composable.
        painter = sleepBenefitsBackImage,

        // Provide a content description for the image for accessibility.
        contentDescription = "A black cat sleeping in front of a dark blue wall",

        // Apply the given modifier to the Image composable, setting it to fill the maximum
        // available size.
        modifier = modifier.fillMaxSize(),

        // Set the content scaling to 'Crop', which will crop the image to fill the bounds while
        // maintaining its aspect ratio.
        contentScale = ContentScale.Crop
    )

    // Start a column layout to arrange its children vertically.
    Column(
        // Apply a modifier to the Column to make it fill the entire available screen space and add
        // padding.
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),

        // Align the children of the Column to be centered horizontally within the Column.
        horizontalAlignment = Alignment.CenterHorizontally,

        // Arrange the children of the Column to be centered vertically within the Column.
        verticalArrangement = Arrangement.Center
    ) {
        // Create a Button composable with a click action to navigate up in the navigation stack.
        Button(onClick = { navController.navigateUp() }) {
            // Inside the button, create a Text composable to display the button's label.
            Text(text = "Back to Home")
        }

        // Create a Text composable to display the title "Benefits of Good Sleep".
        Text(
            text = "Benefits of Good Sleep",
            // Apply the 'titleLarge' text style from the MaterialTheme for styling.
            style = MaterialTheme.typography.titleLarge
        )

        // Create another Text composable for actual sleep benefits.
        Text(
            text = "WILL ENTER BENEFITS OF GOOD SLEEP HERE ONCE I AM DONE WITH OTHER THINGS",
            // Apply padding around the text to separate it from other UI elements.
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun AppNavigation() {
    // Create a NavController instance that remembers the navigation state.
    // NavController is used to navigate between composables in the app.
    val navController = rememberNavController()

    // Set up a NavHost, which is a container for navigation among the composables.
    NavHost(navController = navController, startDestination = "home") {
        // Define a composable route for the 'home' destination.
        // When navigated to 'home', the HomeScreen composable is displayed.
        composable("home") { HomeScreen(navController) }

        // Define a composable route for the 'sleep_benefits' destination.
        // When navigated to 'sleep_benefits', the SleepBenefitsScreen composable is displayed.
        composable("sleep_benefits") { SleepBenefitsScreen(navController) }

        // Define a composable route for the 'new_sleep_entry' destination.
        // When navigated to 'new_sleep_entry', the NewSleepEntryScreen composable is displayed.
        composable("new_sleep_entry") { NewSleepEntryScreen(navController) }

        // Define a composable route for the 'view_sleep_data' destination.
        // When navigated to 'view_sleep_data', the ViewSleepDataScreen composable is displayed.
        // LocalContext.current provides the current context to the composable.
        composable("view_sleep_data") { ViewSleepDataScreen(LocalContext.current) }
    }
}

@Composable
fun NewSleepEntryScreen(navController: NavController) {
    // Retrieve the current context from the LocalContext.
    // LocalContext is a special object that can be used to retrieve the current context.
    val context = LocalContext.current

    // Get an instance of the Calendar class to work with date and time.
    val calendar = Calendar.getInstance()

    // Define and remember mutable state variables to manage visibility of date and time pickers.
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var showWakeUpTimePicker by remember { mutableStateOf(false) }

    // Define and remember mutable state variables for storing the user's input for date and time.
    var sleepEntryDate by remember { mutableStateOf("") }
    var sleepEntryTime by remember { mutableStateOf("") }
    var wakeUpTime by remember { mutableStateOf("") }
    var sleepDuration by remember { mutableStateOf("") }

    // Create a SimpleDateFormat for formatting and parsing time in hours and minutes.
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    // Define a lambda function for handling date selection from DatePickerDialog.
    val onDateSelected = { year: Int, month: Int, dayOfMonth: Int ->
        calendar.set(year, month, dayOfMonth)
        sleepEntryDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(calendar.time)
    }

    // Define a lambda function for handling time selection from TimePickerDialog.
    val onTimeSelected = { hourOfDay: Int, minute: Int ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        sleepEntryTime = timeFormatter.format(calendar.time)
    }

    // Function to calculate and update the sleep duration based on the entered date and time.
    fun calculateAndUpdateSleepDuration() {
        if (sleepEntryDate.isNotBlank() && sleepEntryTime.isNotBlank() && wakeUpTime.isNotBlank()) {
            try {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
                val sleepDateTime = dateFormat.parse("$sleepEntryDate $sleepEntryTime")
                val wakeUpDateTime = dateFormat.parse("$sleepEntryDate $wakeUpTime")

                if (sleepDateTime != null && wakeUpDateTime != null && wakeUpDateTime.after(sleepDateTime)) {
                    val durationMillis = wakeUpDateTime.time - sleepDateTime.time
                    val durationHours = TimeUnit.MILLISECONDS.toHours(durationMillis)
                    sleepDuration = "$durationHours hours"
                }
            } catch (e: ParseException) {
                Toast.makeText(context, "Invalid date or time format", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Lambda function for handling wake-up time selection.
    val onWakeUpTimeSelected = { hourOfDay: Int, minute: Int ->
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        wakeUpTime = timeFormatter.format(calendar.time)
        calculateAndUpdateSleepDuration()
    }

    // Logic to show the DatePickerDialog when 'showDatePicker' is true.
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

    // Logic to show the TimePickerDialog for sleep time when 'showTimePicker' is true.
    if (showTimePicker) {
        showTimePicker = false
        TimePickerDialog(
            context,
            { _, hourOfDay, minute -> onTimeSelected(hourOfDay, minute) },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    // Logic to show the TimePickerDialog for wake-up time when 'showWakeUpTimePicker' is true.
    if (showWakeUpTimePicker) {
        showWakeUpTimePicker = false
        TimePickerDialog(
            context,
            { _, hourOfDay, minute -> onWakeUpTimeSelected(hourOfDay, minute) },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        ).show()
    }

    // Define a reset function to clear all input fields and reset state variables.
    val resetFields = {
        sleepEntryDate = ""
        sleepEntryTime = ""
        wakeUpTime = ""
        sleepDuration = ""
    }

    // Define a function to save the sleep entry to SharedPreferences.
    val saveSleepEntry = saveSleepEntry@{
        // Check for blank fields and display a message if any are blank.
        if (sleepEntryDate.isBlank() || sleepEntryTime.isBlank() || wakeUpTime.isBlank()) {
            Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_LONG).show()
            return@saveSleepEntry
        }

        // Access SharedPreferences and save the sleep entry data.
        val sharedPref = context.getSharedPreferences("SleepData", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("DATE_KEY", sleepEntryDate)
            putString("SLEEP_TIME_KEY", sleepEntryTime)
            putString("WAKE_UP_TIME_KEY", wakeUpTime)
            putString("SLEEP_DURATION_KEY", sleepDuration)
            apply()
        }

        val newEntry = SleepEntry(sleepEntryDate, sleepEntryTime, wakeUpTime, sleepDuration)
        saveSleepEntries(context, newEntry)

        // Notify the user that the sleep entry has been saved.
        Toast.makeText(context, "Sleep entry saved", Toast.LENGTH_SHORT).show()

        // Navigate back or to the confirmation screen.
        navController.popBackStack()
    }

    // Layout definition for the sleep entry screen.
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Composable for date input field.
        OutlinedTextField(
            value = sleepEntryDate,
            onValueChange = { },
            label = { Text("Date of sleep") },
            readOnly = true, // Makes the field not editable; only clickable
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.AccountBox,
                    contentDescription = "Select Date",
                    modifier = Modifier.clickable { showDatePicker = true }
                )
            }
        )

        // Composable for sleep time input field.
        OutlinedTextField(
            value = sleepEntryTime,
            onValueChange = { },
            label = { Text("Time of sleep") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select Time of sleep",
                    modifier = Modifier.clickable { showTimePicker = true }
                )
            }
        )

        // Composable for wake-up time input field.
        OutlinedTextField(
            value = wakeUpTime,
            onValueChange = { },
            label = { Text("Wake up time") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Select wake up time",
                    modifier = Modifier.clickable { showWakeUpTimePicker = true }
                )
            }
        )

        // Display the calculated sleep duration.
        Text(text = "Sleep duration: $sleepDuration")

        // Row layout for buttons.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Button to reset the fields.
            Button(onClick = resetFields) {
                Text("Reset")
            }

            // Button to cancel the entry and go back.
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancel")
            }

            // Button to submit and save the sleep entry.
            Button(onClick = saveSleepEntry) {
                Text("Submit")
            }

            // Button to navigate to the screen where sleep data is viewed.
            Button(onClick = { navController.navigate("view_sleep_data") }) {
                Text("View Sleep Data")
            }
        }
    }
}

fun getSleepEntries(context: Context): List<SleepEntry> {
    // Access the SharedPreferences file named 'SleepData' in private mode (only accessible by the
    // calling application).
    val sharedPref = context.getSharedPreferences("SleepData", Context.MODE_PRIVATE)

    // Retrieve the string representation of sleep entries stored in SharedPreferences with the key
    // 'SLEEP_ENTRIES'.
    // If there's no data present, return an empty JSON array string "[]".
    val entriesString = sharedPref.getString("SLEEP_ENTRIES", "[]")

    // Define the type token for a list of SleepEntry objects. This is necessary for Gson to
    // understand the type of data it should parse.
    val type = object : TypeToken<List<SleepEntry>>() {}.type

    // Use Gson to parse the JSON string back into a List of SleepEntry objects and return it.
    return Gson().fromJson(entriesString, type)
}

fun saveSleepEntries(context: Context, newEntry: SleepEntry) {
    // Access the SharedPreferences file named 'SleepData' in private mode.
    val sharedPref = context.getSharedPreferences("SleepData", Context.MODE_PRIVATE)

    // Start editing the SharedPreferences.
    val editor = sharedPref.edit()

    // Retrieve the existing list of sleep entries and convert it into a mutable list.
    val existingEntries = getSleepEntries(context).toMutableList()
    // Add the new sleep entry to the list.
    existingEntries.add(newEntry)

    // Calculate the date for two weeks ago to filter out older entries.
    val twoWeeksAgo = Calendar.getInstance().apply {
        add(Calendar.DAY_OF_YEAR, -14)
    }.time

    // Filter the list to keep only the entries from the last two weeks.
    val filteredEntries = existingEntries.filter {
        SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).parse(it.date)?.after(twoWeeksAgo) == true
    }.toMutableList()

    // Convert the filtered list of entries to a JSON string using Gson.
    val entriesString = Gson().toJson(filteredEntries)
    // Save the JSON string of filtered entries to SharedPreferences under the key 'SLEEP_ENTRIES'.
    editor.putString("SLEEP_ENTRIES", entriesString)
    // Apply the changes to the SharedPreferences.
    editor.apply()
}

@Composable
fun ViewSleepDataScreen(context: Context) {
    // Fetch sleep entries from SharedPreferences and filter them to include only the entries from
    // the last two weeks.
    val entries = getSleepEntries(context).filter {
        // Parse the date from the sleep entry.
        val entryDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).parse(it.date)
        // Calculate the date for two weeks ago.
        val twoWeeksAgo = Calendar.getInstance().apply { add(Calendar.DAY_OF_MONTH, -14) }.time
        // Check if the entry date is not null and after the date two weeks ago.
        entryDate != null && entryDate.after(twoWeeksAgo)
    }

    // Start a Column composable to arrange its children vertically.
    Column(modifier = Modifier.padding(16.dp)) {
        // Loop through each filtered sleep entry and display its details.
        entries.forEach { entry ->
            // Display the details of the sleep entry using the Text composable.
            Text(text = "Date: ${entry.date}, Sleep Time: ${entry.sleepTime}, Wake Up Time: ${entry.wakeUpTime}, Duration: ${entry.duration}")
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    // Retrieve the current context from the LocalContext.
    val context = LocalContext.current

    // Start a Box composable that allows for layering of composables on top of each other.
    Box(
        // Set the modifier to fill the maximum available size and apply padding around the box.
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        // Align the content inside the box to the bottom center of the screen.
        contentAlignment = Alignment.BottomCenter
    ) {
        // Start a Column composable to arrange its children (buttons) vertically.
        Column(
            // Center the contents of the column horizontally.
            horizontalAlignment = Alignment.CenterHorizontally,

            // Arrange the column contents with a specified space between them.
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Create a button that navigates to the sleep benefits screen when clicked.
            Button(
                onClick = { navController.navigate("sleep_benefits") },
                // Apply padding to the bottom of the button to push it slightly upwards.
                modifier = Modifier.padding(bottom = 1.dp)
            ) {
                // Text inside the button showing its purpose.
                Text(text = "Learn About Sleep Benefits")
            }

            // Create a button that navigates to the new sleep entry screen when clicked.
            Button(onClick = { navController.navigate("new_sleep_entry") }) {
                // Text inside the button showing its purpose.
                Text("Add New Sleep Entry")
            }

            // Create a button for signing up for the app.
            Button(
                // start a new activity (SignInActivity) when the button is clicked.
                onClick = {
                    context.startActivity(Intent(context, SignInActivity::class.java))
                },
                // Apply padding to the top of the button.
                modifier = Modifier.padding(top = 8.dp)
            ) {
                // Text inside the button showing its purpose.
                Text(text = "Sign Up")
            }
        }
    }
}

fun setUpChart(context: Context, chart: LineChart, entries: List<SleepEntry>) {
    // 'entries' is a sorted list of SleepEntry objects for the last 14 days.
    // Create a list of Entry objects for the last 14 days.
//    val chartEntries = entries.mapIndexed { index, sleepEntry ->
//        Entry(index.toFloat(), sleepEntry.duration.toFloat())
//    }

    // Create a list of Entry objects for the chart
    val chartEntries = entries.mapIndexed { index, sleepEntry ->
        val sleepDuration = if (sleepEntry.duration.isNotBlank()) {
            sleepEntry.duration.toFloat() // Convert duration to a float
        } else {
            4f // Default value
        }
        // Create an Entry object which takes the x value as the index and the y value as the sleep duration
        Entry(index.toFloat(), sleepDuration)
    }

    // Create a LineDataSet from the list of entries
    val dataSet = LineDataSet(chartEntries, "Sleep Duration")
    // Customize the dataset appearance
    dataSet.color = ColorTemplate.getHoloBlue()
    dataSet.valueTextColor = Color.BLACK
    dataSet.lineWidth = 2f
    dataSet.setDrawCircles(true)
    dataSet.setDrawValues(true)

    // Create a LineData object with the dataset
    val lineData = LineData(dataSet)

    // Assign the data to the chart and refresh it
    chart.data = lineData
    chart.invalidate() // Refresh the chart
}

@Preview(showBackground = true)
@Composable
fun SleepTrackerPreview() {
    SleepTrackerAppTheme {
        BackgroundImage(modifier = Modifier.fillMaxSize())
//        InitialScreenText(
//            welcome = "Welcome to Better Sleep",
//            ready = "Are you ready to better your sleep?",
//            modifier = Modifier
//        )

        // Previewing HomeScreen with a fake NavController for illustration
//         HomeScreen(navController = rememberNavController())

        // Preview the SleepBenefitsScreen for a change
//         SleepBenefitsScreen(navController = rememberNavController())

        // Preview the SignUpScreen for a change
//         SignUpScreen(navController = rememberNavController())

        // Preview the NewSleepEntryScreen for a change
//        NewSleepEntryScreen(navController = rememberNavController())

        // Preview the ViewSleepDataScreen for a change
//        ViewSleepDataScreen(context = LocalContext.current)
    }
}
