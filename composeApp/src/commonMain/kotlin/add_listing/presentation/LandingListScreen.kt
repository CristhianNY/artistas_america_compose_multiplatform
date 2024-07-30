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
    var location by remember { mutableStateOf("") }

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
                        if (talent.text.isNotEmpty()) landingViewModel.getCategoryRecommendationsAutoCompleted(
                            it.text
                        )

                    },
                    onTalentClicked = {
                        talent = TextFieldValue(it, TextRange(it.length))
                    },
                    location = location,
                    onLocationChange = { location = it },
                    viewModel = landingViewModel
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
                    location = location,
                    onLocationChange = { location = it },
                    maxWidth = this@BoxWithConstraints.maxWidth,
                    viewModel = landingViewModel,
                    onTalentClicked = {
                        talent = TextFieldValue(it, TextRange(it.length))
                    }
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
    location: String,
    onLocationChange: (String) -> Unit,
    viewModel: LandingViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

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
            when (uiState) {
                is LandingState.SuggestionCategory -> (uiState as LandingState.SuggestionCategory).suggestions
                else -> emptyList()
            }?.let {
                FormCard(
                    talent = talent,
                    onTalentChange = onTalentChange,
                    onTalentClicked = onTalentClicked,
                    location = location,
                    onLocationChange = onLocationChange,
                    suggestions = it,
                )
            }
        }
    }
}

@Composable
fun LargeScreenContent(
    talent: TextFieldValue,
    onTalentChange: (TextFieldValue) -> Unit,
    onTalentClicked: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    maxWidth: Dp,
    viewModel: LandingViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

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
            when (uiState) {
                is LandingState.SuggestionCategory -> (uiState as LandingState.SuggestionCategory).suggestions
                else -> emptyList()
            }?.let {
                FormCard(
                    talent = talent,
                    onTalentChange = onTalentChange,
                    location = location,
                    onLocationChange = onLocationChange,
                    suggestions = it,
                    onTalentClicked = onTalentClicked
                )
            }
        }
    }
}

@Composable
fun FormCard(
    talent: TextFieldValue,
    onTalentChange: (TextFieldValue) -> Unit,
    onTalentClicked: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    suggestions: List<String>
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
            suggestions = suggestions
        )
    }
}

@Composable
fun FormContent(
    talent: TextFieldValue,
    onTalentChange: (TextFieldValue) -> Unit,
    onTalentClicked: (String) -> Unit,
    location: String,
    onLocationChange: (String) -> Unit,
    suggestions: List<String>
) {
    var showSuggestions by remember { mutableStateOf(false) }
    var filteredSuggestions by remember { mutableStateOf(suggestions) }
    val focusRequester = remember { FocusRequester() }

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
                filteredSuggestions = suggestions.filter { suggestion ->
                    suggestion.contains(it.text, ignoreCase = true)
                }
                showSuggestions = filteredSuggestions.isNotEmpty()
            },
            label = { Text("What's your talent?") },
            placeholder = { Text("Guitarist, Caterer, Santa...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        if (showSuggestions) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            ) {
                items(filteredSuggestions.size) { index ->
                    val suggestion = filteredSuggestions[index]
                    Text(
                        text = suggestion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onTalentClicked(suggestion)
                                showSuggestions = false
                            }
                            .padding(16.dp)
                    )
                }
            }
        }
        OutlinedTextField(
            value = location,
            onValueChange = onLocationChange,
            label = { Text("Where do you gig?") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused) {
                        showSuggestions = false
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "8,500+ leads sent each day")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* handle click */ }) {
            Text(text = "Start Getting Gigs")
        }
    }
}
