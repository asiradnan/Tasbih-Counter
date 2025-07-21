package com.asiradnan.tasbihcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.asiradnan.tasbihcounter.ui.theme.TasbihCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun Display(modifier: Modifier = Modifier, count: Int) {
    Surface (
        modifier = modifier
            .fillMaxWidth(0.8f),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(16.dp)
        )

    }

}

@Composable
fun Buttons(
    modifier: Modifier = Modifier,
    count: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Spacer(modifier = modifier.weight(0.6f))
        Button(
            onClick = { onDecrement() },
            modifier = modifier
                .weight(2f)
                .aspectRatio(1f),
            shape = CircleShape,
            enabled = count > 0
        ) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = null,
                modifier = modifier.fillMaxSize(0.75f)
            )
        }
        Spacer(modifier = modifier.weight(0.25f))
        Button(
            onClick = { onIncrement() },
            modifier = modifier
                .weight(3f)
                .aspectRatio(1f),
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = null,
                modifier = modifier.fillMaxSize(0.75f)
            )
        }
        Spacer(modifier = modifier.weight(0.6f))
    }
}

@Composable
fun ResetButton(modifier: Modifier = Modifier, onReset: () -> Unit) {
    OutlinedButton(
        onClick = { onReset() },
        modifier = modifier
            .fillMaxWidth(0.9f),
        shape = MaterialTheme.shapes.medium,
        border = ButtonDefaults.outlinedButtonBorder(true).copy(width = 2.dp),
    ) {
        Icon(
            imageVector = Icons.Outlined.Cached,
            contentDescription = null
        )
        Spacer(modifier.width(10.dp))
        Text(
            text = "Reset Counter",
            style = MaterialTheme.typography.titleLarge,
            modifier = modifier.padding(15.dp)
        )
    }
}


@Composable
private fun MainScreen() {
    var count by rememberSaveable { mutableStateOf(0) }
    TasbihCounterTheme {
        Scaffold { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding)
//            verticalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(modifier = Modifier.weight(0.1f))
                Display(modifier = Modifier, count)
                Spacer(modifier = Modifier.weight(0.25f))
                Buttons(
                    modifier = Modifier,
                    count,
                    onIncrement = { count++ },
                    onDecrement = { count-- })
                Spacer(modifier = Modifier.weight(0.05f))
                ResetButton(modifier = Modifier, onReset = { count = 0 })
                Spacer(modifier = Modifier.weight(0.04f))
            }
        }

    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun PreviewScreen() {
    MainScreen()
}

