package com.edu.bsuir.hotel.hotel.controller;

import com.edu.bsuir.hotel.hotel.converter.UserToUserDTO;
import com.edu.bsuir.hotel.hotel.dto.UserDTO;
import com.edu.bsuir.hotel.hotel.entity.UserEntity;
import com.edu.bsuir.hotel.hotel.service.StorageService;
import com.edu.bsuir.hotel.hotel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private StorageService storageService;

    private UserToUserDTO userConverter = new UserToUserDTO();

    @GetMapping("/login/{login}")
    public UserEntity getUserByLogin(@PathVariable(name = "login") String login){
        return userService.findByLogin(login);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int id){
        Optional<UserEntity> userEntity = userService.findById(id);
        if(userEntity.isPresent()){
            UserDTO userDTO = userConverter.convert(userEntity.get());
            return ResponseEntity.ok(userDTO);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/login/{login}/ex")
    public ResponseEntity<Boolean> ifUserExistsByLogin(@PathVariable String login){
        UserEntity userEntity = userService.findByLogin(login);
        if(userEntity != null){
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/email/{email}/ex")
    public ResponseEntity<Boolean> ifUserExistsByEmail(@PathVariable String email){
        UserEntity userEntity = userService.findByEmail(email);
        if(userEntity != null){
            return ResponseEntity.ok(true);
        }else {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("")
    public ResponseEntity saveNewUser(@RequestBody UserEntity userEntity){
        UserEntity userEntity1 = userService.save(userEntity);
        if(userEntity1 != null){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<UserDTO>> getUsersList(){
        List<UserEntity> userEntities = userService.findAll();
        if(!userEntities.isEmpty()){
            List<UserDTO> userDTOS = userConverter.convert(userEntities);
            return ResponseEntity.ok(userDTOS);
        }else {
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/image")
    public ResponseEntity saveProductImage(@RequestParam("image") MultipartFile file){
        if(storageService.storeUserImage(file)){
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/image/{name}")
    public ResponseEntity<Resource> getProductImage(@PathVariable String name){
        Resource res = storageService.getUserImage(name);
        if(res != null){
            return ResponseEntity.ok(res);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
