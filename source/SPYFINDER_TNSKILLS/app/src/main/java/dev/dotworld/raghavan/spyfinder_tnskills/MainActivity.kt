package dev.dotworld.raghavan.spyfinder_tnskills
//Raghavan @ TN Skills Project
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import coil.compose.rememberAsyncImagePainter
import dev.dotworld.raghavan.spyfinder_tnskills.ui.theme.SPYFINDERTNSKILLSTheme
import dev.dotworld.raghavan.spyfinder_tnskills.ui.theme.kort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class UserData(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val avatar: String
)

interface ApiGetData {
    @GET("users")
    suspend fun getusers(@Query("page") page: Int): UserResponse
}


data class UserResponse(
    val page: Int,
    val data: List<UserData>,
    val total_pages: Int
)

val reloadTrigger = mutableStateOf(true)

class MainActivity : ComponentActivity() {
    val currentpage = mutableStateOf(1)
    val jsonArray = JSONArray()
    fun isnetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

    fun getCurrentDateTime(): String {
        val dateFormat = SimpleDateFormat("d/M/yyyy-HH:mm:ss", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        writeToSharedPreferences(this, "data", getCurrentDateTime().toString())

        fetchUsers(this, currentpage.value)
        installSplashScreen()

        setContent {
            SPYFINDERTNSKILLSTheme {
                Containerall()

            }
        }
    }

    private val apiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiGetData::class.java)
    }

    private fun fetchUsers(context: Context, page: Int) {
        val data = readFromSharedPreferences(context, "disdata1", "null")
        val data2 = readFromSharedPreferences(context, "disdata2", "null")
        if (data == "null" || data2 == "null") {
            val userList = mutableListOf<UserData>()
            lifecycleScope.launch(Dispatchers.IO) {

                //  var totalPages = 1 /

                try {

                    val response = apiService.getusers(page)
                    userList.addAll(response.data)
                    //   Log.d("Mainctivity", " ${userList}")
                    //  totalPages = response.total_pages



                    userList.forEach { userData ->
                        val jsonObject = JSONObject().apply {
                            put("id", userData.id)
                            put("email", userData.email)
                            put("first_name", userData.first_name)
                            put("last_name", userData.last_name)
                            put(
                                "avatar",
                                userData.avatar.removePrefix("https://reqres.in/img/faces/")
                            )
                        }
                        jsonArray.put(jsonObject)
                    }


                    println(jsonArray.toString())
                    if (currentpage.value == 2) {
                        writeToSharedPreferences(context, "disdata2", jsonArray.toString())
                    } else {

                        writeToSharedPreferences(context, "disdata1", jsonArray.toString())
                    }


                } catch (e: Exception) {
                    Log.e("MainActivity", "Error fetching users: ${e.message}")

                }


            }

        } else {

            val userListm: List<UserData> = if (currentpage.value == 2) {
                jsonArrayToArrayList(data2)
            } else {
                jsonArrayToArrayList(data)
            }



            userListm.forEach { userData ->
                val jsonObject = JSONObject().apply {
                    put("id", userData.id)
                    put("email", userData.email)
                    put("first_name", userData.first_name)
                    put("last_name", userData.last_name)
                    put("avatar", userData.avatar.removePrefix("https://reqres.in/img/faces/"))
                }
                jsonArray.put(jsonObject)
            }


            println(jsonArray.toString())

        }
    }

    fun jsonArrayToArrayList(jsonArrayString: String): List<UserData> {
        val jsonArray = JSONArray(jsonArrayString)
        val userList = mutableListOf<UserData>()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val email = jsonObject.getString("email")
            val firstName = jsonObject.getString("first_name")
            val lastName = jsonObject.getString("last_name")
            val avatar = jsonObject.getString("avatar")
            val userData = UserData(id, email, firstName, lastName, avatar)
            userList.add(userData)
        }

        return userList
    }

    @Composable
    fun Containerall() {
        val context = LocalContext.current
        val isAuth by remember { mutableStateOf(boolisauth(context, "isauth", false)) }


        if (!isAuth) {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(context, intent, null)
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .fillMaxWidth()
                ) {

                    Hero()
                    ButtonList()
                }
            }

            Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(top = 15.dp))


            RefreshableColumn(jsonArray)


        }
    }

    @Composable
    fun RefreshableColumn(jsonArray: JSONArray) {
        var reloadState by remember { mutableStateOf(0) }

        LaunchedEffect(reloadState) {
            while (true) {
                delay(50)
                reloadState++
            }

        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)) {

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val firstName = jsonObject.getString("first_name")
                val lastName = jsonObject.getString("last_name")
                val email = jsonObject.getString("email")
                val avatar = jsonObject.getString("avatar")
                Item(id.toString(), firstName, lastName, email, avatar)
            }
        }
    }

    //@Preview
    @Composable
    fun Hero() {
        val context = LocalContext.current
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 30.dp)
        ) {
            val imagePainter: Painter = painterResource(id = R.drawable.logo)
            val name = readFromSharedPreferences(context, "name", "Not")
            val crdata = readFromSharedPreferences(context, "data", "19/3/2024-12:00")
            Column {
                Row {
                    Text("Hey ,", fontSize = 45.sp, color = Color.White, fontFamily = kort)
                    Text(
                        name.toUpperCase(),
                        fontSize = 45.sp,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color(red = 0, green = 223, blue = 255),
                        fontFamily = kort
                    )

                }
                Row(modifier = Modifier.padding(start = 4.dp)) {
                    Text("Last logged at", color = Color.Gray, fontFamily = kort, fontSize = 10.sp)
                    Text(
                        crdata,
                        color = Color.Gray,
                        fontFamily = kort,
                        modifier = Modifier.padding(start = 5.dp),
                        fontSize = 10.sp
                    )
                }

            }
        }
    }

    @Composable
    fun ButtonList() {
        val context = LocalContext.current
        fun reloading(context: Context) {
            writeToSharedPreferences(context, "data1", "null")
            writeToSharedPreferences(context, "data2", "null")
        }

        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.clip(RoundedCornerShape(10.dp))) {
                val imagePainter: Painter = painterResource(id = R.drawable.refresh)
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.background(Color(red = 80, green = 80, blue = 80))
                ) {
                    IconButton(onClick = {
                        reloadbox();reloading(context);fetchUsers(
                        context,
                        currentpage.value
                    );if (!isnetwork(context)) {
                        showToast(context, "Turn On Your Network")
                    }
                    }, modifier = Modifier.background(Color(red = 80, green = 80, blue = 80))) {
                        Image(
                            painter = imagePainter,
                            contentDescription = "Refresh",
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(100.dp)
                                )
                                .align(Alignment.TopEnd)
                                .size(70.dp)
                                .padding(8.dp)


                        )

                    } //repeat(jsonArray.length()) {jsonArray.remove(0);}
                }
            }

            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                IconButton(onClick = {
                    if (currentpage.value == 2) {
                        currentpage.value--;
                        reloadbox();fetchUsers(context, currentpage.value);
                    } else {
                        showToast(context, "No Page")
                    }
                }, modifier = Modifier.background(Color(red = 80, green = 80, blue = 80))) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "left",
                        tint = Color(red = 0, green = 223, blue = 255)
                    )

                }
            }

            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.background(Color(red = 80, green = 80, blue = 80))
                ) {
                    Text(
                        currentpage.value.toString(),
                        fontSize = 23.sp,
                        modifier = Modifier.padding(top = 5.dp),
                        color = Color(red = 0, green = 223, blue = 255),
                        fontFamily = kort
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                IconButton(onClick = {
                    if (currentpage.value == 1) {
                        currentpage.value++; reloadbox();fetchUsers(context, currentpage.value);
                    } else {
                        showToast(context, "No Page")
                    }
                }, modifier = Modifier.background(Color(red = 80, green = 80, blue = 80))) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "right",
                        tint = Color(red = 0, green = 223, blue = 255)
                    )

                }
            }
            val imagePainter: Painter = painterResource(id = R.drawable.logout)
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                IconButton(
                    onClick = { checkredirect(context) },
                    modifier = Modifier.background(Color(red = 80, green = 80, blue = 80))
                ) {
                    Image(
                        painter = imagePainter,
                        contentDescription = "logout",
                        modifier = Modifier
                            .clip(
                                RoundedCornerShape(100.dp)
                            )
                            .align(Alignment.TopEnd)
                            .size(70.dp)
                            .padding(8.dp)


                    )

                }
            }

        }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun reloadbox() {
        repeat(jsonArray.length()) { jsonArray.remove(0); }
    }
}

