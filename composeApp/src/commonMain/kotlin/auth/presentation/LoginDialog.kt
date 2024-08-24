import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import artistas.composeapp.generated.resources.Res
import artistas.composeapp.generated.resources.compose_multiplatform
import auth.presentation.AuthViewModel
import auth.presentation.LoginState
import auth.presentation.RegisterState
import com.arkivanov.decompose.ComponentContext
import navigation.home.HomeComponent
import navigation.home.HomeEvent
import navigation.lading.LandingComponent
import navigation.lading.LandingEvent
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginDialog(onDismiss: () -> Unit, viewModel: AuthViewModel, component: ComponentContext) {
    var showLoginForm by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .widthIn(min = 200.dp, max = 280.dp)
                .shadow(16.dp, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colors.surface,
            elevation = 8.dp
        ) {
            if (showLoginForm) {
                LoginForm(
                    onDismiss = onDismiss,
                    viewModel = viewModel,
                    component = component,
                    onRegisterClick = { showLoginForm = false }
                )
            } else {
                RegisterForm(
                    onDismiss = onDismiss,
                    viewModel = viewModel,
                    onLoginClick = { showLoginForm = true }
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginForm(
    onDismiss: () -> Unit,
    viewModel: AuthViewModel,
    component: ComponentContext,
    onRegisterClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    val (emailFocusRequester, passwordFocusRequester) = FocusRequester.createRefs()

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = Strings.HELLO_AGAIN,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        var email by remember { mutableStateOf("") }
        var emailValid by remember { mutableStateOf(true) }
        var password by remember { mutableStateOf("") }
        var forgotPasswordEmail by remember { mutableStateOf("") }
        var showForgotPasswordField by remember { mutableStateOf(false) }

        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it.trim()
                emailValid = emailRegex.matches(email)
            },
            label = { Text(Strings.EMAIL_LABEL) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(emailFocusRequester)
                .onKeyEvent {
                    if (it.key == Key.Tab) {
                        passwordFocusRequester.requestFocus()
                        true
                    } else {
                        false
                    }
                },
            isError = !emailValid,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if (emailValid) MaterialTheme.colors.primary else Color.Red,
                unfocusedBorderColor = if (emailValid) MaterialTheme.colors.onSurface.copy(alpha = 0.5f) else Color.Red,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            )
        )

        if (!emailValid) {
            Text(
                text = Strings.INVALID_EMAIL_ADDRESS,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it.trim() },
            label = { Text(Strings.PASSWORD_LABEL) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (emailValid) {
                        viewModel.login(email, password)
                    }
                }
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colors.primary,
                unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState is LoginState.Error) {
            Text(
                text = Strings.LOGIN_ERROR,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (uiState is LoginState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else if (uiState is LoginState.Success) {
            LaunchedEffect(Unit) {
                onDismiss()
                when (component) {
                    is LandingComponent -> {}
                    is HomeComponent -> {}
                }
            }
        } else {
            Button(
                onClick = {
                    if (emailValid) {
                        viewModel.login(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
            ) {
                Text(Strings.LOG_IN, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { showForgotPasswordField = !showForgotPasswordField }) {
            Text(Strings.FORGOT_PASSWORD, color = Color(0xFF007BFF))
        }

        if (showForgotPasswordField) {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = forgotPasswordEmail,
                onValueChange = {
                    forgotPasswordEmail = it.trim()
                    emailValid = emailRegex.matches(forgotPasswordEmail)
                },
                label = { Text(Strings.RESET_PASSWORD_HINT) },
                modifier = Modifier.fillMaxWidth(),
                isError = !emailValid,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (emailValid) MaterialTheme.colors.primary else Color.Red,
                    unfocusedBorderColor = if (emailValid) MaterialTheme.colors.onSurface.copy(alpha = 0.5f) else Color.Red,
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red
                )
            )

            if (!emailValid) {
                Text(
                    text = Strings.INVALID_EMAIL_ADDRESS,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = { /* Handle password reset */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
            ) {
                Text(Strings.RESET_PASSWORD, color = Color.White)
            }
        }

        TextButton(onClick = onRegisterClick) {
            Text(Strings.REGISTER_PROMPT, color = Color(0xFF007BFF))
        }

        TextButton(onClick = { /* Handle trouble logging in */ }) {
            Text(Strings.TROUBLE_LOGGING_IN, color = Color(0xFF007BFF))
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterForm(onDismiss: () -> Unit, viewModel: AuthViewModel, onLoginClick: () -> Unit) {
    val registerState by viewModel.registerState.collectAsState()

    if (registerState is RegisterState.Success) {
        SuccessMessage()
    } else {
        val (nameFocusRequester, lastNameFocusRequester, emailFocusRequester, passwordFocusRequester, confirmPasswordFocusRequester) = FocusRequester.createRefs()

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = Strings.CREATE_AN_ACCOUNT,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            var name by remember { mutableStateOf("") }
            var lastName by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            var confirmPassword by remember { mutableStateOf("") }
            var passwordsMatch by remember { mutableStateOf(true) }
            var emailValid by remember { mutableStateOf(true) }

            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

            OutlinedTextField(
                value = name,
                onValueChange = { name = it.trim() },
                label = { Text(Strings.FIRST_NAME_LABEL) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(nameFocusRequester)
                    .onKeyEvent {
                        if (it.key == Key.Tab) {
                            lastNameFocusRequester.requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { lastNameFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it.trim() },
                label = { Text(Strings.LAST_NAME_LABEL) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(lastNameFocusRequester)
                    .onKeyEvent {
                        if (it.key == Key.Tab) {
                            emailFocusRequester.requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { emailFocusRequester.requestFocus() }
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it.trim()
                    emailValid = emailRegex.matches(email)
                },
                label = { Text(Strings.EMAIL_LABEL) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(emailFocusRequester)
                    .onKeyEvent {
                        if (it.key == Key.Tab) {
                            passwordFocusRequester.requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                isError = !emailValid,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = if (emailValid) MaterialTheme.colors.primary else Color.Red,
                    unfocusedBorderColor = if (emailValid) MaterialTheme.colors.onSurface.copy(alpha = 0.5f) else Color.Red,
                    errorBorderColor = Color.Red,
                    errorLabelColor = Color.Red
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { passwordFocusRequester.requestFocus() }
                )
            )

            if (!emailValid) {
                Text(
                    text = Strings.INVALID_EMAIL_ADDRESS,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it.trim() },
                label = { Text(Strings.PASSWORD_LABEL) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(passwordFocusRequester)
                    .onKeyEvent {
                        if (it.key == Key.Tab) {
                            confirmPasswordFocusRequester.requestFocus()
                            true
                        } else {
                            false
                        }
                    },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { confirmPasswordFocusRequester.requestFocus() }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it.trim()
                    passwordsMatch = password == it
                },
                label = { Text(Strings.CONFIRM_PASSWORD_LABEL) },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(confirmPasswordFocusRequester)
                    .onKeyEvent {
                        if (it.key == Key.Tab) {
                            true
                        } else {
                            false
                        }
                    },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (passwordsMatch && emailValid) {
                            viewModel.register(name, lastName, email, password)
                        }
                    }
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary,
                    unfocusedBorderColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                )
            )

            if (!passwordsMatch) {
                Text(
                    text = Strings.PASSWORDS_DO_NOT_MATCH,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (registerState is RegisterState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Button(
                    onClick = {
                        if (passwordsMatch && emailValid) {
                            viewModel.register(name, lastName, email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF007BFF))
                ) {
                    Text(Strings.REGISTER, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = onLoginClick) {
                Text(Strings.LOGIN_PROMPT, color = Color(0xFF007BFF))
            }
        }
    }
}

@Composable
fun SuccessMessage() {
    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = Strings.SUCCESS_IMAGE_DESC
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = Strings.CONFIRMATION_EMAIL_SENT,
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )
    }
}
