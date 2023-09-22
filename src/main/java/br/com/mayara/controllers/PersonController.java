package br.com.mayara.controllers;

import br.com.mayara.data.vo.v1.PersonVO;
import br.com.mayara.data.vo.v2.PersonVOV2;
import br.com.mayara.services.PersonServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person/v1")
@Tag(name="People", description = "Endpoints for Managing People")
public class PersonController {
    @Autowired
    private PersonServices service;
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Finds all people recorded", description = "Find all people recorded in the database", tags = {"People"},
            responses = {
            @ApiResponse(description = "Success", responseCode = "200", content={
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                    ),
            }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content=@Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content=@Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content=@Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content=@Content)
        }
    )
    public List<PersonVO> findAll() throws Exception {
        return service.findAll();
    }
     @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     @Operation(summary = "Finds a person", description = "Finds a Person", tags = {"People"},
             responses = {
                     @ApiResponse(description = "Success", responseCode = "200", content={
                             @Content(
                                     mediaType = "application/json",
                                     array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                             ),
                     }),
                     @ApiResponse(description = "Bad Request", responseCode = "400", content=@Content),
                     @ApiResponse(description = "Unauthorized", responseCode = "401", content=@Content),
                     @ApiResponse(description = "Not Found", responseCode = "404", content=@Content),
                     @ApiResponse(description = "Internal Server Error", responseCode = "500", content=@Content)
             }
     )
     public PersonVO findById(@PathVariable(value="id")Long id){

        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates a person", description = "Creates a Person", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                            ),
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content=@Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content=@Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content=@Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content=@Content)
            }
    )
    public PersonVO create(@RequestBody PersonVO person) {
        return service.create(person);
    }
    @PostMapping(value = "/v2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Creates a person version 2", description = "Creates a Person version 2", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                            ),
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content=@Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content=@Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content=@Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content=@Content)
            }
    )
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person){
        return service.createV2(person);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Updates a person", description = "Updates a Person", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200", content={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                            ),
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content=@Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content=@Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content=@Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content=@Content)
            }
    )
    public PersonVO update(@RequestBody PersonVO person) {
        return service.update(person);
    }

    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Deletes a person", description = "Deletes a Person", tags = {"People"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content={
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                            ),
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content=@Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content=@Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content=@Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content=@Content)
            }
    )
    public ResponseEntity<?> delete(@PathVariable(value="id")Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    //a espcificação de consumo e produção de dados é opcional, mas nesse caso precisamos por causa do swagger
}
