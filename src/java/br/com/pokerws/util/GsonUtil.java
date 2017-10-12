/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.pokerws.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.ws.rs.client.Entity.json;

/**
 *
 * @author PauLinHo
 */
public class GsonUtil implements JsonDeserializer<Date>, JsonSerializer<Date> {
    
    private SimpleDateFormat JSON_STRING_DATE;
    
    public GsonUtil(SimpleDateFormat sdf){
        this.JSON_STRING_DATE = sdf;
    }

    

    @Override
    public Date deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {

        try {
            return JSON_STRING_DATE.parse(je.getAsString());
        } catch (ParseException ex) {
            Logger.getLogger(GsonUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public JsonElement serialize(Date t, Type type, JsonSerializationContext jsc) {

        String data = JSON_STRING_DATE.format(t);

        return new JsonPrimitive(data);
    }
}
