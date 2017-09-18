package iisi.tcd.textMapper.test;


import iisi.tcd.textMapper.util.TextBeanUtil;

public class Test2 {
	public static void main(String[] args) {
		TestBean bean = new TestBean();
		System.out.println(TextBeanUtil.parseString(bean).length());
	}
}
