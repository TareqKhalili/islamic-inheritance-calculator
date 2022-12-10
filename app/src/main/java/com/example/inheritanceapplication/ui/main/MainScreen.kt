package com.example.inheritanceapplication.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inheritanceapplication.Genders
import com.example.inheritanceapplication.MainUiModel
import com.example.inheritanceapplication.PropertyDetails
import com.example.inheritanceapplication.PropertyType
import com.example.inheritanceapplication.R
import com.example.inheritanceapplication.ui.navigation.Screen
import com.example.inheritanceapplication.ui.theme.LightGray
import com.example.inheritanceapplication.ui.theme.LightGreen
import com.example.inheritanceapplication.ui.theme.PrimaryGreen
import com.example.inheritanceapplication.ui.theme.SecondaryGray
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.Locale


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    mainUiModel: MainUiModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = RoundedCornerShape(10.dp),
        sheetContent = {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Property Type",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(10.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = SecondaryGray
                    )
                    Divider(modifier = Modifier.align(Alignment.BottomCenter))
                }
                PropertyOption(
                    title = "Cash",
                    icon = painterResource(id = R.drawable.baseline_monetization_on_24),
                    onSelect = {
                        mainUiModel.addProperty(PropertyType.CASH)
                        scope.launch {
                            state.hide()
                        }
                    }
                )
                PropertyOption(
                    title = "Car",
                    icon = painterResource(id = R.drawable.baseline_car_24),
                    onSelect = {
                        mainUiModel.addProperty(PropertyType.CAR)
                        scope.launch {
                            state.hide()
                        }
                    }
                )
                PropertyOption(
                    title = "Land",
                    icon = painterResource(id = R.drawable.baseline_forest_24),
                    onSelect = {
                        mainUiModel.addProperty(PropertyType.LAND)
                        scope.launch {
                            state.hide()
                        }
                    }
                )
                PropertyOption(
                    title = "Company",
                    icon = painterResource(id = R.drawable.baseline_business_24),
                    onSelect = {
                        mainUiModel.addProperty(PropertyType.COMPANY)
                        scope.launch {
                            state.hide()
                        }
                    }
                )
                PropertyOption(
                    title = "Gold",
                    icon = painterResource(id = R.drawable.baseline_gold_24),
                    onSelect = {
                        mainUiModel.addProperty(PropertyType.GOLD)
                        scope.launch {
                            state.hide()
                        }
                    }
                )
            }
        }
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }) {
            Column {
                TopBar(onClick = {
                    scope.launch {
                        state.show()
                        focusManager.clearFocus()
                    }
                })
                GenderSelector(mainUiModel.gender, mainUiModel::setGenderType)
                ContentSpacer(
                    title = "Amount In Currency",
                    modifier = Modifier.padding(top = 15.dp)
                )
                PropertiesContainer(mainUiModel.properties, onDelete = {
                    mainUiModel.remove(it)
                })
            }


            ExtendedFloatingActionButton(
                onClick = {
                    navController.navigate(Screen.InheritorsScreen.route)
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(15.dp),
                text = {
                    Text(text = "Continue to Inheritors", color = Color.White)
                },
                icon = {
                    Image(
                        imageVector = Icons.Default.KeyboardArrowRight,
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

@Composable
fun ContentSpacer(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Divider(modifier = Modifier.align(Alignment.Center))
        Surface(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp),
            color = LightGray
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                letterSpacing = 2.sp,
                color = SecondaryGray
            )
        }
    }
}

@Preview
@Composable
fun PreviewContentSpacer() {
    ContentSpacer(title = "Amount In Currency")
}

@Composable
fun PropertyOption(
    title: String,
    icon: Painter,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(modifier = modifier
        .fillMaxWidth()
        .clickable { onSelect() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null, tint = PrimaryGreen,
            )

            Text(
                text = title,
                fontWeight = FontWeight.Medium,
                color = PrimaryGreen,
                fontSize = 18.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewPropertyOption() {
    PropertyOption(
        title = "Car",
        icon = painterResource(id = R.drawable.baseline_car_24),
        onSelect = {}
    )
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewMainScreen() {
    MainScreen(
        mainUiModel = FakeData.uiModel,
        navController = NavController(LocalContext.current)
    )
}

@Composable
fun TopBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Column {
                Text("Inheritance Calculator")
            }
        },
        actions = {
            IconButton(onClick = onClick) {
                Icon(Icons.Filled.AddCircle, null)
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
    TopBar(onClick = {})
}

@Composable
fun GenderSelector(
    genders: MutableState<Genders>,
    changeGenderAction: (Genders) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 15.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Genders.values().forEach { gender ->
            ActionRadioButton(
                title = gender,
                selectedOption = genders.value,
                icon = if (gender == Genders.MALE)
                    painterResource(id = R.drawable.baseline_male_24)
                else
                    painterResource(id = R.drawable.baseline_female_24),
                onClick = { changeGenderAction(gender) },
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview
@Composable
fun PreviewGenderSelector() {
    GenderSelector(FakeData.uiModel.gender, {})
}

@Composable
fun ActionRadioButton(
    title: Genders,
    selectedOption: Genders,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = title == selectedOption

    Surface(
        modifier = modifier.clickable { onClick() },
        color = if (isSelected) {
            PrimaryGreen
        } else {
            LightGreen
        },
        shape = RoundedCornerShape(5.dp),
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = Color.White,
                    unselectedColor = Color.White,
                )
            )

            Text(
                text = title.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )

            Image(
                painter = icon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun PropertiesContainer(
    properties: SnapshotStateList<PropertyDetails>,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 10.dp)
    ) {
        properties.forEachIndexed { index, propertyDetails ->
            PropertyDetails(index, propertyDetails, onDelete = onDelete)
        }
    }
}

@Preview
@Composable
fun PreviewPropertiesContainer() {
    PropertiesContainer(FakeData.uiModel.properties, {})
}

@Composable
fun PropertyDetails(
    index: Int,
    details: PropertyDetails,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = details.getIcon()),
            contentDescription = null,
            tint = PrimaryGreen,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .size(35.dp)
        )

        Text(
            text = details.type.name
                .lowercase(Locale.ROOT)
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = PrimaryGreen,
            modifier = Modifier.weight(1f)
        )


        if (details.canHaveCashAmount()) {
            OutlinedTextField(
                value = details.amountInCurrency.value.toString().run {
                    if (this == "0") {
                        ""
                    } else {
                        this
                    }
                },
                singleLine = true,
                onValueChange = { newString: String ->
                    details.amountInCurrency.value = newString
                        .replace(Regex("[^0-9]"), "")
                        .run {
                            when (this.count {
                                it == '.'
                            }) {
                                0, 1 -> this.take(12).toLongOrDefault(0)
                                else -> details.amountInCurrency.value
                            }
                        }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
                    .height(50.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = SecondaryGray,
                    cursorColor = PrimaryGreen,
                    focusedIndicatorColor = PrimaryGreen,
                    unfocusedIndicatorColor = Color.LightGray,
                    backgroundColor = LightGreen
                ),
                placeholder = {
                    Text(text = "0", color = Color.LightGray)
                }
            )
        }

        IconButton(onClick = { onDelete(index) }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        }
    }
}

private fun String.toLongOrDefault(default: Long): Long {
    return try {
        toLong()
    } catch (exception: Exception) {
        default
    }
}

@Preview
@Composable
fun PreviewProperty() {
    PropertyDetails(index = 0, details = FakeData.uiModel.properties[0], onDelete = {})
}

object FakeData {
    val uiModel = MainUiModel().apply {
        properties.add(
            PropertyDetails(
                type = PropertyType.CASH,
                amountInCurrency = mutableStateOf(0L)
            )
        )
    }
}