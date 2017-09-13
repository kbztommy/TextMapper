package iisi.tcd.kbz.test;

import iisi.tcd.kbz.annotation.TextBean;
import iisi.tcd.kbz.annotation.TextMapper;

@TextBean
public class TestBean {
	
	@TextMapper(length = 10)
	private int id;
	
	@TextMapper(length = 10)
	private String name;
	
}
