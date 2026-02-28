package com.example.studentmanagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.studentmanagement.Student; 
import com.example.studentmanagement.StudentRepository;
import java.util.List;

@Service
public class StudentService {
  @Autowired
  private StudentRepository repo;
  
  
//Fetch all students
  public List<Student> getAllStudents(){
	   return repo.findAll();
	   }
//Save or Update a student
   public void savestudent(Student s) {
	     repo.save(s);
       }
   
   public Student getStudentById(Long id) {
       return repo.findById(id).orElseThrow(() -> 
           new IllegalArgumentException("Invalid student Id:" + id));
   }
    public void deleteStudent(Long id) {
    	 repo.deleteById(id);
    }
   
}
