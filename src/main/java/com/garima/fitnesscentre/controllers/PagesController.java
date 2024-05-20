package com.garima.fitnesscentre.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garima.fitnesscentre.models.CourseRepository;
import com.garima.fitnesscentre.models.PageRepository;
import com.garima.fitnesscentre.models.UserRepository;
import com.garima.fitnesscentre.models.Data.Course;
import com.garima.fitnesscentre.models.Data.Page;
import com.garima.fitnesscentre.models.Data.User;


@Controller
@RequestMapping("/")
public class PagesController 
{
    @Autowired
    private PageRepository pageRepo;
    @Autowired
    private CourseRepository courseRepo;
     @Autowired
    private UserRepository userRepo;
    @GetMapping
    public String home(Model model)
    {
        Page page = pageRepo.findBySlug("home");
       model.addAttribute("page",page);

        return "home";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/{slug}")
    public String page(@PathVariable String slug, Model model) {
        
        Page page = pageRepo.findBySlug(slug);

        if(page == null) {
            return "redirect:/";
        }
        if(slug.equals("courses"))
        {
            List<Course> courses = courseRepo.findAll();

            model.addAttribute("courses", courses);
            return "courses";

        }
        if(slug.equals("profile"))
        {
            List<User> users = userRepo.findAll();
            model.addAttribute("users", users);
            return "profile";

        }
        model.addAttribute("page", page);
        return slug;
        // Page page = pageRepo.findBySlug(slug);
        
        // if (page == null) {
        //     return "redirect:/";
        // }
        // else if(slug.equals("about"))
        // {
        //     return "about_us";
        // }
        
        // model.addAttribute("page", page);
        
        // return "page";
    }
    
}
