package com.garima.fitnesscentre.controllers;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.garima.fitnesscentre.models.Data.Page;

import jakarta.validation.Valid;

import com.garima.fitnesscentre.models.PageRepository;
import java.util.List;
@Controller
@RequestMapping("/admin/pages")
public class AdminPagesController {
  
    @Autowired
    private PageRepository pageRepo;
     // upar autowire laga diya to likhne ki zaroorat ni h
    // public AdminPagesController(PageRepository pageRepo)
    // {  
    //     this.pageRepo = pageRepo;
    // }

    @GetMapping
    public String index(Model model)
    {
        List<Page> pages = pageRepo.findAllByOrderBySortingAsc();
        model.addAttribute("pages",pages);

        return "admin/pages/index";
    }
    @GetMapping("/add")
    public String add(@ModelAttribute Page page)
    {
      //  model.addAttribute("page",new Page());
        return "admin/pages/add";
    }
    @PostMapping("/add")
    public  String add(@Valid Page page,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model)
    {  
        if(bindingResult.hasErrors())
        {
            return "admin/pages/add";
        }

        redirectAttributes.addFlashAttribute("message", "page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ","-");
        Page slugExists = pageRepo.findBySlug(slug);

        if(slugExists != null)
        {
            redirectAttributes.addFlashAttribute("message","Slug exists,choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);

        }
        else
        {
            page.setSlug(slug);
            page.setSorting(100);

            pageRepo.save(page);
        }
       return "redirect:/admin/pages/add";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id,Model model)
    {
        Page page = pageRepo.getReferenceById(id);
        model.addAttribute("page",page);
        return "admin/pages/edit";
    }
    @PostMapping("/edit")
    public  String edit(@Valid Page page,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model)
    {  
        Page pageCurrent = pageRepo.getReferenceById(page.getId());
        if(bindingResult.hasErrors())
        { model.addAttribute("pageTitle",pageCurrent.getTitle());
            return "admin/pages/edit";
        }

        redirectAttributes.addFlashAttribute("message", "page edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = page.getSlug() == "" ? page.getTitle().toLowerCase().replace(" ", "-") : page.getSlug().toLowerCase().replace(" ","-");
        Page slugExists = pageRepo.findBySlug(page.getId(),slug);

        if(slugExists != null)
        {
            redirectAttributes.addFlashAttribute("message","Slug exists,choose another");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("page", page);

        }
        else
        {
            page.setSlug(slug);
            // page.setSorting(100);

            pageRepo.save(page);
        }
       return "redirect:/admin/pages/edit/" + page.getId();
    }
     @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,RedirectAttributes redirectAttributes )
    {
        pageRepo.deleteById(id);
         redirectAttributes.addFlashAttribute("message","Page deleted");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/admin/pages";
    }
    @PostMapping("/reorder")
    public @ResponseBody String reorder(@RequestParam("id[]") int[] id)
    {
     int count =1;
     Page page;
     for(int pageId : id)
     {
        page = pageRepo.getReferenceById(pageId);
        page.setSorting(count);
        pageRepo.save(page);
        count++;
     }
     return "ok";

    }
    
}
