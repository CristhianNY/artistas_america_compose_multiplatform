import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
    var showLoginDialog by remember { mutableStateOf(false) }
    var talent by remember { mutableStateOf("") }
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
                    onTalentChange = { talent = it },
                    location = location,
                    onLocationChange = { location = it }
                )
            } else {
                LargeScreenContent(
                    talent = talent,
                    onTalentChange = { talent = it },
                    location = location,
                    onLocationChange = { location = it },
                    maxWidth = this@BoxWithConstraints.maxWidth
                )
            }
        }
    }

    if (showLoginDialog) {
        LoginDialog(onDismiss = { showLoginDialog = false }, authViewModel, component)
    }
}

@Composable
fun SmallScreenContent(talent: String, onTalentChange: (String) -> Unit, location: String, onLocationChange: (String) -> Unit) {
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
                location = location,
                onLocationChange = onLocationChange
            )
        }
    }
}

@Composable
fun LargeScreenContent(talent: String, onTalentChange: (String) -> Unit, location: String, onLocationChange: (String) -> Unit, maxWidth: Dp) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp) // Adjust the height as needed
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
            contentAlignment = Alignment.Center // Center the form vertically
        ) {
            FormCard(
                talent = talent,
                onTalentChange = onTalentChange,
                location = location,
                onLocationChange = onLocationChange
            )
        }
    }
}

@Composable
fun FormCard(talent: String, onTalentChange: (String) -> Unit, location: String, onLocationChange: (String) -> Unit) {
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
            location = location,
            onLocationChange = onLocationChange
        )
    }
}

@Composable
fun FormContent(talent: String, onTalentChange: (String) -> Unit, location: String, onLocationChange: (String) -> Unit) {
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
            onValueChange = onTalentChange,
            label = { Text("What's your talent?") },
            placeholder = { Text("Guitarist, Caterer, Santa...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        OutlinedTextField(
            value = location,
            onValueChange = onLocationChange,
            label = { Text("Where do you gig?") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "8,500+ leads sent each day")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* handle click */ }) {
            Text(text = "Start Getting Gigs")
        }
    }
}
