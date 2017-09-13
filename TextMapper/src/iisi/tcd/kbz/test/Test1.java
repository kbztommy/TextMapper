package iisi.tcd.kbz.test;

import iisi.tcd.kbz.util.TextBeanUtil;

public class Test1 {
	public static void main(String[] args) {
		TextBeanUtil.parseBean("0123456789", TestBean.class);
	}
}
