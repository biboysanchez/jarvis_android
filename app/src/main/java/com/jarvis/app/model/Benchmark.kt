package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.obj
import com.jarvis.app.extension.string
import org.json.JSONObject

class Benchmark (json:JSONObject) {
    val id           = json.string("_id")
    val danamasSaham = json.double("danamas_saham")
    val jciIndex     = json.double("jci_index")
}