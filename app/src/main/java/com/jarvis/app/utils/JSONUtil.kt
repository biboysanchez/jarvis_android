package com.jarvis.app.utils

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JSONUtil {
    private const val TAG = "JSONUtil"
    fun isSuccess(response: String): Boolean {
        Log.i(TAG, ": RESPONSE: $response")
        return JSONObject(response).getBoolean("success")
    }

    @Throws(JSONException::class)
    fun jsonToMap(json: JSONObject): HashMap<String, String> {
        var retMap = HashMap<String, String>()
        if (json !== JSONObject.NULL) {
            retMap = toMap(json)
        }
        return retMap
    }

    @Throws(JSONException::class)
    fun toMap(`object`: JSONObject): HashMap<String, String> {
        val map = HashMap<String, String>()

        val keysItr = `object`.keys()
        while (keysItr.hasNext()) {
            val key = keysItr.next()
            var value = `object`.get(key)

            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            map[key] = value.toString()
        }
        return map
    }

    @Throws(JSONException::class)
    fun toList(array: JSONArray): List<Any> {
        val list = ArrayList<Any>()
        for (i in 0 until array.length()) {
            var value = array.get(i)
            if (value is JSONArray) {
                value = toList(value)
            } else if (value is JSONObject) {
                value = toMap(value)
            }
            list.add(value)
        }
        return list
    }

}