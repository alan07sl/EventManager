package com.utn.tacs.eventmanager.controllers;

import com.utn.tacs.eventmanager.controllers.dto.ListDTO;
import com.utn.tacs.eventmanager.controllers.dto.UserDTO;
import com.utn.tacs.eventmanager.controllers.dto.UserStatsDTO;
import com.utn.tacs.eventmanager.dao.User;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.repositories.UserRepository;
import com.utn.tacs.eventmanager.security.JWTRepository;
import com.utn.tacs.eventmanager.services.UserService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;

import static com.utn.tacs.eventmanager.security.SecurityConstants.HEADER_STRING;
import static com.utn.tacs.eventmanager.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private MapperFacade orikaMapper;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDTO user) throws CustomException {
        User newUser = orikaMapper.map(user, User.class);
        userService.createUser(newUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ListDTO<UserDTO>> getUsers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size ) {

        Page<User> result = userService.searchPaginated(new User(name, null), page, size);

        ListDTO<UserDTO> list = new ListDTO<>();
        list.setPageNumber(page);
        list.setPageCount(result.getTotalPages());
        list.setResultCount(result.getTotalElements());
        list.setResult(result.getContent().stream().map((User u) -> { u.setPassword(null); return orikaMapper.map(u, UserDTO.class); }).collect(Collectors.toList()));
        list.setNext(result.hasNext() ? "/users?page="+ (list.getPageNumber() + 1) + "&name=" + name + "&size=" + size : null);
        list.setPrev(list.getPageNumber() > 1 ? "/users?page="+ (list.getPageNumber() - 1) + "&name=" + name + "&size=" + size : null);

        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStatsDTO> getById(@PathVariable Integer id) throws CustomException {
        UserStatsDTO userStats = new UserStatsDTO();
        User user = userService.findById(id);
        userStats.setUsername(user.getUsername());
        userStats.setAlarms(user.getAlarms().size());
        userStats.setEventsLists(user.getEventsLists().size());
        userStats.setLastLogin(user.getLastLogin());
        return new ResponseEntity<>(userStats,HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING).replace(TOKEN_PREFIX, "");
        JWTRepository.getInstance().removeToken(token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}