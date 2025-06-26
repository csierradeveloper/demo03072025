package com.csierra.demo03072025.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ChangeNameLater {

    @GetMapping("/test")
    ResponseEntity<String> test() {
        return ResponseEntity.ok().body("Hello World");
    }
}
