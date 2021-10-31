package io.kio.android.codebase.network

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import java.lang.reflect.Type

class NetworkVendorsTest {

    @Test
    fun `test coroutines`() = runBlocking {
        repeat(5) {
            delay(1000)
            println(it)
        }
    }

    @Test
    fun `test serializations`() {

        val myInfo = MyInfo("Kio", 22)

        val jsonString = Json.encodeToString(MyInfo.serializer(), myInfo)
        println(myInfo)

        val myInfo2 = Json.decodeFromString(MyInfo.serializer(), jsonString)
        println(myInfo2)

        assertEquals(myInfo, myInfo2)

    }

    @Test
    fun `test OkHttp`() {
        val client = OkHttpClient.Builder().build()

        val request = client.newCall(Request.Builder().apply {
            this.url("https://api.github.com/users")
            this.method("get", null)
        }.build())

        println(request.execute().body?.string())
    }

    @Test
    fun `test retrofit`() {
        val client = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder().apply {
            this.client(client)
            this.baseUrl("https://api.github.com/")
            this.addConverterFactory(object : Converter.Factory() {
                override fun responseBodyConverter(
                    type: Type,
                    annotations: Array<out Annotation>,
                    retrofit: Retrofit
                ): Converter<ResponseBody, *> {
                    return Converter<ResponseBody, String> { value -> value.string() }
                }

                override fun stringConverter(
                    type: Type,
                    annotations: Array<out Annotation>,
                    retrofit: Retrofit
                ): Converter<*, String> {
                    return Converter<Any, String> { it.toString() }
                }
            })
        }.build()

        assertNotNull(
            retrofit.create(GithubService::class.java).tryFetchUsers().execute().body().also {
                println(it)
            })

    }

}

@Serializable
data class MyInfo(val name: String, val age: Int)

interface GithubService {

    @GET("/users")
    fun tryFetchUsers(): Call<String>

}
