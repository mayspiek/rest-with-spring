package br.com.mayara.controllers;

import br.com.mayara.data.vo.v1.PersonVO;
import br.com.mayara.data.vo.v2.PersonVOV2;
import br.com.mayara.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonServices service;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PersonVO> findAll() throws Exception {
        return service.findAll();
    }
     @GetMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
     public PersonVO findById(@PathVariable(value="id")Long id){
        return service.findById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO create(@RequestBody PersonVO person) {
        return service.create(person);
    }
    @PostMapping(value = "/v2", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVOV2 createV2(@RequestBody PersonVOV2 person){
        return service.createV2(person);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public PersonVO update(@RequestBody PersonVO person) {
        return service.update(person);
    }

    @DeleteMapping(value="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@PathVariable(value="id")Long id) throws Exception {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    //a espcificação de consumo e produção de dados é opcional, mas nesse caso precisamos por causa do swagger
}
