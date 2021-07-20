package com.training.RunningTracker.сontroller;

import com.training.RunningTracker.entity.User;
import com.training.RunningTracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequestMapping("/RunningTracker")
@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/login")
    User login(@RequestBody User newUser) throws SQLException {
        return userService.getUserByLoginAndPassword(newUser);
    }

   /* @DeleteMapping("/delete/{id}")
    User delete(@RequestBody User newUser) throws  SQLException {
        return userService.deleteUser(newUser);
    }*/




}
