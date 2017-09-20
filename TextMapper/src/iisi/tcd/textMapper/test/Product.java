package iisi.tcd.textMapper.test;

import iisi.tcd.textMapper.annotation.TextBean;
import iisi.tcd.textMapper.annotation.TextMapper;

@TextBean
public class Product {
	@TextMapper(length = 10)
	private String id;
	@TextMapper(length = 6, paddingWord = '0')
	private int amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
