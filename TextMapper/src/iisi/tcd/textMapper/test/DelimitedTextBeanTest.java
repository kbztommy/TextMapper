package iisi.tcd.textMapper.test;

import iisi.tcd.textMapper.util.TextBeanUtil;

public class DelimitedTextBeanTest {

	public static void main(String[] args) {
		String text = "894957,Tommy,18";

		Student student = TextBeanUtil.parseBean(text, Student.class);

		System.out.println("id : " + student.getId());
		System.out.println("name : " + student.getName());
		System.out.println("age : " + student.getAge());

		String text2 = TextBeanUtil.parseString(student);

		System.out.println("---------------------");

		System.out.println(text);
		System.out.println(text2);
		System.out.println("is Equal :" + text.equals(text2));
	}

}
