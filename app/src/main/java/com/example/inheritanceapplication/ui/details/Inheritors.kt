package com.example.inheritanceapplication.ui.details

import Main
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inheritanceapplication.InheritorDetails
import com.example.inheritanceapplication.MainUiModel
import com.example.inheritanceapplication.R
import com.example.inheritanceapplication.RelationKey
import com.example.inheritanceapplication.ui.main.ContentSpacer
import com.example.inheritanceapplication.ui.theme.LightGray
import com.example.inheritanceapplication.ui.theme.PrimaryGreen
import com.example.inheritanceapplication.ui.theme.SecondaryGray
import java.util.Locale

@Composable
fun InheritorsScreen(
    mainUiModel: MainUiModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Surface(color = LightGray) {
        Box(modifier = modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                TopBar(
                    navigateUp = { navController.navigateUp() }
                )

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 60.dp)
                ) {
                    ContentSpacer(
                        title = "Inheritors Numbers",
                        modifier = Modifier.padding(top = 15.dp)
                    )

                    mainUiModel.inheritors.forEach { pair ->
                        InheritorsDetails(pair)
                    }
                }

            }

            ExtendedFloatingActionButton(
                onClick = {
                    val proyarities = Main.PROYARITY.values()
                    val Inheritor = IntArray(proyarities.size)
                    mainUiModel.inheritors.forEach {
                        it.values.forEach {
                            it.forEach {
                                Inheritor[it.getPositionInCalculationsEnum(proyarities)] = it.amount.value
                            }
                        }
                    }

                    val dataProcessor = Main()
                    dataProcessor.provideData(
                        Inheritor = Inheritor,
                        totalWealth = mainUiModel.totalAmount().toDouble(),
                        totalShare = 0.0
                    )

                    dataProcessor.process_data()
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.dp),
                text = {
                    Text(text = "Calculate Inheritance", color = Color.White)
                },
                icon = {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_calculate_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            Color.White
                        )
                    )
                },
                backgroundColor = PrimaryGreen,
                elevation = FloatingActionButtonDefaults.elevation(0.dp)
            )

        }
    }
}

@Preview
@Composable
fun PreviewInheritorsScreen() {
    InheritorsScreen(MainUiModel(), NavController(LocalContext.current))
}

@Composable
fun TopBar(
    navigateUp: () -> Boolean,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column {
                Text("Inheritors Details")
            }
        },
        navigationIcon = {
            IconButton(onClick = { navigateUp() }) {
                Image(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(
                        Color.White
                    )
                )
            }
        },
        backgroundColor = PrimaryGreen,
        contentColor = Color.White,
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar({ true })
}

@Composable
fun InheritorsDetails(
    inheritors: Map<RelationKey, List<InheritorDetails>>,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    val isExpandable by remember {
        derivedStateOf {
            inheritors[inheritors.keys.first()]!!.size > 1
        }
    }

    if (inheritors.keys.size == 1) {
        Column(
            modifier = modifier
                .padding(start = 20.dp, end = 5.dp, top = 5.dp, bottom = 5.dp)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = inheritors.keys.first().name
                        .lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                        .replace("_", " ")
                        .let {
                            if (!isExpandable) {
                                it.replace("side", "")
                            } else {
                                it
                            }
                        },
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = isExpandable) {
                            isExpanded = !isExpanded
                        },
                    fontSize = 18.sp,
                    color = PrimaryGreen,
                    fontWeight = FontWeight.Bold,
                )

                if (!isExpandable) {
                    Counter(inheritors[inheritors.keys.first()]!!.first().amount)
                } else {
                    IconButton(onClick = {
                        isExpanded = !isExpanded
                    }) {
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            }


            if (isExpanded) {
                inheritors[inheritors.keys.first()]!!.forEach { inheritorDetails ->
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 5.dp, start = 15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = inheritorDetails.name
                                .lowercase()
                                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
                                .replace("_", " "),
                            modifier = Modifier.weight(1f),
                            fontSize = 18.sp,
                            color = PrimaryGreen,
                            fontWeight = FontWeight.Bold,
                        )
                        Counter(inheritorDetails.amount)
                    }
                }
            }
        }
    }
}

@Composable
fun Counter(amount: MutableState<Int>, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
                if (amount.value > 0) {
                    amount.value--
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_remove_24),
                contentDescription = null,
                tint = PrimaryGreen
            )
        }
        Text(
            text = amount.value.toString(),
            fontSize = 18.sp,
            modifier = modifier.padding(horizontal = 5.dp),
            color = SecondaryGray
        )
        IconButton(onClick = { amount.value++ }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = PrimaryGreen)
        }
    }
}
