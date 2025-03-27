package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.example.dto.Owner;
import org.example.dto.PetType;
import org.example.dto.Role;
import org.example.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.example.utils.Constants.BASE_URL;
import static org.example.utils.Constants.SUCCESS_CODE;
import static org.hamcrest.Matchers.equalTo;

public class SimpleTest {
    static RequestSpecification requestSpecification;


    @BeforeAll
    static void setUp() {
        requestSpecification = RestAssured.given()
                .baseUri(BASE_URL)
                .accept(ContentType.JSON);

        // Добавить одного пользователя с ролью admin
        User user = User.builder()
                .username("admin")
                .password("adminpass")
                .enabled(true)
                .roles(List.of(Role.builder().name("admin").build()))
                .build();

        requestSpecification.given()
                .with().body(user)
                .contentType("application/json")
                .when()
                .post("/petclinic/api/users")
                .then()
                .statusCode(SUCCESS_CODE)
                .body("username", equalTo("admin"));

        // Добавить 2 владельцев питомцев
        Owner owner1 = Owner.builder()
                .firstName("Alex")
                .lastName("Dove")
                .address("123 Main St")
                .city("LargeTown")
                .telephone("123-456-7890")
                .build();

        Owner owner2 = Owner.builder()
                .firstName("Jane")
                .lastName("Mitchell")
                .address("456 Main St")
                .city("SmallTown")
                .telephone("098-765-4321")
                .build();

        requestSpecification.given()
                .with().body(owner1)
                .contentType("application/json")
                .when()
                .post("/petclinic/api/owners")
                .then()
                .statusCode(201);

        requestSpecification.given()
                .with().body(owner2)
                .contentType("application/json")
                .when()
                .post("/petclinic/api/owners")
                .then()
                .statusCode(201);

        // Добавить 3 вида питомцев
        PetType petType1 = PetType.builder()
                .name("Cat")
                .build();

        PetType petType2 = PetType.builder()
                .name("Dog")
                .build();

        PetType petType3 = PetType.builder()
                .name("Hamster")
                .build();

        requestSpecification.given()
                .with().body(petType1)
                .contentType("application/json")
                .when()
                .post("/petclinic/api/pettypes")
                .then()
                .statusCode(SUCCESS_CODE);

        requestSpecification.given()
                .with().body(petType2)
                .contentType("application/json")
                .when()
                .post("/petclinic/api/pettypes")
                .then()
                .statusCode(SUCCESS_CODE);

        requestSpecification.given()
                .with().body(petType3)
                .contentType("application/json")
                .when()
                .post("/petclinic/api/pettypes")
                .then()
                .statusCode(SUCCESS_CODE);
    }

    @Test
    void testStub() {
        Assertions.assertTrue(true);
    }

    @Test
    void testConnect() {
        requestSpecification
                .given()
                    .get("/petclinic")
                .then()
                    .statusCode(SUCCESS_CODE);
    }
}
