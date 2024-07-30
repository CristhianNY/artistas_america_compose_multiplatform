package add_listing.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import auth.presentation.AuthViewModel
import auth.presentation.LoginDialog
import navigation.lading.LandingComponent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
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
        val isSmallScreen = maxWidth < 600.dp

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
                        if (talent.text.isNotEmpty()) landingViewModel.getCategoryRecommendationsAutoCompleted(it.text)
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
                    viewModel = landingViewModel
                )
            } else {
                LargeScreenContent(
                    talent = talent,
                    onTalentChange = {
                        talent = it
                        if (talent.text.isNotEmpty()) landingViewModel.getCategoryRecommendationsAutoCompleted(it.text)
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
                    viewModel = landingViewModel
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
    viewModel: LandingViewModel
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Promotional Image",
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
                viewModel = viewModel
            )
        }
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
    viewModel: LandingViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = "Promotional Image",
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
                viewModel = viewModel
            )
        }
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
    viewModel: LandingViewModel
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
            viewModel = viewModel
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
    viewModel: LandingViewModel
) {
    var showCategorySuggestions by remember { mutableStateOf(false) }
    var filteredCategorySuggestions by remember { mutableStateOf(emptyList<String>()) }

    var showCitySuggestions by remember { mutableStateOf(false) }
    var filteredCitySuggestions by remember { mutableStateOf(emptyList<String>()) }

    val focusRequester = remember { FocusRequester() }

    val uiState by viewModel.uiState.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Get more gigs on GigSalad!",
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
            label = { Text("What's your talent?") },
            placeholder = { Text("Guitarist, Caterer, Santa...") },
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
            label = { Text("Where do you gig?") },
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
        Text(text = "8,500+ leads sent each day")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* handle click */ }) {
            Text(text = "Start Getting Gigs")
        }
    }
}
