package com.zbf.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zbf.example.dao.Person;
import com.zbf.example.dao.PersonDao;

@RestController
public class DataController {

	@Autowired
	PersonDao personDao;
	
	@RequestMapping("/set")
	public void set(){
		Person person = new Person("1","wyf",32);
		personDao.save(person);
		personDao.stringRedisTemplateDemo();
	}
	
	
	
	@RequestMapping("/getStr")
	public String getStr(){
		return personDao.getString();
	}
	
	@RequestMapping("/getPerson")
	public Person getPerson(){
		return personDao.getPerson();
	}
	
	
	@RequestMapping("/delete")
	public void removePerson(){
		personDao.removePerson();
	}
	
	
	
}
