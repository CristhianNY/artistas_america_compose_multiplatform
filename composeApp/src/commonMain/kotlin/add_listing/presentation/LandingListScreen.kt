package add_listing.presentation

import LoginDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import auth.presentation.AuthViewModel
import navigation.lading.LandingComponent
import navigation.lading.LandingEvent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import support.HoverableButton
import support.TopNavigationBar

@Composable
fun LandingListScreen(component: LandingComponent) {
    val authViewModel: AuthViewModel = koinInject()
    val landingViewModel: LandingViewModel = koinInject()
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    var showLoginDialog by remember { mutableStateOf(false) }
    var talent by remember { mutableStateOf(TextFieldValue("")) }
    var location by remember { mutableStateOf(TextFieldValue("")) }

    BoxWithConstraints {
        val isSmallScreen = maxWidth < 1200.dp

        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            TopNavigationBar(
                isLoggedIn,
                { showLoginDialog = true },
                { authViewModel.logout() },
                component
            )

            if (isSmallScreen) {
                SmallScreenContent(
                    talent = talent,
                    onTalentChange = {
                        talent = it
                        if (talent.text.isNotEmpty()) landingViewModel.getCategoryRecommendationsAutoCompleted(
                            it.text
                        )
                    },
                    onTalentClicked = {
                        talent = TextFieldValue(it, TextRange(it.length))
                    },
                    location = location,
                    onLocationChange = {
                        location = it
                        if (location.text.isNotEmpty()) landingViewModel.getCitiesAutoCompleted(it.text)
                    },
                    onLocationClicked = {
                        location = TextFieldValue(it, TextRange(it.length))
                    },
                    viewModel = landingViewModel,
                    isLoggedIn,
                    authViewModel,
                    component = component
                )
            } else {
                LargeScreenContent(
                    talent = talent,
                    onTalentChange = {
                        talent = it
                        if (talent.text.isNotEmpty()) landingViewModel.getCategoryRecommendationsAutoCompleted(
                            it.text
                        )
                    },
                    onTalentClicked = {
                        talent = TextFieldValue(it, TextRange(it.length))
                    },
                    location = location,
                    onLocationChange = {
                        location = it
                        if (location.text.isNotEmpty()) landingViewModel.getCitiesAutoCompleted(it.text)
                    },
                    onLocationClicked = {
                        location = TextFieldValue(it, TextRange(it.length))
                    },
                    maxWidth = this@BoxWithConstraints.maxWidth,
                    viewModel = landingViewModel,
                    isLoggedIn,
                    authViewModel,
                    component = component
                )
            }
        }
    }

    if (showLoginDialog) {
        LoginDialog(onDismiss = { showLoginDialog = false }, authViewModel, component)
    }
}

@Composable
fun SmallScreenContent(
    talent: TextFieldValue,
    onTalentChange: (TextFieldValue) -> Unit,
    onTalentClicked: (String) -> Unit,
    location: TextFieldValue,
    onLocationChange: (TextFieldValue) -> Unit,
    onLocationClicked: (String) -> Unit,
    viewModel: LandingViewModel,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel,
    component: LandingComponent
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = Strings.PROMOTIONAL_IMAGE_DESC,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            FormCard(
                talent = talent,
                onTalentChange = onTalentChange,
                onTalentClicked = onTalentClicked,
                location = location,
                onLocationChange = onLocationChange,
                onLocationClicked = onLocationClicked,
                viewModel = viewModel,
                isLoggedIn,
                authViewModel = authViewModel,
                component = component
            )
        }

        StepsComposable(isSmallScreen = true)
        PricingTableComposable(isSmallScreen = true)
    }
}

@Composable
fun LargeScreenContent(
    talent: TextFieldValue,
    onTalentChange: (TextFieldValue) -> Unit,
    onTalentClicked: (String) -> Unit,
    location: TextFieldValue,
    onLocationChange: (TextFieldValue) -> Unit,
    onLocationClicked: (String) -> Unit,
    maxWidth: Dp,
    viewModel: LandingViewModel,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel,
    component: LandingComponent
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.compose_multiplatform),
                contentDescription = Strings.PROMOTIONAL_IMAGE_DESC,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .width(0.4f * maxWidth.value.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                FormCard(
                    talent = talent,
                    onTalentChange = onTalentChange,
                    onTalentClicked = onTalentClicked,
                    location = location,
                    onLocationChange = onLocationChange,
                    onLocationClicked = onLocationClicked,
                    viewModel = viewModel,
                    isLoggedIn,
                    authViewModel,
                    component
                )
            }
        }

        StepsComposable(isSmallScreen = false)
        PricingTableComposable(isSmallScreen = false)
    }
}

