package add_listing.presentation.add_listing_steps

import Strings
import add_listing.presentation.LandingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import navigation.add_listing.AddAddressComponent
import navigation.add_listing.AddAddressEvent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun AddressScreen(component: AddAddressComponent) {

    val landingViewModel: LandingViewModel = koinInject()
    val addressSuggestions by landingViewModel.addressSuggestions.collectAsState()
    val formState by landingViewModel.formState.collectAsState()
    var address by remember { mutableStateOf(formState.address ?: "") }
    var isAddressSelected by remember { mutableStateOf(formState.address != null) }
    var showSuggestions by remember { mutableStateOf(true) }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title as en el diseño */ },
                navigationIcon = {
                    IconButton(onClick = { component.onEvent(AddAddressEvent.GoBack) }) {
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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        Image(
                            painter = painterResource(Res.drawable.compose_multiplatform),
                            contentDescription = Strings.PROMOTIONAL_IMAGE_DESC,
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp),
                            contentScale = ContentScale.Crop
                        )
                    }

                    item {
                        Text(
                            text = "“Mariachi” – sounds good!",
                            style = MaterialTheme.typography.h6,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Alright, where can we find you gigs?",
                            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = horizontalPadding)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "This location determines where you appear in search results and will determine your distance from the client’s event. Want to serve more than one area? Don’t worry, you can add and change locations later.",
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = horizontalPadding)
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))

                        OutlinedTextField(
                            value = address,
                            onValueChange = {
                                address = it
                                showSuggestions = true // Mostrar sugerencias mientras se escribe
                                isAddressSelected =
                                    false // No permitir continuar hasta que se seleccione una dirección
                                landingViewModel.getAddressSuggestions(it)
                            },
                            label = { Text("Address or postal code") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding)
                        )
                    }

                    if (showSuggestions) {
                        items(addressSuggestions) { suggestion ->
                            Text(
                                text = suggestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = horizontalPadding)
                                    .clickable {
                                        address = suggestion
                                        isAddressSelected = true
                                        showSuggestions = false // Ocultar sugerencias
                                        landingViewModel.updateAddress(suggestion)
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Button(
                            onClick = { component.onEvent(AddAddressEvent.GoToUploadImagesScreen) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = horizontalPadding),
                            enabled = isAddressSelected // Habilitar solo si se seleccionó una dirección
                        ) {
                            Text(text = "Continue")
                        }
                    }
                }
            }
        }
    )
}


