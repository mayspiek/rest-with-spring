package br.com.mayara.services;

import br.com.mayara.controllers.PersonController;
import br.com.mayara.data.vo.v1.PersonVO;
import br.com.mayara.data.vo.v2.PersonVOV2;
import br.com.mayara.mapper.DozzerMapper;
import br.com.mayara.mapper.custom.PersonMapper;
import br.com.mayara.model.Person;
import br.com.mayara.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PersonServices {

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;
    @Autowired
    PersonMapper mapper;

    public List<PersonVO> findAll() {

        logger.info("Finding all people!");
        var persons = DozzerMapper.parseListObjects(repository.findAll(), PersonVO.class);
        persons.stream().forEach(p -> {
            try {
                p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return persons;
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.getReferenceById(id);
        PersonVO vo = DozzerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }

    public PersonVO create(PersonVO person){
        logger.info("Creating one person!");
        var entity = DozzerMapper.parseObject(person, Person.class);
        var vo =  DozzerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }
    public PersonVOV2 createV2(PersonVOV2 person) {
        logger.info("Creating one person!");
        var entity = mapper.convertVoToEntity(person);
        var vo =  mapper.convertEntityToVo(repository.save(entity));
        return vo;
    }

    public PersonVO update(PersonVO person) {

        logger.info("Updating one person!");

        var entity = repository.getReferenceById(person.getKey());

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getLastName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        var vo =  DozzerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {

        logger.info("Deleting one person!");

        var entity = repository.getReferenceById(id);
        repository.delete(entity);
    }
}
