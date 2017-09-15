package iisi.tcd.kbz.test;

import iisi.tcd.kbz.annotation.TextBean;
import iisi.tcd.kbz.annotation.TextMapper;

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
