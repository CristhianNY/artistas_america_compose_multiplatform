package add_listing.presentation.add_listing_steps

import add_listing.presentation.LandingViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import navigation.add_listing.AddListingComponent
import navigation.add_listing.AddListingEvent
import navigation.add_listing.AddServiceNameComponent
import navigation.add_listing.AddServiceNameEvent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun ServiceNameScreen(component: AddServiceNameComponent) {
    val landingViewModel: LandingViewModel = koinInject()

    val formState by landingViewModel.formState.collectAsState()

    var serviceName by remember { mutableStateOf(formState.serviceName ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title as in the design */ },
                navigationIcon = {
                    IconButton(onClick = { component.onEvent(AddServiceNameEvent.GoBack) }) {
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
                        .verticalScroll(rememberScrollState()),
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
                        text = " ${formState.category} – got it! So...",
                        style = MaterialTheme.typography.h6,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "What name does your act or service go by?",
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Choose something that accurately represents the services you offer and is appealing at a glance – for example, 'The Moondance Band' or 'Elite Catering'.",
                        style = MaterialTheme.typography.body1,
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = serviceName,
                        onValueChange = {
                            serviceName = it
                            landingViewModel.updateServiceName(serviceName)
                        },
                        label = { Text("Act or service name") },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { component.onEvent(AddServiceNameEvent.GoToAddressScreen) },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = horizontalPadding)
                    ) {
                        Text(text = "Continue")
                    }
                }
            }
        }
    )
}