@Composable
fun FormCard(
    talent: TextFieldValue,
    onTalentChange: (TextFieldValue) -> Unit,
    onTalentClicked: (String) -> Unit,
    location: TextFieldValue,
    onLocationChange: (TextFieldValue) -> Unit,
    onLocationClicked: (String) -> Unit,
    viewModel: LandingViewModel,
    isLoggedIn: Boolean,
    authViewModel: AuthViewModel,
    component: LandingComponent
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        FormContent(
            talent = talent,
            onTalentChange = onTalentChange,
            onTalentClicked = onTalentClicked,
            location = location,
            onLocationChange = onLocationChange,
            onLocationClicked = onLocationClicked,
            viewModel = viewModel,
            isLoggedIn,
            authViewModel,
            component
        )
    }
}

@Composable
fun FormContent(
    talent: TextFieldValue,
    onTalentChange: (TextFieldValue) -> Unit,
    onTalentClicked: (String) -> Unit,
    location: TextFieldValue,
    onLocationChange: (TextFieldValue) -> Unit,
    onLocationClicked: (String) -> Unit,
    viewModel: LandingViewModel,
    isloggedIn: Boolean,
    authViewModel: AuthViewModel,
    component: LandingComponent
) {
    var showCategorySuggestions by remember { mutableStateOf(false) }
    var filteredCategorySuggestions by remember { mutableStateOf(emptyList<String>()) }

    var showCitySuggestions by remember { mutableStateOf(false) }
    var filteredCitySuggestions by remember { mutableStateOf(emptyList<String>()) }

    val focusRequester = remember { FocusRequester() }

    val uiState by viewModel.uiState.collectAsState()
    var showLoginDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = Strings.GET_MORE_GIGS,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = talent,
            onValueChange = {
                onTalentChange(it)
                filteredCategorySuggestions = when (val state = uiState) {
                    is LandingState.SuggestionCategory -> state.suggestions?.filter { suggestion ->
                        suggestion.contains(it.text, ignoreCase = true)
                    }!!

                    else -> emptyList()
                }
                showCategorySuggestions = filteredCategorySuggestions.isNotEmpty()
            },
            label = { Text(Strings.TALENT_LABEL) },
            placeholder = { Text(Strings.TALENT_PLACEHOLDER) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (showCategorySuggestions) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            ) {
                items(filteredCategorySuggestions.size) { index ->
                    val suggestion = filteredCategorySuggestions[index]
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onTalentClicked(suggestion)
                                showCategorySuggestions = false
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
        OutlinedTextField(
            value = location,
            onValueChange = {
                onLocationChange(it)
                filteredCitySuggestions = when (val state = uiState) {
                    is LandingState.SuggestionCity -> state.citySuggestions?.filter { suggestion ->
                        suggestion.contains(it.text, ignoreCase = true)
                    }!!

                    else -> emptyList()
                }
                showCitySuggestions = filteredCitySuggestions.isNotEmpty()
            },
            label = { Text(Strings.LOCATION_LABEL) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        showCategorySuggestions = false
                    }
                }
        )
        if (showCitySuggestions) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            ) {
                items(filteredCitySuggestions.size) { index ->
                    val suggestion = filteredCitySuggestions[index]
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onLocationClicked(suggestion)
                                showCitySuggestions = false
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = Strings.LEADS_SENT_EACH_DAY)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (!isloggedIn) {
                showLoginDialog = true
            }else{
                component.onEvent(LandingEvent.GoToDashboard)
            }
        }) {
            Text(text = Strings.START_GETTING_GIGS)
        }

        if (showLoginDialog) {
            LoginDialog(onDismiss = { showLoginDialog = false }, authViewModel, component)
        }
    }
}

