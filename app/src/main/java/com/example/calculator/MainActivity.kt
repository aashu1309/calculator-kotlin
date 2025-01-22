package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.objecthunter.exp4j.ExpressionBuilder

fun evaluateExpression(expression: String): Double {
    return ExpressionBuilder(expression).build().evaluate()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculator()
        }
    }
}

@Composable
fun Calculator() {
    Scaffold(
        containerColor = Color.LightGray
    ) { innerPadding -> MainContent(innerPadding) }
}

@Composable
fun MainContent(innerPadding: PaddingValues) {
    var input by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(300.dp),
                enabled = true,
                textStyle = TextStyle(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray,
                    focusedContainerColor = Color.LightGray,
                    focusedIndicatorColor = Color.Black
                )
            )
        }

        IconButton(
            onClick = {
                input = input.dropLast(
                    n = 1
                )
            },
            modifier = Modifier
                .padding(end = 8.dp)
                .size(40.dp)
                .align(Alignment.End)
        ) {
            Icon(
                painterResource(R.drawable.backspace),
                contentDescription = "Erase Button",
                modifier = Modifier.size(35.dp)
            )
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val buttons = listOf(
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
            )

            items(buttons.size) { index ->
                Button(
                    onClick = {
                        when (buttons[index]) {
                            "=" -> {
                                input = evaluateExpression(input).toString()
                            }

                            "C" -> {
                                input = ""
                            }

                            else -> {
                                input += buttons[index]
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    colors = ButtonDefaults.buttonColors(Color.White)
                ) {
                    Text(
                        text = buttons[index],
                        color = Color.Black,
                        style = TextStyle(fontSize = 30.sp)
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Calculator()
}