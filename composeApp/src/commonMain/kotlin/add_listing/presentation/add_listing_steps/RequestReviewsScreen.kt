package add_listing.presentation.add_listing_steps

import add_listing.presentation.LandingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import navigation.add_listing.AddRequestReviewComponent
import navigation.add_listing.AddRequestReviewEvent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import support.isValidEmail

@Composable
fun RequestReviewsScreen(component: AddRequestReviewComponent) {
    val landingViewModel: LandingViewModel = koinInject()

    // Lista que contiene pares de nombre y correo electrónico
    val clientReviews = remember { mutableStateListOf<Pair<String, String>>() }

    // Variables para el nuevo par que se desea agregar
    var clientName by remember { mutableStateOf("") }
    var clientEmail by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title as in the design */ },
                navigationIcon = {
                    IconButton(onClick = { component.onEvent(AddRequestReviewEvent.GoBack) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                backgroundColor = Color.White,
                elevation = 0.dp
            )
        },
        content = {
            BoxWithConstraints {
                val isSmallScreen = maxWidth < 1200.dp
                val horizontalPadding = if (isSmallScreen) 0.dp else 400.dp

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                        .imePadding(), // Asegura que el teclado no oculte el contenido
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(Res.drawable.compose_multiplatform),
                        contentDescription = Strings.PROMOTIONAL_IMAGE_DESC,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = "Awesome! Let’s request reviews for your profile.",
                        style = MaterialTheme.typography.h6,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Reviews are extremely important to your success. Get a head start by requesting feedback from a few clients. You can always request more later!",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    // Mostrar los campos actuales
                    clientReviews.forEachIndexed { index, (name, email) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { newName -> clientReviews[index] = newName to email },
                                label = { Text("Client name") },
                                modifier = Modifier.weight(1f)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                OutlinedTextField(
                                    value = email,
                                    onValueChange = { newEmail -> clientReviews[index] = name to newEmail },
                                    label = { Text("Client email address") },
                                    isError = !isValidEmail(email),
                                )
                                if (!isValidEmail(email)) {
                                    Text(
                                        text = "Invalid email address",
                                        color = Color.Red,
                                        style = MaterialTheme.typography.body2,
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Campos para agregar un nuevo cliente
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = clientName,
                            onValueChange = { clientName = it },
                            label = { Text("Client name") },
                            modifier = Modifier.weight(1f)
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = clientEmail,
                                onValueChange = {
                                    clientEmail = it
                                    emailError = !isValidEmail(it)
                                },
                                label = { Text("Client email address") },
                                isError = emailError,
                            )
                            if (emailError) {
                                Text(
                                    text = "Invalid email address",
                                    color = Color.Red,
                                    style = MaterialTheme.typography.body2,
                                    modifier = Modifier.padding(start = 16.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = {
                            if (clientName.isNotBlank() && clientEmail.isNotBlank() && !emailError) {
                                clientReviews.add(clientName to clientEmail)
                                landingViewModel.addClientReviewRequest(clientName, clientEmail)
                                clientName = ""
                                clientEmail = ""
                                emailError = false
                            }
                        },
                        enabled = clientName.isNotBlank() && clientEmail.isNotBlank() && !emailError
                    ) {
                        Text(text = "+ Request another review")
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { component.onEvent(AddRequestReviewEvent.GoToAllDoneScreen) },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding)
                    ) {
                        Text(text = "Continue")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = { component.onEvent(AddRequestReviewEvent.GoToAllDoneScreen) } // Asume un evento para omitir el paso
                    ) {
                        Text(text = "Skip this step")
                    }
                }
            }
        }
    )
}