@Composable
fun StepsComposable(isSmallScreen: Boolean) {
    if (isSmallScreen) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth().padding()
            ) {
                StepItem(
                    number = "1",
                    title = Strings.STEP_1_TITLE,
                    description = Strings.STEP_1_DESC
                )
                StepItem(
                    number = "2",
                    title = Strings.STEP_2_TITLE,
                    description = Strings.STEP_2_DESC
                )
                StepItem(
                    number = "3",
                    title = Strings.STEP_3_TITLE,
                    description = Strings.STEP_3_DESC
                )
                StepItem(
                    number = "4",
                    title = Strings.STEP_4_TITLE,
                    description = Strings.STEP_4_DESC
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp, vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(
                        listOf(
                            StepData(
                                "1",
                                Strings.STEP_1_TITLE,
                                Strings.STEP_1_DESC
                            ),
                            StepData(
                                "2",
                                Strings.STEP_2_TITLE,
                                Strings.STEP_2_DESC
                            ),
                            StepData(
                                "3",
                                Strings.STEP_3_TITLE,
                                Strings.STEP_3_DESC
                            ),
                            StepData(
                                "4",
                                Strings.STEP_4_TITLE,
                                Strings.STEP_4_DESC
                            )
                        )
                    ) { step ->
                        StepItem(
                            number = step.number,
                            title = step.title,
                            description = step.description
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StepItem(number: String, title: String, description: String) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .width(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)  // Ajusta el tamaño para asegurar que sea un círculo perfecto
                        .background(Color(0xFFCCE4FF), shape = CircleShape)
                ) {
                    Text(
                        text = number,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2590FF),
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 48.dp)  // Alinea la descripción con el título
            )
        }
    }
}

@Composable
fun PricingTableComposable(isSmallScreen: Boolean) {
    if (isSmallScreen) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            PricingCard(
                title = Strings.PLAN_FREE,
                price = Strings.PLAN_FREE_PRICE,
                description = Strings.PLAN_FREE_DESC,
                features = Strings.PLAN_FREE_FEATURES.split("\n"),
                buttonText = Strings.CHOOSE_THIS_PLAN
            )
            Spacer(modifier = Modifier.height(16.dp))
            PricingCard(
                title = Strings.PLAN_PRO,
                price = Strings.PLAN_PRO_PRICE,
                description = Strings.PLAN_PRO_DESC,
                features = Strings.PLAN_PRO_FEATURES.split("\n"),
                buttonText = Strings.CHOOSE_THIS_PLAN
            )
            Spacer(modifier = Modifier.height(16.dp))
            PricingCard(
                title = Strings.PLAN_FEATURED,
                price = Strings.PLAN_FEATURED_PRICE,
                description = Strings.PLAN_FEATURED_DESC,
                features = Strings.PLAN_FEATURED_FEATURES.split("\n"),
                buttonText = Strings.CHOOSE_THIS_PLAN,
                isButtonHoveredInitially = true
            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth().background(Color(0xFFF6F9FA))
                .padding(horizontal = 250.dp, vertical = 40.dp),

            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            PricingCard(
                title = Strings.PLAN_FREE,
                price = Strings.PLAN_FREE_PRICE,
                description = Strings.PLAN_FREE_DESC,
                features = Strings.PLAN_FREE_FEATURES.split("\n"),
                buttonText = Strings.CHOOSE_THIS_PLAN,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            PricingCard(
                title = Strings.PLAN_PRO,
                price = Strings.PLAN_PRO_PRICE,
                description = Strings.PLAN_PRO_DESC,
                features = Strings.PLAN_PRO_FEATURES.split("\n"),
                buttonText = Strings.CHOOSE_THIS_PLAN,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            PricingCard(
                title = Strings.PLAN_FEATURED,
                price = Strings.PLAN_FEATURED_PRICE,
                description = Strings.PLAN_FEATURED_DESC,
                features = Strings.PLAN_FEATURED_FEATURES.split("\n"),
                buttonText = Strings.CHOOSE_THIS_PLAN,
                modifier = Modifier.weight(1f),
                isButtonHoveredInitially = true
            )
        }
    }
}

@Composable
fun PricingCard(
    title: String,
    price: String,
    description: String,
    features: List<String>,
    buttonText: String,
    modifier: Modifier = Modifier,
    isButtonHoveredInitially: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 8.dp, // Añade sombra a la tarjeta
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Centra los elementos en el eje horizontal
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centra el título horizontalmente
            )
            Text(
                text = price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally) // Centra el precio horizontalmente
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center, // Centra el texto de la descripción
                modifier = Modifier.fillMaxWidth() // Ocupa todo el ancho disponible
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                features.forEachIndexed { index, feature ->
                    Box(modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(
                            text = if (index == 0) feature else "• $feature",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = if (index == 0) FontWeight.Bold else FontWeight.Normal // El primer texto en negrita
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            HoverableButton(
                buttonText = buttonText,
                onClick = { /* handle click */ },
                modifier = Modifier,
                isHoveredInitially = isButtonHoveredInitially
            )
        }
    }
}

data class StepData(val number: String, val title: String, val description: String)
