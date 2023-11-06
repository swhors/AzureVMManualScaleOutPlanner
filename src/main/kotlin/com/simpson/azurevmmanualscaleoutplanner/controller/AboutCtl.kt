package com.simpson.azurevmmanualscaleoutplanner.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/about")
@RestController
class AboutCtl {
    @GetMapping("/")
    fun getAbout() : ResponseEntity<String> {
        return ResponseEntity("Hello", HttpStatus.OK)
    }
}