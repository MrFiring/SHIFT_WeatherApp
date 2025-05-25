package ru.mrfiring.shiftweatherapp.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.ResponseBody
import ru.mrfiring.shiftweatherapp.data.network.model.CityModel
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.util.zip.GZIPInputStream

class CitiesParserImpl(private val moshi: Moshi): CitiesParser {

    override suspend fun decompressGZip(responseBody: ResponseBody?): String {
        val output: StringBuilder = StringBuilder()

        responseBody?.let {
            val gzipStream = GZIPInputStream(ByteArrayInputStream(responseBody.bytes()))
            val input = BufferedReader(InputStreamReader(gzipStream))

            var read: String? = ""
            while (read != null) {
                read = input.readLine()
                output.append(read)
            }
            input.close()
            gzipStream.close()
        }
        return output.toString()
    }

    override suspend fun parseJson(json: String): List<CityModel> {
        val type = Types.newParameterizedType(List::class.java, CityModel::class.java)
        val jsonAdapter = moshi.adapter<List<CityModel>>(type).lenient()
        return jsonAdapter.fromJson(json) ?: emptyList()
    }
}