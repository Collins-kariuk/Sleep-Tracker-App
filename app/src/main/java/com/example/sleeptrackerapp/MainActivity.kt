package com.example.sleeptrackerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        contentScale = ContentScale.Crop,
        alpha = 0.7f
    )
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
    }
}
