package com.garima.fitnesscentre.controllers;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.garima.fitnesscentre.models.CategoryRepository;
import com.garima.fitnesscentre.models.CourseRepository;
import com.garima.fitnesscentre.models.TrainerRepository;
import com.garima.fitnesscentre.models.Data.Category;
import com.garima.fitnesscentre.models.Data.Course;

import com.garima.fitnesscentre.models.Data.Trainer;

import jakarta.validation.Valid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/admin/courses")
public class AdminCoursesController 
{
    @Autowired
    private CourseRepository courseRepo;
     @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private TrainerRepository trainerRepo;
    @GetMapping
    public String index(Model model,@RequestParam(value="page", required = false) Integer p)
    {   int perPage = 5;
        int page = (p != null) ? p : 0;
        
        // Pageable pageable = PageRequest.of(page, perPage);
        
        List<Course> courses =  courseRepo.findAll();
         List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();
        
        
         HashMap<Integer,String> cats = new HashMap<>();
        for(Category cat : categories){
            cats.put(cat.getId(),cat.getName());
        }
        HashMap<Integer,String> tras = new HashMap<>();
        for(Trainer tra : trainers){
            tras.put(tra.getId(),tra.getName());

        }
        model.addAttribute("courses", courses);
         model.addAttribute("cats", cats);
          model.addAttribute("tras", tras);
       //Long count = courseRepo.count();
      //  double pageCount = Math.ceil((double)count / (double)perPage);
       // model.addAttribute("pageCount", (int)pageCount);
       // model.addAttribute("count", count);
        model.addAttribute("perPage", perPage);
        model.addAttribute("page", page);
        
        return "admin/courses/index";
        

    }
     @GetMapping("/add")
    public String add(@ModelAttribute Course course,Model model)
    {   List<Category> categories = categoryRepo.findAll();
        List<Trainer> trainers = trainerRepo.findAll();
       model.addAttribute("trainers",trainers);
        model.addAttribute("categories",categories);
       
        return "admin/courses/add";
    }
     @PostMapping("/add")
    public  String add(@Valid Course course,BindingResult bindingResult,MultipartFile file,RedirectAttributes redirectAttributes,Model model) throws IOException
    {  
        List<Category> categories = categoryRepo.findAll();
          List<Trainer> trainers = trainerRepo.findAll();
        if(bindingResult.hasErrors())
        {    model.addAttribute("categories",categories);
         model.addAttribute("trainers",trainers);
        
            return "admin/courses/add";
        }
        boolean fileOk = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename );
        if(filename.endsWith("jpg") || filename.endsWith("png"))
        {
            fileOk=true;
        }


        
        redirectAttributes.addFlashAttribute("message", "Course added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = course.getName().toLowerCase().replace(" ","-");
        String startDateStr = course.getStartDate() ;
        int durationMonths = course.getDuration();
        Course slugExists = courseRepo.findBySlug(course.getId(),slug,course.getStartDate() ,course.getDuration());
        if(!fileOk)
        {
            redirectAttributes.addFlashAttribute("message","Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("course", course);

        }
        else if(slugExists != null)
        {
            redirectAttributes.addFlashAttribute("message","Course exists,choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            //redirectAttributes.addFlashAttribute("page", page);

        }
        else
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
   
        // Parse the start date string into a LocalDate object
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);

        // Calculate the end date by adding the specified duration in months
        LocalDate endDate = startDate.plusMonths(durationMonths);

        // Format the end date as a string in the same format as the start date
        String endDateStr = endDate.format(formatter);

        
        course.setEndDate(endDateStr);
            course.setSlug(slug);
           course.setImage(filename);

            courseRepo.save(course);
            Files.write(path,bytes);
        }
        
        

       return "redirect:/admin/courses/add";
    }
     @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id,Model model)
    {
        Course course = courseRepo.getReferenceById(id);
         List<Category> categories = categoryRepo.findAll();
          List<Trainer> trainers = trainerRepo.findAll();
           model.addAttribute("categories",categories);
         model.addAttribute("trainers",trainers);
        model.addAttribute("course",course);
        return "admin/courses/edit";
    }
     @PostMapping("/edit")
    public  String edit(@Valid Course course,BindingResult bindingResult,MultipartFile file,RedirectAttributes redirectAttributes,Model model) throws IOException
    {   Course courseCurr = courseRepo.getReferenceById(course.getId());

        List<Category> categories = categoryRepo.findAll();
          List<Trainer> trainers = trainerRepo.findAll();
        if(bindingResult.hasErrors())
        {    model.addAttribute("coursename", courseCurr.getName());
             model.addAttribute("categories",categories);
         
             model.addAttribute("trainers",trainers);

            return "admin/courses/edit";
        }
        boolean fileOk = false;
        byte[] bytes = file.getBytes();
        String filename = file.getOriginalFilename();
        Path path = Paths.get("src/main/resources/static/media/" + filename );
        if(!file.isEmpty())
        {
            if(filename.endsWith("jpg") || filename.endsWith("png"))
        {
            fileOk=true;
        }
        }
        else
        {
            fileOk = true;
        }
        


        
        redirectAttributes.addFlashAttribute("message", "Course edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = course.getName().toLowerCase().replace(" ","-");
        String startDateStr = course.getStartDate() ;
        int durationMonths = course.getDuration();
        Course slugExists = courseRepo.findBySlug(course.getId(),slug,course.getStartDate() ,course.getDuration());
        if(!fileOk)
        {
            redirectAttributes.addFlashAttribute("message","Image must be a jpg or a png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("course", course);

        }
        else if(slugExists != null)
        {
            redirectAttributes.addFlashAttribute("message","Course exists,choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            //redirectAttributes.addFlashAttribute("page", page);

        }
        else
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
   
        // Parse the start date string into a LocalDate object
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);

        // Calculate the end date by adding the specified duration in months
        LocalDate endDate = startDate.plusMonths(durationMonths);

        // Format the end date as a string in the same format as the start date
        String endDateStr = endDate.format(formatter);

        
        course.setEndDate(endDateStr);
            course.setSlug(slug);
            if(!file.isEmpty())
            {
                Path path2 = Paths.get("src/main/resources/static/media/" + courseCurr.getImage() );
                Files.delete(path2);
                course.setImage(filename);
                 Files.write(path,bytes);

            }
            else
            {
                course.setImage(courseCurr.getImage());
            }
           

            courseRepo.save(course);
            
        }
        
        

       return "redirect:/admin/courses/edit/" + course.getId();
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,RedirectAttributes redirectAttributes ) throws IOException
    {    Course course =courseRepo.getReferenceById(id);
        Course courseCurr = courseRepo.getReferenceById(course.getId());
        Path path2 = Paths.get("src/main/resources/static/media/" + courseCurr.getImage() );
                Files.delete(path2);
        courseRepo.deleteById(id);
         redirectAttributes.addFlashAttribute("message","Course deleted");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            //delete the course
        return "redirect:/admin/courses";
    }

}
