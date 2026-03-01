package com.example.studentmanagement;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentController {
    @Autowired
    private StudentService service;
    
    
    @GetMapping("/")
   public String start() {
    	  return "redirect:/login";
    }
    //1.SHOW LOGIN PAGE
    @GetMapping("/login")
    public String loginPage() {
   	  return "login";
    }
    //2.PROCESS LOGIN FROM
    @PostMapping("/login")
    public String checkLogin(@RequestParam String username, @RequestParam String password, Model model) {
        if ("admin".equals(username) && "123".equals(password)) {
            // Change this line! It must go to /students
            return "redirect:/students"; 
        } else {
            model.addAttribute("error", true);
            return "login";
        }
    }
//3.SHOW STUDENT LIST
    @GetMapping("/students")
    public String home(Model model) {
        model.addAttribute(""
        		+ "listStudents", service.getAllStudents());
        return "index";
    }
    
    //ADD FOR EDIT MAPPING
     @GetMapping("/edit/{id}")
     public String showEdit(@PathVariable("id") Long id ,Model model){
    	   Student student =service.getStudentById(id);
    	    model.addAttribute("student",student);
    	    return "edit-student";
     }
        
     @GetMapping("/delete/{id}")
       public String deleteStudent(@PathVariable("id") Long id,RedirectAttributes ra ) {
    	    service.deleteStudent(id);
    	    ra.addFlashAttribute("message","Student deleted successfully!");
    	    return "redirect:/students";//This line sends the browser back to the main list;
     }
    

     
    
    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("student") Student s , BindingResult result,Model model, RedirectAttributes ra) {
        
    	    if(result.hasErrors()) {
    	    	model.addAttribute("listStudents",service.getAllStudents());
    	    }
    	
    	
        service.savestudent(s);
        String message =(s.getId()== null)?"Student registered successfully!": "Student records updated";
        ra.addFlashAttribute("message", message);
        return "redirect:/students";
    }
}