package com.example.HomeToHomeSpring.service;

import com.example.HomeToHomeSpring.model.Role;
import com.example.HomeToHomeSpring.model.User;
import com.example.HomeToHomeSpring.repository.RoleDao;
import com.example.HomeToHomeSpring.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {


    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;


    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleDao.save(adminRole);

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("Default role for newly created record");
        roleDao.save(userRole);


        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ROLE_ADMIN");
        roleAdmin.setRoleDescription("Default role for newly ROLE_ADMIN record");
        roleDao.save(roleAdmin);


        Role roleMODERATOR = new Role();
        roleMODERATOR.setRoleName("ROLE_MODERATOR");
        roleMODERATOR.setRoleDescription("Default role for newly ROLE_MODERATOR record");
        roleDao.save(roleMODERATOR);

        User adminUser = new User();
        adminUser.setUserName("admin");
        adminUser.setPassword("admin");
        adminUser.setUserFullName("admin");
        adminUser.setEmail("admin@gmail.com");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleAdmin);
        adminUser.setRole(adminRoles);
        userDao.save(adminUser);

    }

    public User registerNewUser(User user) {

        Role role = roleDao.findById("User").get();
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setPassword(user.getPassword());

        return userDao.save(user);
    }

    public User updateUser(User user) {
        Optional<User> optionalUser  = userDao.findById(user.getUserName());
        if (optionalUser.isPresent()){
            Set<Role> userRoles = new HashSet<>();
            userRoles.addAll(optionalUser.get().getRole());
            userRoles.addAll(user.getRole());
            optionalUser.get().setRole(userRoles);
            return userDao.save( optionalUser.get());

        }
        return null;
    }


    public Iterable<User> getAll() {

        return userDao.findAll();
    }





}
