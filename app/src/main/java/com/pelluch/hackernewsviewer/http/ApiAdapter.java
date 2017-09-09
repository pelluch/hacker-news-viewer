package com.pelluch.hackernewsviewer.http;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by pablo on 9/9/17.
 */

public class ApiAdapter<T> implements JsonDeserializer<T> {

    @Override
    public T deserialize(JsonElement json, Type type,
                         JsonDeserializationContext context) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        try {
            JsonArray data = json.getAsJsonObject().get("hits")
                    .getAsJsonArray();
            // Deserialize it. You use a new instance of Gson to avoid infinite recursion
            // to this deserializer
            return new Gson().fromJson(data, type);
        } catch(IllegalStateException e) {
            e.printStackTrace();
            return null;
        }


    }
}