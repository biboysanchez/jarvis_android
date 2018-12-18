package com.jarvis.app.model

import com.jarvis.app.extension.double
import com.jarvis.app.extension.int
import com.jarvis.app.extension.string
import org.json.JSONObject
import java.util.*

class Table5 (json:JSONObject) {
    var company     = json.string("company")
    var levelId1    = json.int("level_id_1")
    var levelId2    = json.int("level_id_2")
    var portfolio   = json.string("portfolio")
    var saham       = json.double("saham")
    var target      = json.double("target")
    var jciIndex    = json.double("jci_index")
    var matrix      = json.string("matrix")

    companion object {
        fun table5DropdownList():List<String>{
            return Arrays.asList("Level Id 1", "Level Id 2", "Saham", "Target", "JCI Index")
        }
    }
}