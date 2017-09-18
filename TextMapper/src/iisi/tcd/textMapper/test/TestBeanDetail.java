package iisi.tcd.textMapper.test;

import iisi.tcd.textMapper.annotation.TextBean;
import iisi.tcd.textMapper.annotation.TextMapper;

@TextBean
public class TestBeanDetail {
	@TextMapper(length = 3)
	private String note;

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}
