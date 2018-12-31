package com.jarvis.app.sessions

import android.content.Context
import android.content.SharedPreferences
import com.jarvis.app.model.User
import org.json.JSONException
import org.json.JSONObject

class UserSession(context: Context) {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        this.preferences = context.getSharedPreferences(USER_PREFERENCE, 0)
        this.editor      = preferences.edit()
    }

    /**
     * User status if already logged in
     */
    val isActive: Boolean
        get() = preferences.getBoolean(USER_LOGGED, false)


    /**
     * User token received from login response
     */
    fun token(): String?{
        return preferences.getString(USER_TOKEN, "")
    }

    /**
     * User unique id
     */
    fun fkUserId(): String?{
        return preferences.getString(FK_USER_ID, "")
    }

    /**
     * Set token value
     */
    fun setToken(token: String) {
        editor.putString(USER_TOKEN, token).apply()
    }

    /**
     * Set user id value
     */
    fun setUserId(userId: String) {
        editor.putString(FK_USER_ID, userId).apply()
    }

    fun setIsLogged(){
        editor.putBoolean(USER_LOGGED, true).apply()
    }

    fun setUserPin(pin: String){
        editor.putString(USER_PIN, pin).apply()
    }

    fun pin():String?{
        return preferences.getString(USER_PIN, "")
    }

    /**
     * Save user object to local data
     */
    fun authorize(userObj: JSONObject){
        try {
            editor.putString(USER_OBJECT, userObj.toString()).apply()
        }catch (e: JSONException){
            e.printStackTrace()
        }
    }

    /**
     * Get user data from local data
     */
    fun user(): User?{
        return try {
            val userObj = preferences.getString(USER_OBJECT, "")
            val json = JSONObject(userObj)
            User(json)
        }catch (e: JSONException){
            null
        }
    }

    /**
     * Clear all user save data
     */
    fun deAuthorize(){
        editor.clear().apply()
    }

    /**
     * Key for each setters and getters
     */
    companion object {
        const val USER_PREFERENCE = "user_preference"
        const val USER_LOGGED     = "user_logged"
        const val USER_TOKEN      = "user_token"
        const val USER_OBJECT     = "user_object"
        const val FK_USER_ID      = "user_id"
        const val USER_PIN        = "user_pin"
    }
}