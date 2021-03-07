package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.model.MapBoundingBox
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

private const val BASE_URL = "https://activecaptain-stage.garmin.com/"

class KaptainWebservice : Webservice {

    interface Api {

        @POST("community/api/v1/points-of-interest/bbox")
        fun getPoiList(@Body boundingBox: MapBoundingBox): Call<PoiListResponse>

        //  @GET("community/api/v1/points-of-interest/{poiID}/summary?_={timestamp}")
        @GET("community/api/v1/points-of-interest/{poiID}/summary")
        fun getPoiSummary(
            @Path("poiID") poiId: Long,
        ): Call<PoiSummaryResponse>

        @GET("community/api/v1/points-of-interest/{poiID}/reviews")
        fun getPoiReviews(
            @Path("poiID") poiId: Long,
        ): Call<List<Review>>
    }

    companion object {
        private val moshi: Moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        private val logging = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val httpClientBuilder: OkHttpClient.Builder = OkHttpClient()
            .newBuilder()
            .addInterceptor(logging)

        private val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(httpClientBuilder.build())
            .build()

        val service: Api = retrofit.create(Api::class.java)
    }


    override fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse> {
        return service.getPoiList(bbBox)
    }

    override fun getPoiReviews(poiId: Long): Call<List<Review>> {
        return service.getPoiReviews(poiId)
    }

    override fun getReviewSummary(id: Long): Call<PoiSummaryResponse> {
        return service.getPoiSummary(id)
    }
}