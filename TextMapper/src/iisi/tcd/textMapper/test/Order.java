package iisi.tcd.textMapper.test;

import java.util.List;

import iisi.tcd.textMapper.annotation.FixedLengthTextBean;
import iisi.tcd.textMapper.annotation.TextMapper;
@FixedLengthTextBean
public class Order {
	@TextMapper(length = 10)
	private String id;
	@TextMapper(length = 8)
	private String sellerId;
	@TextMapper(length = 8)
	private String buyerId;
	@TextMapper(length = 2, paddingWord = '0')
	private int productCount;
	@TextMapper(length = 16, repeat = "productCount")
	private List<Product> details;
	@TextMapper(length = 6, paddingWord = '0')
	private int totalAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public List<Product> getDetails() {
		return details;
	}

	public void setDetails(List<Product> details) {
		this.details = details;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

}
