import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
actual fun HowWorksSection() {
    BoxWithConstraints {
        val isMobile = maxWidth < 600.dp
        if (isMobile) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "How it works.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(16.dp))
                HowItWorksStep(
                    imageUrl = "https://picsum.photos/100",
                    title = "Browse and compare.",
                    description = "Compare rates and availability of local entertainers and vendors."
                )
                Spacer(modifier = Modifier.height(16.dp))
                HowItWorksStep(
                    imageUrl = "https://picsum.photos/100",
                    title = "Book securely.",
                    description = "Booking through our platform ensures payment protection, amazing service, and no-hassle refunds with our Worry-Free Guarantee."
                )
                Spacer(modifier = Modifier.height(16.dp))
                HowItWorksStep(
                    imageUrl = "https://picsum.photos/100",
                    title = "Enjoy your event.",
                    description = "Sit back, relax, and watch your party come to life."
                )
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "How it works.",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    HowItWorksStep(
                        imageUrl = "https://picsum.photos/100",
                        title = "Browse and compare.",
                        description = "Compare rates and availability of local entertainers and vendors."
                    )
                    HowItWorksStep(
                        imageUrl = "https://picsum.photos/100",
                        title = "Book securely.",
                        description = "Booking through our platform ensures payment protection, amazing service, and no-hassle refunds with our Worry-Free Guarantee."
                    )
                    HowItWorksStep(
                        imageUrl = "https://picsum.photos/100",
                        title = "Enjoy your event.",
                        description = "Sit back, relax, and watch your party come to life."
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https://picsum.photos/600/300",
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        }
    }
}

@Composable
fun HowItWorksStep(imageUrl: String, title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )
        }
    }
}
