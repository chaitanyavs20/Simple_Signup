package com.example.chaitanya.gym_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chaitanya.gym_app.ui.theme.Gym_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Gym_AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SignUp()
                    
                }
            }
        }
    }
}

@Composable
fun SignUp() {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.background(Color.Black)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SignUpHeader()
            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            SignUpFields(name = name,
                email = email,
                password = password,
                onNameChange = {
                    name = it
                },
                onEmailChange = {
                    email = it
                },
                onPasswordChange = {
                    password = it
                }
            )
        }
    }
}

@Composable
fun SignUpHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(R.drawable.gym_logo), contentDescription = "Gym Logo")
        Text(
            text = "BodyWizard", color = Color.White, fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Text(
            text = "Ready To Start Your Fitness Journey", color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

    }
}

@Composable
fun SignUpFields(
    name: String,
    email: String,
    password: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DemoFields(value = name,
            label = "Name",
            placeholder = "Enter your name",
            onValueChange = onNameChange,
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name") })
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        DemoFields(
            value = email,
            label = "Email",
            placeholder = " Enter your email",
            onValueChange = onEmailChange,
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        DemoFields(
            value = password,
            label = "Password",
            placeholder = "Enter your password ",
            onValueChange = onPasswordChange,
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Button(
            onClick = { },
            modifier = Modifier
                .clip(RoundedCornerShape(70))
                .fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = "Sign Up")

        }
        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Column() {
            MainText()
        }

    }
}

private const val TAG_URL = "ANNOTATION_TAG_URL"

@Composable
fun MainText() {
    val uriHandler = LocalUriHandler.current // utility to open links in a browser
    val text1 = "By clicking SignUp, you agree to our User Agreement Privacy Policy" // "my website" contains a link

    val link1 = "https://policies.google.com/terms?hl=en-US"
    val annotatedText1 = attachLink(
        source = text1,
        segment = "User Agreement Privacy Policy",
        link = link1
    )
    val String1 =ClickableText(
        text = annotatedText1,
        style = TextStyle(textAlign = TextAlign.Center),
        onClick = {  annotatedText1
            .getStringAnnotations(TAG_URL, it, it)
            .firstOrNull()
            ?.let { url -> uriHandler.openUri(url.item)}


}
    )
}

fun attachLink(
    source: String,
    segment: String,
    link: String
): AnnotatedString {
    val builder = AnnotatedString.Builder() // builder to attach metadata(link)
    builder.append(source) // load current text into the builder

    val start = source.indexOf(segment) // get the start of the span "my website"
    val end = start + segment.length // get the end of the span
    val hyperlinkStyle = SpanStyle(
        color = Color.Blue,
        textDecoration = TextDecoration.Underline
    ) // create a hyperlink text style

    builder.addStyle(hyperlinkStyle, start, end) // style "my website" to make it look like a link
    builder.addStringAnnotation(TAG_URL, link, start, end) // attach the link to the span. We can then access it via the TAG_URL

    return builder.toAnnotatedString()
}

@Composable
fun DemoFields(
    value: String,
    label: String,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit),
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        },
        placeholder = {
            Text(text = placeholder, fontSize = 20.sp, fontWeight = FontWeight.Normal)
        },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = Modifier.background(Color.White),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Gray
        )
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    Gym_AppTheme {
       SignUp()
    }
}


