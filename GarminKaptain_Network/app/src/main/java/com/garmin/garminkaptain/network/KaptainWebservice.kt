package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.Review
import com.garmin.garminkaptain.data.ReviewSummary
import com.garmin.garminkaptain.model.MapBoundingBox
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


class KaptainWebservice : Webservice {

    interface Api {

        @POST("community/api/v1/points-of-interest/bbox")
        fun getPoiList(@Body boundingBox: MapBoundingBox): Call<PoiListResponse>

        //  @GET("community/api/v1/points-of-interest/{poiID}/summary?_={timestamp}")
        @GET("community/api/v1/points-of-interest/{poiID}/summary")
        fun getPoiSummary(
            @Path("poiID") poiId: Int,
            //@Query("timestamp") timestamp: Long
        ): Call<ReviewSummary>


        @GET("community/api/v1/points-of-interest/{poiID}/reviews?_={timestamp}")
        fun getPoiReviews(
            @Path("poiID") poiId: Int,
            @Path("timestamp") timestamp: Long
        ): Call<List<Review>>
    }


    override fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse> {

        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClientBuilder = OkHttpClient()
            .newBuilder()
            .addInterceptor(logging)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://activecaptain-stage.garmin.com/")
            .client(httpClientBuilder.build())
            .build()


        val service: Api = retrofit.create(Api::class.java)
        return service.getPoiList(bbBox)
    }
}