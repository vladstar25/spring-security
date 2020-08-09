package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import web.model.Role;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "hello", method = GET)
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("Welcome!!!");
        model.addAttribute("messages", messages);
        return "hello";
    }

    @RequestMapping(value = "login", method = GET)
    public String loginPage() {
        return "login";
    }


    @RequestMapping(value = "panel", method = GET)
    public ModelAndView allUsers() {
		List<User> listUser = userService.allUsers();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user");
		modelAndView.addObject("listUsers", listUser);
		return modelAndView;
	}
    @RequestMapping(value = "add", method = GET)
    public String addPage() {
        return "add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addUser(@RequestParam(name = "login") String login,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "name") String name,
                          @RequestParam(name = "age") int age) {
       User user = new User(name, age, login, passwordEncoder.encode(password));
       Set<Role> set = new HashSet<>();
       set.add(Role.ROLE_ADMIN);
       user.setRole(set);
       userService.add(user);
       return "redirect:/panel";
    }

    @RequestMapping(value = "edit", method = GET)
    @ResponseBody
    public ModelAndView edit(@RequestParam("id") int id) {
        User user = userService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editpage");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    @RequestMapping(value = "edit", method = RequestMethod.POST)
	public String editUser(@ModelAttribute("user") User user){
		userService.edit(user);
		return "redirect:/panel";
	}

    @RequestMapping(value = "delete", method = GET)
    @ResponseBody
	public ModelAndView deleteUser(@RequestParam("id") int id) {
		User user = userService.getById(id);
		userService.delete(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/panel");
		return modelAndView;
	}
}
