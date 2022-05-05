package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.StudentController;
import com.example.demo.entity.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.serivce.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentController.class)
public class StudentControllerSaveTest2 {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private StudentService studService;
	
	@MockBean
	private StudentRepository studRepo;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	public void testSaveStudent() throws Exception {
		
		Student s1=new Student(1,"nitin","jkc");
		Student s2=new Student(1,"nitin","jkc");
		
		when(studRepo.save(s2)).thenReturn(s1);

/*		MvcResult andReturn = mockMvc.perform(post("/students/add").contentType("application/json") 
				.content(objectMapper.writeValueAsString(s2))
				.with(csrf())
				).andExpect(status().isOk())
				.andExpect( content().string("1")).andReturn();
				
		
		String actual=andReturn.getResponse().getContentAsString();
		System.out.println("actual: "+actual);
*/
		mockMvc.perform(post("/students/add").contentType("application/json")  
				.content(objectMapper.writeValueAsString(s2))
				.with(csrf())
				).andExpect(status().isOk())
				.andExpect( content().string("1"))
				.andDo(print());
		
		assertEquals(s1, studRepo.save(s2));
		
	}
	
	@Test
	public void notEmptyStudentTest() throws JsonProcessingException, Exception {
		Student s5=new Student();
		
		//ResultActions andDo =
				mockMvc.perform(post("/students/add")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(s5))
				.with(csrf())
				).andExpect(status().isOk())
		.andDo(print());
		
	Mockito.verify(studRepo,times(0)).save(s5);
	}
	
}