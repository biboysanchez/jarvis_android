package com.jarvis.app.model

import com.jarvis.app.extension.string
import org.json.JSONObject

class User (json: JSONObject) {
    var userName = json.string("name")
    var fkUserId = json.string("fk_user_id")
}