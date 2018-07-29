package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainJsonObject = new JSONObject( json );
            JSONObject name = mainJsonObject.getJSONObject( "name" );
            String mainName = name.getString( "mainName" );
            JSONArray alsoKnownAsJsonArray = name.getJSONArray( "alsoKnownAs" );
            List <String> alsoKnownAs = convertToListFromJsonArray( alsoKnownAsJsonArray );
            String placeOfOrigin = mainJsonObject.optString( "placeOfOrigin" );
            String description = mainJsonObject.getString( "description" );
            String image = mainJsonObject.getString( "image" );
            JSONArray ingredientsJsonArray = mainJsonObject.getJSONArray( "ingredients" );
            List <String> ingredients = convertToListFromJsonArray( ingredientsJsonArray );
            return new Sandwich( mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients );
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List <String> convertToListFromJsonArray(JSONArray jsonArray) throws JSONException {
        List <String> list = new ArrayList <>( jsonArray.length() );

        for (int i = 0; i < jsonArray.length(); i++) {
            list.add( jsonArray.getString( i ) );
        }

        return list;
    }
}
