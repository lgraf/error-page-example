package com.example.errorpage.errorpage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@SpringBootTest( webEnvironment = RANDOM_PORT/*, properties = "server.servlet.context-path="*/ )
class ErrorPageApplicationTests {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    void unauthorizedShouldShowFullErrorResponseWhenErrorPathIsPermitted() {
        var response = testRestTemplate.getForEntity( "/example", JsonNode.class );

        assertThat( response.getStatusCode() ).isEqualTo( UNAUTHORIZED );

        JsonNode body = response.getBody();

        assertThat( body ).isNotNull();
        assertThat( body.findValue( "status" ).asInt() ).isEqualTo( 401 );
        assertThat( body.findValue( "error" ).asText() ).isEqualTo( "Unauthorized" );
    }


    @Test
    void forbiddenShouldShowFullErrorResponseWhenErrorPathIsPermitted() {
        var response = testRestTemplate.withBasicAuth( "username", "password" )
                .getForEntity( "/example", JsonNode.class );

        assertThat( response.getStatusCode() ).isEqualTo( FORBIDDEN );

        JsonNode body = response.getBody();

        assertThat( body ).isNotNull();
        assertThat( body.findValue( "status" ).asInt() ).isEqualTo( 403 );
        assertThat( body.findValue( "error" ).asText() ).isEqualTo( "Forbidden" );
    }

}
