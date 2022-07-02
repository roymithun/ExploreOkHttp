import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    makeSimpleOkHttpRequest()
}

fun makeSimpleOkHttpRequest() {
    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(2000, TimeUnit.SECONDS)
        .readTimeout(5000, TimeUnit.SECONDS)
        .addInterceptor(CustomAuthenticationInterceptor())
        .addInterceptor(CustomInterceptor())
        .addInterceptor(
            HttpLoggingInterceptor { println("MY_TAG: $it") }.apply {
                setLevel(HttpLoggingInterceptor.Level.HEADERS)
            })
        .build()

    val request = Request.Builder()
        .url("https://jsonplaceholder.typicode.com/posts")
        .build()

    okHttpClient.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            println("exception $e")
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            val posts = Gson().fromJson(body, Array<Post>::class.java)
            println(posts[0])
        }
    })
}

class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("User-Agent", "RamChagol")
            .build()

        return chain.proceed(request)
    }
}

class CustomAuthenticationInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.request().let {
        val request = it.newBuilder().addHeader("Authorization", "Bearer 123456789").build()
        chain.proceed(request)
    }
}

// https://objectpartners.com/2018/06/08/okhttp-authenticator-selectively-reauthorizing-requests/
// https://stackoverflow.com/questions/61779947/okhttp-add-basic-authentication-header
class CustomAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        TODO("Not yet implemented")
    }
}
