package com.jarvis.app.extension

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

fun JSONObject.string(value: String): String{
    return try {
        this.getString(value)
    }catch (e: JSONException){
        ""
    }
}

fun JSONObject.int(value: String) : Int{
    return try {
        this.getInt(value)
    }catch (e:JSONException){
        0
    }
}

fun JSONObject.double(value: String) : Double{
    return try {
        this.getDouble(value)
    }catch (e:JSONException){
        0.0
    }
}

fun JSONObject.bool(value: String) : Boolean{
    return try {
        this.getBoolean(value)
    }catch (e:JSONException){
        false
    }
}

fun JSONObject.obj(value: String) : JSONObject {
    return try {
        this.getJSONObject(value)
    }catch (e:JSONException){
        JSONObject()
    }
}

fun JSONObject.arr(value: String) : JSONArray {
    return try {
        this.getJSONArray(value)
    }catch (e:JSONException){
        JSONArray()
    }
}