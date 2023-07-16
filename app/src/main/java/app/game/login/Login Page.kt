package app.game.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(
    loginViewModel: LoginViewModel
) {

    val loginUiState by loginViewModel.flowLoginState.collectAsState()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val focusManager = LocalFocusManager.current
    val completableTask = {
        loginViewModel.username("")
        loginViewModel.password("")
        focusManager.clearFocus()
    }

    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.systemBars)
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState())
            .height(screenHeight),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.padding(top = 30.dp),
            shape = RoundedCornerShape(
                topStart = 30.dp,
                topEnd = 30.dp
            ),
            elevation = CardDefaults.cardElevation(10.dp)
        ){
            Column(
                modifier = Modifier.padding(
                    start = 30.dp, end = 30.dp, bottom = 30.dp, top = 80.dp
                ),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Column {
                    Text(
                        text = "Log in to app",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Welcome Message",
                        fontSize = 18.sp,
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    val isUsernameEmpty by remember { derivedStateOf { loginUiState.username.isNullOrEmpty() } }
                    val isPasswordEmpty by remember { derivedStateOf { loginUiState.password.isNullOrEmpty() } }
                    var showPassword by remember { mutableStateOf(false) }

                    CustomTextBox(
                        text = loginUiState.username ?: "",
                        onValueChange = loginViewModel::username,
                        showPlaceholder = isUsernameEmpty,
                        placeHolder = "Login@email.com",
                        imeAction = ImeAction.Next,
                        isEmail = true,
                    )

                    CustomTextBox(
                        text = loginUiState.password ?: "",
                        onValueChange = { loginViewModel.password(it) },
                        showPlaceholder = isPasswordEmpty,
                        placeHolder = "Your password",
                        imeAction = ImeAction.Done,
                        isPassword = true,
                        onDone = {
                            completableTask()
                        },
                        showPassword = showPassword,
                        showPasswordFun = { showPassword = !showPassword }
                    )
                }

                Text(
                    modifier = Modifier.clickable { },
                    text = "Forgot Password?",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Card(
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth(),
                    onClick = { completableTask() },
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                    elevation = CardDefaults.cardElevation(5.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onTertiary
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Divider(
                        modifier = Modifier.width(100.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        modifier = Modifier.padding(10.dp),
                        text = "Or login with",
                        fontSize = 12.sp,
                    )
                    Divider(
                        modifier = Modifier.width(100.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ){
                        SignInWithOther(
                            companionIcon = R.drawable.google,
                            onClick = {  /*TODO*/ },
                        )

                        SignInWithOther(
                            companionIcon = R.drawable.facebook,
                            onClick = {  /*TODO*/ }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                NoAccount()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextBox(
    text: String,
    onValueChange: (String) -> Unit,
    showPlaceholder: Boolean,
    placeHolder: String,
    imeAction: ImeAction,
    isPassword: Boolean = false,
    onDone:(() -> Unit)? = null,
    isEmail: Boolean = false,
    showPasswordFun:(() -> Unit)? = null,
    showPassword: Boolean = true
){
    val boxSelected = MaterialTheme.colorScheme.primary
    val boxUnSelected = MaterialTheme.colorScheme.outline
    var isBoxSelected by remember { mutableStateOf(true) }
    val highlightBox by remember{
        derivedStateOf { if(!isBoxSelected) boxUnSelected else boxSelected }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, highlightBox),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.8f)
                    .padding(10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                BasicTextField(
                    modifier = Modifier
                        .onFocusChanged {
                            isBoxSelected = !isBoxSelected
                        }
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        imeAction = imeAction,
                        keyboardType = if (isEmail) KeyboardType.Email else KeyboardType.Password // makes it so text is not underlined
                    ),
                    singleLine = true,
                    keyboardActions = KeyboardActions(
                        onDone = { onDone?.let { it() } }
                    ),
                    cursorBrush =
                    Brush.horizontalGradient(
                        0f to MaterialTheme.colorScheme.primary,
                        1f to MaterialTheme.colorScheme.primary
                    ),
                    textStyle =
                    TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    value = text,
                    onValueChange = { onValueChange(it) },
                    visualTransformation = if (!showPassword) PasswordVisualTransformation() else VisualTransformation.None, // this makes characters in to dots for password
                    decorationBox = { innerTextField ->
                        if (showPlaceholder) {
                            Text(
                                text = placeHolder,
                                color = Color.Gray,
                                fontSize = 14.sp,
                            )
                        }
                        innerTextField()
                    },
                )
            }

            if(isPassword) {
                Card(
                    modifier = Modifier.padding(5.dp),
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(8.dp),
                    onClick = { showPasswordFun?.let { showPassword -> showPassword() } },
                ) {
                    Column(
                        modifier = Modifier.padding(7.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        Text(
                            text = if(showPassword) "VIEW" else "HIDE",
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSecondary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInWithOther(
    companionIcon: Int,
    onClick: () -> Unit,
) {

    Card(
        modifier = Modifier.size(50.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        onClick = { onClick() },
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Icon(
            modifier = Modifier.padding(10.dp),
            painter = painterResource(id = companionIcon),
            contentDescription = "Login logo",
            tint = Color.Unspecified
        )

    }
}

@Composable
fun NoAccount() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = "Don't have an account? ",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier.clickable {  },
            text = "Sign up",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}