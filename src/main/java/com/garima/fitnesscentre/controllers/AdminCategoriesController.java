package com.garima.fitnesscentre.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.garima.fitnesscentre.models.CategoryRepository;
import com.garima.fitnesscentre.models.Data.Category;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoriesController 
{
    @Autowired
    private CategoryRepository categoryRepo;


    @GetMapping
    public String index(Model model)
    {
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);

        return "admin/categories/index";

    }
    // if we don't want to pass category in each method of controller then
    // this category in model attribute is connected to th:object category in add.html page
    // @ModelAttribute("category")
    // public Category getCategory()
    // {
    //     return new Category();
    // }

    @GetMapping("/add")
    public String add(Category category)
    {
        return "admin/categories/add";
    }

     @PostMapping("/add")
    public  String add(@Valid Category category,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model)
    {  
        if(bindingResult.hasErrors())
        {
            return "admin/categories/add";
        }

        redirectAttributes.addFlashAttribute("message", "category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug =  category.getName().toLowerCase().replace(" ", "-");
        Category categoryExists = categoryRepo.findByName(category.getName());

        if(categoryExists != null)
        {
            redirectAttributes.addFlashAttribute("message","Category exists,choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            // to have what was types in there still in next page too when category exists wala error
            redirectAttributes.addFlashAttribute("categoryInfo", category);

        }
        else
        {
            category.setSlug(slug);
           

            categoryRepo.save(category);
        }
       return "redirect:/admin/categories/add";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id,Model model)
    {
        Category category = categoryRepo.getReferenceById(id);
        model.addAttribute("category",category);
        return "admin/categories/edit";
    }
    @PostMapping("/edit")
    public  String edit(@Valid Category category,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model)
    {  
        Category categoryCurrent = categoryRepo.getReferenceById(category.getId());
        if(bindingResult.hasErrors())
        { model.addAttribute("categoryName",categoryCurrent.getName());
            return "admin/categories/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Category edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = category.getName().toLowerCase().replace(" ", "-") ;
        Category categoryExists = categoryRepo.findByName(category.getName());

        if(categoryExists != null)
        {
            redirectAttributes.addFlashAttribute("message","Name exists,choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            

        }
        else
        {
            category.setSlug(slug);
            // page.setSorting(100);

            categoryRepo.save(category);
        }
       return "redirect:/admin/categories/edit/" + category.getId();
    }
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,RedirectAttributes redirectAttributes )
    {
        categoryRepo.deleteById(id);
         redirectAttributes.addFlashAttribute("message","Category deleted");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/admin/categories";
    }

}
