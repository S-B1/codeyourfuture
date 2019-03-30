package com.cyf.webapi;

import com.cyf.entities.User;
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
        userRepository.save(new User("Paula", "email@email.com"));
        return "save";
    }
}
