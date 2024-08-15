package add_listing.presentation.add_listing_steps

import Strings
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.check_success
import artistas.composeapp.generated.resources.compose_multiplatform
import navigation.add_listing.AddChoosePlanComponent
import navigation.add_listing.AddChoosePlanEvent
import org.jetbrains.compose.resources.painterResource
import support.HoverableButton

@Composable
fun ChoosePlanScreen(component: AddChoosePlanComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title as in the design */ },
                navigationIcon = {
                    IconButton(onClick = { component.onEvent(AddChoosePlanEvent.GoBack) }) {
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
                        .verticalScroll(rememberScrollState()), // Añadir scroll vertical
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
                        text = "All done!",
                        style = MaterialTheme.typography.h6,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Now, choose the plan that’s right for you.",
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = horizontalPadding)
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    PricingTableComposable(isSmallScreen)
                }
            }
        }
    )
}

@Composable
fun PricingTableComposable(isSmallScreen: Boolean) {
    if (isSmallScreen) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            PricingCard(
                title = "Free",
                price = "$0/mo",
                description = "Try it for free!",
                features = listOf(
                    "For beginners that want to start with a few leads.",
                    "Fewest leads",
                    "5% booking fee",
                    "Low visibility",
                    "No client phone numbers until booking",
                    "Up to 2 categories",
                    "Accept deposits up to $500",
                    "Add video and audio samples",
                    "Up to 10 photos"
                ),
                buttonText = "Choose This Plan",
                isCheckIconVisible = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            PricingCard(
                title = "Pro",
                price = "$69.50/3 mos",
                description = "Save $98.50 billed annually at $179.50",
                features = listOf(
                    "For semi-serious pros that want a steady amount of leads.",
                    "Average of 16x more leads than Free",
                    "2.5% booking fee",
                    "High visibility",
                    "Access to client phone numbers",
                    "Up to 15 categories",
                    "Accept deposits up to $1000",
                    "Add video and audio samples",
                    "Up to 50 photos"
                ),
                buttonText = "Choose This Plan",
                isCheckIconVisible = false
            )
            Spacer(modifier = Modifier.height(16.dp))
            PricingCard(
                title = "Featured",
                price = "$84.50/3 mos",
                description = "Save $98.50 billed annually at $239.50",
                features = listOf(
                    "For serious professionals that want the most possible leads.",
                    "Average of 25x more leads than Free",
                    "2.5% booking fee",
                    "Highest visibility",
                    "Access to client phone numbers",
                    "Up to 20 categories",
                    "Accept deposits up to $2000",
                    "Add video and audio samples",
                    "Up to 100 photos"
                ),
                buttonText = "Choose This Plan",
                isButtonHoveredInitially = true,
                isCheckIconVisible = true
            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp, vertical = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PricingCard(
                title = "Free",
                price = "$0/mo",
                description = "Try it for free!",
                features = listOf(
                    "For beginners that want to start with a few leads.",
                    "Fewest leads",
                    "5% booking fee",
                    "Low visibility",
                    "No client phone numbers until booking",
                    "Up to 2 categories",
                    "Accept deposits up to $500",
                    "Add video and audio samples",
                    "Up to 10 photos"
                ),
                buttonText = "Choose This Plan",
                modifier = Modifier.weight(1f),
                isCheckIconVisible = false
            )
            Spacer(modifier = Modifier.width(16.dp))
            PricingCard(
                title = "Pro",
                price = "$69.50/3 mos",
                description = "Save $98.50 billed annually at $179.50",
                features = listOf(
                    "For semi-serious pros that want a steady amount of leads.",
                    "Average of 16x more leads than Free",
                    "2.5% booking fee",
                    "High visibility",
                    "Access to client phone numbers",
                    "Up to 15 categories",
                    "Accept deposits up to $1000",
                    "Add video and audio samples",
                    "Up to 50 photos"
                ),
                buttonText = "Choose This Plan",
                modifier = Modifier.weight(1f),
                isCheckIconVisible = false
            )
            Spacer(modifier = Modifier.width(16.dp))
            PricingCard(
                title = "Featured",
                price = "$84.50/3 mos",
                description = "Save $98.50 billed annually at $239.50",
                features = listOf(
                    "For serious professionals that want the most possible leads.",
                    "Average of 25x more leads than Free",
                    "2.5% booking fee",
                    "Highest visibility",
                    "Access to client phone numbers",
                    "Up to 20 categories",
                    "Accept deposits up to $2000",
                    "Add video and audio samples",
                    "Up to 100 photos"
                ),
                buttonText = "Choose This Plan",
                modifier = Modifier.weight(1f),
                isButtonHoveredInitially = true,
                isCheckIconVisible = true
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
    isButtonHoveredInitially: Boolean = false,
    isCheckIconVisible: Boolean
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = price,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Column {
                features.forEachIndexed { index, feature ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        if (isCheckIconVisible) {
                            Image(
                                painter = painterResource(Res.drawable.check_success),
                                contentDescription = "Check Icon",
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = if (index == 0) feature else "• $feature",
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = if (index == 0) FontWeight.Bold else FontWeight.Normal
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
