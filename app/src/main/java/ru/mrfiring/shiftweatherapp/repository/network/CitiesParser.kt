package ru.mrfiring.shiftweatherapp.repository.network

import android.util.Log
import com.squareup.moshi.Types
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.util.zip.GZIPInputStream

class CitiesParser() {

    suspend fun decompressGZip(responseBody: ResponseBody): String{
        val output: StringBuilder = StringBuilder()
        try{
            val gzipStream = GZIPInputStream(ByteArrayInputStream(responseBody.bytes()))
            val input = BufferedReader(InputStreamReader(gzipStream))

            var read: String? = ""
            while(read != null){
                read = input.readLine()
                output.append(read)
            }
            input.close()
            gzipStream.close()

        }catch (ex: IOException){
            Log.e("CitiesParser", "IOError ${ex.toString()}")

        }
        return output.toString()
    }

    suspend fun parseJson(json: String) : List<City>{
        val type = Types.newParameterizedType(List::class.java, City::class.java)
        val jsonAdapter = OpenWeatherApi.moshi.adapter<List<City>>(type).lenient()
        return jsonAdapter.fromJson(json) ?: emptyList()
    }
}