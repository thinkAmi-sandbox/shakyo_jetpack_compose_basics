package com.example.basiccodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basiccodelab.ui.theme.BasicCodeLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicCodeLabTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = MaterialTheme.colors.primary) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(text = "Hello")
            Text(text = name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BasicCodeLabTheme {
        Greeting("Android")
    }
}

@Composable
private fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        Greeting(name = "Android")
    }
}