package br.com.mayara.services;

import br.com.mayara.controllers.PersonController;
import br.com.mayara.data.vo.v1.PersonVO;
import br.com.mayara.mapper.DozzerMapper;
import br.com.mayara.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServices implements UserDetailsService{

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    @Autowired
    UserRepository repository;

    public UserServices(UserRepository repository) {
        this.repository = repository;
    }

    public PersonVO findById(Long id) {

        logger.info("Finding one person!");

        var entity = repository.getReferenceById(id);
        PersonVO vo = DozzerMapper.parseObject(entity, PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
        return vo;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Finding one person by username " + username + "!");
        var user = repository.findByUserName(username);
        if (username != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found!");
        }
    }

   
}
