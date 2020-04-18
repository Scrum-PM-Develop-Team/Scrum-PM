package edu.whu.ScrumPM.controller;

import edu.whu.ScrumPM.dao.user.User;
import edu.whu.ScrumPM.model.AjaxResponse;
import edu.whu.ScrumPM.service.user.UserRestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Controller
@RequestMapping("/rest")
public class UserRestController {
    @Resource(name="userRestJPAServiceImpl")
    UserRestService userRestService;

    @PostMapping("/user")
    public @ResponseBody AjaxResponse saveUser(@RequestBody User user) {

        log.info("saveUser：{}",user);
        try {
            User successUser= userRestService.saveUser(user);
            return AjaxResponse.success(successUser);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

    @DeleteMapping("/user/{id}")
    public @ResponseBody AjaxResponse deleteUser(@PathVariable Long id) {

        log.info("deleteUser：{}",id);
        try {
            userRestService.deleteUser(id);
            return AjaxResponse.success(id);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

    @PutMapping("/user")
    public @ResponseBody AjaxResponse updateUser(@RequestBody User user) {

        log.info("updateUser：{}",user);
        try{
            userRestService.updateUser(user);
            return AjaxResponse.success(user);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }

    @GetMapping( "/user")
    public @ResponseBody AjaxResponse getUserByName(@RequestParam String userName) {
        try{
            User user= userRestService.getUser(userName);
            return AjaxResponse.success(user);
        }catch (Exception e){
            return AjaxResponse.fail(e);
        }
    }
}
