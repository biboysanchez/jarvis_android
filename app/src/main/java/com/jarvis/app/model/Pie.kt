package com.jarvis.app.model

class Pie {
    var amount  = 0
    var color   = 0
    var name    = ""
    var percent = 0

    constructor(percent: Int, color: Int, name: String, amount: Int) {
        this.percent = percent
        this.color = color
        this.name = name
        this.amount = amount
    }
}