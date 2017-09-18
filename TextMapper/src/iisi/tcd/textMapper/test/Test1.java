package iisi.tcd.textMapper.test;

import iisi.tcd.textMapper.util.TextBeanUtil;

public class Test1 {
	public static void main(String[] args) {
		TestBean bean = TextBeanUtil.parseBean("01234502abcdef", TestBean.class);
		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getCount());
		for (TestBeanDetail detail : bean.getDetails()) {
			System.out.println(detail.getNote());
		}
	}
}
