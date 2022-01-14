package com.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping( "/example" )
class Controller {

    @GetMapping
    ResponseEntity<String> example() {
        return ResponseEntity.ok( "example" );
    }

}
