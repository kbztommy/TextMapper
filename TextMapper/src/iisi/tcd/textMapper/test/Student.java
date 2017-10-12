package iisi.tcd.textMapper.test;

import iisi.tcd.textMapper.annotation.DelimitedTextBean;
import iisi.tcd.textMapper.annotation.TextMapper;

@DelimitedTextBean
public class Student {

	@TextMapper
	private int id;

	@TextMapper
	private String name;

	@TextMapper
	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

}
