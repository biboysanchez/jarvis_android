package com.jarvis.app.utils

import android.content.Context
import android.util.Log
import com.jarvis.app.extension.simpleDialog
import com.jarvis.app.extension.string
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object JSONUtil {
    fun isSuccess(context: Context, response: String): Boolean {
        val json = JSONObject(response)
        return try {
            if (json.string("message_action").contains("SUCCESS")){
                true
            }else{
                context.simpleDialog("Failed",json.string("message_desc"))
                false
            }
        }catch (e:JSONException){
            e.printStackTrace()
            false
        }
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