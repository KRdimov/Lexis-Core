package com.getlexis.types;

public class Template {
	private String content;
	
	public Template(String content) {
		setContent(content);
	}

	public void setContent(String content) {
		this.content = content;
	}
	public String getContent() {
		return content;
	}	
}
