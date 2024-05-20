package com.garima.fitnesscentre.controllers;

import java.util.HashMap;
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

import com.garima.fitnesscentre.models.CentreRepository;

import com.garima.fitnesscentre.models.TrainerRepository;

import com.garima.fitnesscentre.models.Data.Centre;

import com.garima.fitnesscentre.models.Data.Trainer;

import jakarta.validation.Valid;

@Controller

@RequestMapping("/admin/trainers")
public class AdminTrainersController {

    @Autowired
    private TrainerRepository trainerRepo;

    @Autowired
    private CentreRepository centreRepo;

    @GetMapping
    public String index(Model model)
    {    
        List<Trainer> trainers = trainerRepo.findAll();
       
        List<Centre> centres = centreRepo.findAll();
       
         HashMap<Integer,String> cents = new HashMap<>();
        for(Centre cent : centres){
            cents.put(cent.getId(),cent.getCity());
        }
         model.addAttribute("cents", cents);
          model.addAttribute("trainers", trainers);
        return "admin/trainers/index";
    }

    @GetMapping("/add")
    public String add(Trainer trainer, Model model)
    {
        List<Centre> centres = centreRepo.findAll();
        model.addAttribute("centres", centres);
        return "admin/trainers/add";
    }

    @PostMapping("/add")
    public String add(@Valid Trainer trainer, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "admin/trainers/add";
        }
        redirectAttributes.addFlashAttribute("message", "Trainer added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");



        trainerRepo.save(trainer);

    

        return "redirect:/admin/trainers/add";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id,Model model)
    {
        Trainer trainer = trainerRepo.getReferenceById(id);
        model.addAttribute("trainer",trainer);
        List<Centre> centres = centreRepo.findAll();
        model.addAttribute("centres", centres);
        return "admin/trainers/edit";
    }
     @PostMapping("/edit")
    public  String edit(@Valid Trainer trainer,BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model)
    {  
        Trainer trainerCurr = trainerRepo.getReferenceById(trainer.getId());
        List<Centre> centres = centreRepo.findAll();
        if(bindingResult.hasErrors())
        { 
            model.addAttribute("trainername",trainerCurr.getName());
            model.addAttribute("centres",centres);
            return "admin/trainers/edit";
        }

        redirectAttributes.addFlashAttribute("message", "Trainer edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
         
         trainerRepo.save(trainer);

       
       return "redirect:/admin/trainers/edit/" + trainer.getId();
    }
     @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id,RedirectAttributes redirectAttributes )
    {
        trainerRepo.deleteById(id);
         redirectAttributes.addFlashAttribute("message","Trainer deleted");
            redirectAttributes.addFlashAttribute("alertClass", "alert-success");
            
        return "redirect:/admin/trainers";
    }
    



    
}