package me.brendonp.tabcorp.data

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import me.brendonp.tabcorp.data.entities.LaunchEntity
import me.brendonp.tabcorp.data.entities.RocketEntity
import me.brendonp.tabcorp.domain.models.Launch
import me.brendonp.tabcorp.domain.models.Rocket
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class SpaceXService : SpaceRepository {

    private companion object {
        const val BASE_URL = "https://api.spacexdata.com/v3/"
    }

    private interface Client {

        @GET("launches")
        fun getLaunches(): Call<List<LaunchEntity>>

        @GET("launches/{id}")
        fun getLaunch(@Path("id") id: String): Call<LaunchEntity>

        @GET("rockets/{id}")
        fun getRocket(@Path("id") id: String): Call<RocketEntity>
    }

    private val client: Client

    init {
        // Normally we would have a global Retrofit and Gson instance and inject it
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        client = retrofit.create(Client::class.java)
    }

    override fun getLaunches(): List<Launch> {
        return client.getLaunches().execute().body()?.map(SpaceEntityMapper::mapLaunch)
            ?: throw UnknownError()
    }

    override fun getLaunch(id: String): Launch? {
        return client.getLaunch(id).execute().body()?.let {
            SpaceEntityMapper.mapLaunch(it)
        }
    }

    override fun getRocket(id: String): Rocket? {
        return SpaceEntityMapper.mapRocket(client.getRocket(id).execute().body())
    }
}