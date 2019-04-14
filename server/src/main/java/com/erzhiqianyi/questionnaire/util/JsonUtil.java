package com.erzhiqianyi.questionnaire.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;

public class JsonUtil {
    public static <T> String toJson(T value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String result = objectMapper.writeValueAsString(value);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            T result = objectMapper.readValue(json,typeReference);
            return result;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
