import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit

/**
 * Helpful links:
 *
 * https://blog.mindorks.com/okhttp-interceptor-making-the-most-of-it
 * https://stackoverflow.com/questions/42927216/retrofitokhttp-http-504-unsatisfiable-request-only-if-cached
 */
fun getCache(): Cache {
    val httpCacheDirectory = File("http-cache")
    /* // DON'T do create new file, because then instead of http-cache dir a empty file will be created
    .apply {
        createNewFile()
    }
    // On Android do following
    // File(applicationContext.getCacheDir(), "http-cache")
    */
    val cacheSize = 10 * 1024 * 1024 // 10 MiB
    println("cache path : ${httpCacheDirectory.absolutePath}")
    return Cache(httpCacheDirectory, cacheSize.toLong())
}

class NetworkCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(15, TimeUnit.MINUTES) // 15 minutes cache
            .build()
        return response.newBuilder()
            // pragma: no-cache
            .removeHeader("Pragma")
            // cache-control: max-age=43200, but if you want to override
//            .removeHeader("Cache-Control")
//            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}

class ForceOfflineCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isNetAvailable()) {
            request = request.newBuilder()
                // 504 Unsatisfiable Request (only-if-cached) <> if "Pragma" header is not removed by network interceptor
                .cacheControl(CacheControl.FORCE_CACHE)
                .build()
        }
        return chain.proceed(request)
    }
}

private fun isNetAvailable(): Boolean {
    return try {
        URL("http://www.google.com").openConnection().apply {
            connect()
            getInputStream().close()
        }
        true
    } catch (e: MalformedURLException) {
        throw RuntimeException(e)
    } catch (e: IOException) {
        false
    }
}