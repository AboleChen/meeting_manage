package com.canway.manager.controller;

import com.canway.manager.pojo.User;
import com.canway.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;


@CrossOrigin
@Controller
@RequestMapping("/user")
public class UseController {

    public  static String person;

    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/search")
    public Object search(@RequestParam(value = "name") String name, int page_index, int page_size) {
        return userService.search(name, page_index, page_size);
    }

    @ResponseBody
    @GetMapping("/get_user")
    public Object get_user() {
        String name = person;
        return userService.get_user(name);
    }

    @ResponseBody
    @PostMapping("/add")
    public Object add(@RequestBody User user) {
        return userService.add(user);
    }

    @ResponseBody
    @PostMapping("/update")
    public Object update(@RequestBody User user) {
        return userService.update(user);
    }

    @ResponseBody
    @GetMapping("/delete")
    public Object delete(@RequestParam(value = "id") int id) {
        return userService.delete(id);
    }

    @PostMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("password")String password, Map<String, Object>map, HttpSession session) {

        person = name;
        String str = "";
        if (userService.login(name, password)) {
            session.setAttribute("user", name);
            str = "redirect:/index.html";
        } else {
            str = "redirect:/login.html";
        }
        return str;
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/login.html";
    }
}
