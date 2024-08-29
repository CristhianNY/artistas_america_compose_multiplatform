package payment
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
import androidx.compose.material.Checkbox
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import navigation.payment.AddPaymentComponent
import navigation.payment.AddPaymentEvent

@Composable
fun PaymentScreen(component: AddPaymentComponent) {
    var couponCode by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var securityCode by remember { mutableStateOf("") }
    var billingAddress by remember { mutableStateOf("") }
    var billingZip by remember { mutableStateOf("") }
    var autoRenewalChecked by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Empty title as in the design */ },
                navigationIcon = {
                    IconButton(onClick = { component.onEvent(AddPaymentEvent.GoBack) }) {
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
                        .padding(horizontal = horizontalPadding)
                        .verticalScroll(rememberScrollState())
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Select a Featured plan and start getting gigs!",
                        style = MaterialTheme.typography.h6.copy(fontSize = 24.sp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        PlanOption(price = "$84.50", originalPrice = "$169", duration = "3 months", isSelected = true)
                        PlanOption(price = "$144.50", originalPrice = "$289", duration = "6 months")
                        PlanOption(price = "$239.50", originalPrice = "$479", duration = "1 year")
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = couponCode,
                        onValueChange = { couponCode = it },
                        label = { Text("Coupon code") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { /* Lógica para aplicar el cupón */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Apply coupon")
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    OutlinedTextField(
                        value = cardNumber,
                        onValueChange = { cardNumber = it.filter { char -> char.isDigit() } },
                        label = { Text("Card number") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = expirationDate,
                            onValueChange = { expirationDate = it.filter { char -> char.isDigit() || char == '/' } },
                            label = { Text("Expiration date") },
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = securityCode,
                            onValueChange = { securityCode = it.filter { char -> char.isDigit() } },
                            label = { Text("Security code") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = billingAddress,
                        onValueChange = { billingAddress = it },
                        label = { Text("Billing street address") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = billingZip,
                        onValueChange = { billingZip = it.filter { char -> char.isDigit() } },
                        label = { Text("Billing ZIP/Postal code") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = autoRenewalChecked,
                            onCheckedChange = { autoRenewalChecked = it }
                        )
                        Text(
                            text = "I acknowledge that my credit card will be charged every 3 months unless I log in and cancel auto-renewal.",
                            style = MaterialTheme.typography.body2,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { /* Lógica para lanzar PromoKit */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Launch my PromoKit")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    TextButton(
                        onClick = { /* Lógica para cancelar la suscripción */ }
                    ) {
                        Text(text = "Skip this step", color = Color.Gray)
                    }
                }
            }
        }
    )
}

@Composable
fun PlanOption(price: String, originalPrice: String, duration: String, isSelected: Boolean = false) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = price, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(text = originalPrice, fontSize = 14.sp, color = Color.Gray, textDecoration = TextDecoration.LineThrough)
        Text(text = "for $duration", fontSize = 12.sp, color = Color.Gray)
        if (isSelected) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Selected", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Green)
        }
    }
}