fun checkredirect(context: Context) {
    setisauthcheck(context, "isauth", false);
    val intent = Intent(context, LoginActivity::class.java)
    startActivity(context, intent, null)
}

@Composable
fun Item(id: String, firstname: String, lastname: String, email: String, image: String) {

    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(Color.Gray)
            .fillMaxWidth()
    ) {

        Row {
            Image(
                painter = rememberAsyncImagePainter("https://reqres.in/img/faces/" + image),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(70.dp)
                    .padding(top = 12.dp)
                    .clip(
                        RoundedCornerShape(200.dp)
                    )


                // Optional: Set image size
            )

            Column(modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)) {
                Row {
                    Text(
                        "ID :",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 1.dp),
                        color = Color.White,
                        fontFamily = kort
                    )
                    Text(
                        id,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color(red = 0, green = 223, blue = 255),
                        fontFamily = kort
                    )
                }
                Row {
                    Text(
                        "Name :",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 1.dp),
                        color = Color.White,
                        fontFamily = kort
                    )
                    Text(
                        firstname,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color(red = 0, green = 223, blue = 255),
                        fontFamily = kort
                    )
                    Text(
                        lastname,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color(red = 0, green = 223, blue = 255),
                        fontFamily = kort
                    )
                }
                Row {
                    Text(
                        "Email ID :",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 1.dp),
                        color = Color.White,
                        fontFamily = kort
                    )
                    Text(
                        email,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color(red = 0, green = 223, blue = 255),
                        fontFamily = kort
                    )
                }
            }
        }
    }
}


fun setisauthcheck(context: Context, key: String, value: Boolean) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean(key, value)
    editor.apply()
}

fun readFromSharedPreferences(context: Context, key: String, defaultValue: String): String {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, defaultValue) ?: defaultValue
}


fun writeToSharedPreferences(context: Context, key: String, value: String) {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(key, value)
    editor.apply()
}

fun boolisauth(context: Context, key: String, defaultValue: Boolean): Boolean {
    val sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(key, defaultValue) ?: defaultValue
}



