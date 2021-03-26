package com.thduc.eshop.utilities

import com.thduc.eshop.constant.UploadType
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.text.SimpleDateFormat
import java.util.*

import java.util.regex.Matcher
import java.util.regex.Pattern


object Helper {
    fun hashTagsStringFromString(str: String?): List<String> {
        val MY_PATTERN: Pattern = Pattern.compile("#(\\S+)")
        val mat: Matcher = MY_PATTERN.matcher(str)
        val strs: MutableList<String> = ArrayList()
        while (mat.find()) {
            //System.out.println(mat.group(1));
            strs.add(mat.group(1))
        }
        return strs
    }

    fun currentTime(upload_type: UploadType): String {
        val rand = Random()
        return upload_type.toString() + "_" + SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Date()) + "_" + java.lang.String.valueOf(
            rand.nextInt(1000)
        )
    }

    fun bcriptPassword(password: String?): String {
//        Password p = new Password();
        return BCryptPasswordEncoder().encode(password)
    }

//    fun Successfully(action: String): ResponseEntity<*> {
//        val hashMap: HashMap<*, *> = HashMap<Any?, Any?>()
//        hashMap["message"] = "$action successfully"
//        return ResponseEntity<Any?>(hashMap, HttpStatus.OK)
//    }
}