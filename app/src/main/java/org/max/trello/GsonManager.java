package org.max.trello;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class GsonManager {

    public static Gson getGson() {
        return new GsonBuilder()
                .setExclusionStrategies(getExclusionStrategy())
                .setFieldNamingStrategy(getFieldNamingStrategy())
                .create();
    }

    private static FieldNamingStrategy getFieldNamingStrategy() {
        return new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                if("webId".equals(f.getName())) {
                    return "id";
                }
                return f.getName();
            }
        };
    }

    private static ExclusionStrategy getExclusionStrategy() {
        return new ExclusionStrategy() {

            List<String> excludedFields = Arrays.asList("id", "edited", "toDelete");

            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return excludedFields.contains(f.getName());
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };
    }
}
