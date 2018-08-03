package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static String NAME_CODE = "name";
    private final static String MAIN_NAME_CODE = "mainName";
    private final static String ALSO_KNOWN_AS_CODE = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN_CODE = "placeOfOrigin";
    private final static String DESCRIPTION_CODE = "description";
    private final static String IMAGE_CODE = "image";
    private final static String INGREDIENTS_CODE = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject mainJsonObject = new JSONObject( json );
            JSONObject name = mainJsonObject.optJSONObject( NAME_CODE );
            String mainName = name.optString( MAIN_NAME_CODE );
            JSONArray alsoKnownAsJsonArray = name.optJSONArray( ALSO_KNOWN_AS_CODE );
            List <String> alsoKnownAs = convertToListFromJsonArray( alsoKnownAsJsonArray );
            String placeOfOrigin = mainJsonObject.optString( PLACE_OF_ORIGIN_CODE );
            String description = mainJsonObject.optString( DESCRIPTION_CODE );
            String image = mainJsonObject.optString( IMAGE_CODE );
            JSONArray ingredientsJsonArray = mainJsonObject.optJSONArray( INGREDIENTS_CODE );
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
            list.add( jsonArray.optString( i ) );
        }

        return list;
    }
}
