package com.orlandev.lazygridstaggered

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.orlandev.lazygridstaggered.ui.theme.LazyGridStaggeredTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyGridStaggeredTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val columnWidth = remember {

                        mutableStateOf((50..220).random())
                    }

                    Column {
                        Button(onClick = {
                            columnWidth.value = (50..220).random()
                        }, modifier = Modifier.padding(6.dp)) {
                            Text(
                                text = "Generate",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                        LazyColumn {
                            item {
                                StaggeredVerticalGrid(
                                    maxColumnWidth = columnWidth.value.dp,
                                    modifier = Modifier.padding(4.dp)
                                ) {
                                    (1..100).forEach {
                                        Card(it.toString(), columnWidth)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Card(
    id: String,
    state: MutableState<Int>,
    modifier: Modifier = Modifier
) {
    val color = remember(state.value) {
        mutableStateOf(Color((0xFF000000..0xFFFFFFFF).random()))
    }

    Surface(
        modifier = modifier
            .padding(4.dp)
            .clickable(
                onClick = { },

                ),
        color = color.value,
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .height((50..200).random().dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = id,
                textAlign = TextAlign.Center,
                color = color.value.rippleColor(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun Color.whiteness(): Float {
    return (red + green + blue) / 3
}

private fun Color.rippleColor(): Color {
    return if (whiteness() >= 0.5) Color.Black else Color.White
}