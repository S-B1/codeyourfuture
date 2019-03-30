package com.cyf.webapi;

import com.cyf.entities.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService
{
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/save")
    public String save()
    {
//        userRepository.save(new User("2", "Paula", "email@email.com"));
//        userRepository.save(new User("3", "Paula", "email@email.com"));
//        userRepository.save(new User("4", "Paula", "email@email.com"));
        return "user";
    }
}
