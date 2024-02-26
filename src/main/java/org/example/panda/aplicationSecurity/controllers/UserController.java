package org.example.panda.aplicationSecurity.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.example.panda.aplicationSecurity.services.IUserService;
import org.example.panda.aplicationSecurity.services.models.request.UserRequest;
import org.example.panda.utilities.ErrorDetalles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(originPatterns = "*")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    @Autowired
    IUserService userService;

    @Operation(
            summary = "Gets all users with active status",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema()
                            )
                    )
            }
    )
    @GetMapping("/users")
    ResponseEntity<List<UserRequest>> listUsers(){
        return ResponseEntity.ok().body(userService.findAll());
    }
    @Operation(
            summary = "Update users",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema()
                            )
                    ),
                    @ApiResponse(
                            description = "Bad Request / Invalid data",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetalles.class)
                            )
                    )
                    ,
                    @ApiResponse(
                            description = "Internal Server Error / existing data",
                            responseCode = "500",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetalles.class)
                            )
                    )
            }
    )
    @PutMapping("user/{id}")
    ResponseEntity<UserRequest> updateUser(@Valid @RequestBody UserRequest userRequest, @PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.update(id, userRequest));
    }
    @Operation(
            summary = "Delete users",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid token",
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema()
                            )
                    ),
                    @ApiResponse(
                            description = "Not Found",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorDetalles.class)
                            )
                    )
            }
    )
    @DeleteMapping("user/{id}")
    ResponseEntity<UserRequest> deleteUser(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(userService.delete(id));
    }
}
