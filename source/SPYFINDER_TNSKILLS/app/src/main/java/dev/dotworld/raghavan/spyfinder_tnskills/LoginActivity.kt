package dev.dotworld.raghavan.spyfinder_tnskills
//Raghavan @ TN Skills Project
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.gson.annotations.SerializedName
import dev.dotworld.raghavan.spyfinder_tnskills.ui.theme.SPYFINDERTNSKILLSTheme
import dev.dotworld.raghavan.spyfinder_tnskills.ui.theme.kor
import dev.dotworld.raghavan.spyfinder_tnskills.ui.theme.kort
import kotlinx.coroutines.Dispatchers
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

var loading by mutableStateOf(false)

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        setContent {
            SPYFINDERTNSKILLSTheme {

                Box(modifier = Modifier.fillMaxSize()) {
                    ImageComponent()
                    LoginText()
                    GetInput()

                    GetPassword()


                }




            }

        }
    }

    @Composable
    fun ImageComponent() {

        val imagePainter: Painter = painterResource(id = R.drawable.logo)
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = imagePainter,
                contentDescription = "App Logo",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(250.dp)

            )

        }
    }

    @Composable
    fun LoginText() {
        val textToDisplay = "Login"

        Row(modifier = Modifier.height(550.dp)) {
            Text(
                text = textToDisplay,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .align(Alignment.CenterVertically) // Or other desired alignment
                    .padding(top = 10.dp, bottom = 10.dp, start = 28.dp, end = 16.dp),

                color = Color.White,
                fontSize = 40.sp,
                fontFamily = kor
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GetPassword() {
//box 2
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun GetInput() {
        Column(
            modifier = Modifier.padding(top = 330.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var emailText by remember { mutableStateOf("") }

            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it },
                label = { Text(text = "Email", color = Color.White, fontFamily = kort) },
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Email, contentDescription = "Email Icon")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, imeAction = ImeAction.Search
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(red = 0, green = 223, blue = 255),
                    unfocusedBorderColor = Color(red = 0, green = 170, blue = 200)
                )
            )

            // password input
            var passwordText by remember { mutableStateOf("") }

            OutlinedTextField(
                modifier = Modifier.padding(top = 20.dp),
                value = passwordText,
                onValueChange = { passwordText = it },
                label = { Text(text = "Password", color = Color.White, fontFamily = kort) },
                singleLine = true,
                textStyle = TextStyle(color = Color.White),
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Lock, contentDescription = "Password Icon")
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(red = 0, green = 223, blue = 255),
                    unfocusedBorderColor = Color(red = 0, green = 170, blue = 200)
                )
            )
            val context = LocalContext.current

            Column(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 100.dp),
                horizontalAlignment = (Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    modifier = Modifier
                        .height(50.dp)
                        .width(250.dp),
                    onClick = { Loginbtn(context, emailText, passwordText) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(red = 0, green = 223, blue = 255)
                    ),
                    enabled = !loading
                ) {

                    Text(text = "Login", fontSize = 20.sp, fontFamily = kort)

                }
                Box(modifier = Modifier.padding(top = 60.dp)) {
                    //  var loading by remember { mutableStateOf(false) }


                    if (!loading) return

                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }


            }

        }
    }

    //Log.i("TAG", "Email: $emailText, Password: $passwordText")

    fun Loginbtn(context: Context, emailText: String, passwordText: String) {
        if (emailText != "" && passwordText != "") {
            loading = true
            checkuserdata(context, emailText, passwordText)
        } else {

            showToast(this, "Please Provide Login Details")
        }

    }
}


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
//api

fun checkuserdata(context: Context, email: String, password: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/api/") //base url
        .addConverterFactory(GsonConverterFactory.create()).build()


    val apiService = retrofit.create(ApiService::class.java)
    val request = LoginRequest(email, password)
    val call = apiService.login(request)

    call.enqueue(object : Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

            if (response.isSuccessful) {
                val token = response.body()?.token
                if (token != null) {
                    loading = false
                    if (response.code() == 200) {
                        writeTosavetoken(context, "token", token)
                        writeTosavetoken(context, "name", extractFirstNameFromEmail(email))
                        showToast(context, "Logged in ")
                        setisauth(context, "isauth", true)
                        val intent = Intent(context, MainActivity::class.java)
                        ContextCompat.startActivity(context, intent, null)


                    }
                }
            } else {

                if (response.code() == 400) {
                    showToast(context, "unauthorized user access")
                    loading = false
                }


            }
        }

        fun extractFirstNameFromEmail(email: String): String {
            val parts = email.split("@")
            val firstNameParts = parts[0].split(".")
            return if (firstNameParts.isNotEmpty()) {
                firstNameParts[0]
            } else {
                "Unknown"
            }
        }

        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
            showToast(context, "Failed to make login request: ${t.message}")
            loading = false
        }
    })


}

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

}

// Data classes for API requests and responses
data class LoginRequest(
    val email: String, val password: String
)

data class LoginResponse(
    @SerializedName("token") val token: String
)

fun setisauth(context: Context, key: String, value: Boolean) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun writeTosavetoken(context: Context, key: String, value: String) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}


