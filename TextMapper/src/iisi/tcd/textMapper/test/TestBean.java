package iisi.tcd.textMapper.test;

import java.util.List;

import iisi.tcd.textMapper.annotation.TextBean;
import iisi.tcd.textMapper.annotation.TextMapper;

@TextBean
public class TestBean {

	@TextMapper(length = 3)
	private Integer id;

	@TextMapper(length = 3)
	private String name;

	@TextMapper(length = 2)
	private int count;

	@TextMapper(length = 3, repeat = "count")
	private List<TestBeanDetail> details;

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<TestBeanDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TestBeanDetail> details) {
		this.details = details;
	}

}