package com.newstestproject.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.newstestproject.data.util.JsonParser
import com.newstestproject.domain.model.Source

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromMeaningsJson(json: String): Source? {
        return jsonParser.fromJson<Source>(
            json,
            object : TypeToken<Source>(){}.type
        )
    }

    @TypeConverter
    fun toMeaningsJson(meanings: Source): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<Source>(){}.type
        ) ?: "[]"
    }
}