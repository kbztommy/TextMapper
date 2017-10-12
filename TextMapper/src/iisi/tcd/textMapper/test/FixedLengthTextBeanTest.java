package iisi.tcd.textMapper.test;

import iisi.tcd.textMapper.util.TextBeanUtil;

public class FixedLengthTextBeanTest {
	public static void main(String[] args) {
		String text = "AS14503503493849385860384602AA54039460000300BB49305960001000001300";

		Order order = TextBeanUtil.parseBean(text, Order.class);
		System.out.println("id : " + order.getId());
		System.out.println("sellerId : " + order.getSellerId());
		System.out.println("buyerId : " + order.getBuyerId());
		System.out.println("productCount : " + order.getProductCount());
		System.out.println("totalAmount : " + order.getTotalAmount());

		for (Product product : order.getDetails()) {
			System.out.println("---------------------");
			System.out.println("id : " + product.getId());
			System.out.println("amount : " + product.getAmount());
		}

		System.out.println("---------------------");
		String text2 = TextBeanUtil.parseString(order);
		System.out.println(text);
		System.out.println(text2);
		System.out.println("is equal : " + text.equals(text2));
	}
}
