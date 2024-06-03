package com.app.readingtracker.share.helper

import com.auth0.android.jwt.JWT

fun isJWTExpired(): Boolean {
    val token: String = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTcxNzEzMjExMSwiaWF0IjoxNzE3MDQ1NzExfQ.2QiLue1uP_1_5JUC55xkxkJAtUDTQG1Et_rtn176isk"
    val jwt: JWT = JWT(token)
    return  jwt.isExpired(0)
}