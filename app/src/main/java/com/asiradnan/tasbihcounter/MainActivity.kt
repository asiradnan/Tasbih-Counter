package com.asiradnan.tasbihcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Cached
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier, onLeftHandToggle: () -> Unit, leftHandEnabled: Boolean) {
    CenterAlignedTopAppBar(
        title = {
            Text("Tasbih Counter")
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.tasbih),
                    contentDescription = "Moon icon as logo"
                )
            }
        },
        actions = {
            Box() {
                var expanded by remember { mutableStateOf(false) }
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings icon"
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = "Left Hand Mode")

                        },
                        onClick = { /* Do something... */ },
                        trailingIcon = {
                            Switch(
                                checked = leftHandEnabled,
                                onCheckedChange = { onLeftHandToggle() })
                        }
                    )
                }
            }
        },
    )
}

@Preview
@Composable
fun DuaDropDown(modifier: Modifier = Modifier) {
    var itemPosition by rememberSaveable { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val duas = listOf("Subhanallah", "Alhamdulillah", "Astagfirullah")
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f),
    ) {
        OutlinedCard(
            border = ButtonDefaults.outlinedButtonBorder(true).copy(width = 1.5.dp),
            shape = MaterialTheme.shapes.small,
//            elevation = CardDefaults.outlinedCardElevation(1.dp)
        )
        {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .padding(all = 12.dp)
                    .clickable { expanded = !expanded }
                    .fillMaxWidth()
            ) {

                Text(text = duas[itemPosition])
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = modifier.size(32.dp)
                )
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier.fillMaxWidth(0.8f)
        ) {

            duas.forEachIndexed { idx, dua ->
                DropdownMenuItem(
                    text = { Text(text = dua) },
                    onClick = {
                        itemPosition = idx
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun Display(modifier: Modifier = Modifier, count: Int) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(0.8f),
        border = ButtonDefaults.outlinedButtonBorder(true).copy(width = 1.dp),
    ) {
        Text(
            text = count.toString(),
            style = MaterialTheme.typography.displayLarge,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun DecrementButton(modifier: Modifier = Modifier, count: Int, onDecrement: () -> Unit) {
    Button(
        onClick = { onDecrement() },
        modifier = modifier
            .aspectRatio(1f),
        shape = CircleShape,
        enabled = count > 0,
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary)
    ) {
        Icon(
            imageVector = Icons.Filled.Remove,
            contentDescription = null,
            modifier = modifier.fillMaxSize(0.75f)
        )
    }
}

@Composable
fun IncrementButton(modifier: Modifier = Modifier, onIncrement: () -> Unit) {
    Button(
        onClick = { onIncrement() },
        modifier = modifier
            .aspectRatio(1f),
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = null,
            modifier = modifier.fillMaxSize(0.75f)
        )
    }
}

@Composable
fun RightHandButtons(
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
        Spacer(modifier = modifier.weight(0.4f))
        DecrementButton(modifier = modifier.weight(1.5f), count = count, { onDecrement() })
        Spacer(modifier = modifier.weight(0.4f))
        IncrementButton(modifier = modifier.weight(2.75f), onIncrement = { onIncrement() })
        Spacer(modifier = modifier.weight(0.4f))
    }
}

@Composable
fun LeftHandButtons(
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
        Spacer(modifier = modifier.weight(0.4f))
        IncrementButton(modifier = modifier.weight(2.75f), onIncrement = { onIncrement() })
        Spacer(modifier = modifier.weight(0.4f))
        DecrementButton(modifier = modifier.weight(1.5f), count = count, { onDecrement() })
        Spacer(modifier = modifier.weight(0.4f))
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
            contentDescription = null,
            Modifier.size(30.dp)
        )
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
    var leftHandEnabled by rememberSaveable { mutableStateOf(false) }
    TasbihCounterTheme {
        Scaffold(
            topBar = {
                TopBar(
                    Modifier,
                    onLeftHandToggle = { leftHandEnabled = !leftHandEnabled },
                    leftHandEnabled = leftHandEnabled
                )
            }
        ) { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(innerPadding)
//            verticalArrangement = Arrangement.SpaceAround
            ) {
                Spacer(modifier = Modifier.weight(0.04f))
                DuaDropDown()
                Spacer(modifier = Modifier.weight(0.025f))
                Display(modifier = Modifier, count)
                Spacer(modifier = Modifier.weight(0.25f))
                if (leftHandEnabled)
                    LeftHandButtons(
                        modifier = Modifier,
                        count,
                        onIncrement = { count++ },
                        onDecrement = { count-- }
                    )
                else
                    RightHandButtons(
                        modifier = Modifier,
                        count,
                        onIncrement = { count++ },
                        onDecrement = { count-- }
                    )
                Spacer(modifier = Modifier.weight(0.06f))
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

