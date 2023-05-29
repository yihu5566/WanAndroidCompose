package com.ldf.wanandroidcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ldf.wanandroidcompose.ui.theme.WanAndroidComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WanAndroidComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(listOf("index", "index"))
                }
            }

        }
    }
}

@Composable
fun Greeting(listMsg: List<String>) {
    LazyColumn {
        items(listMsg.size) { index ->
            buildItem(index)
        }
    }


}

@Composable
fun buildItem(name: Int) {
    Column {
        Box(
            Modifier
                .height(20.dp)
                .background(Color(R.color.purple_200))
        )
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "con",
                Modifier
                    .size(Dp(17f), Dp(17f))
                    .clip(CircleShape)
                    .background(Color(R.color.teal_200), shape = CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape),
            )
            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(
                    text = "Hello $name!",
                )
                Text(
                    text = "Hello $name!",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WanAndroidComposeTheme {
        Greeting(listOf("index", "index"))
    }
}