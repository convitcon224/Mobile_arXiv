package vn.edu.usth.arxiv;

import java.time.LocalDate;
import java.util.ArrayList;

public class mDocument {
	private String id;
	private String title;
	private String content;
	private ArrayList<String> authors = new ArrayList<String>();
	private LocalDate date;

//	public mDocument() {}

//	public mDocument(String id, String title, String content, String author,String date) {
//		super();
//		this.id = id;
//		this.title = title;
//		this.content = content;
//		this.author = author;
//		this.date = dateConverter(date);
//	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id.replace("\n"," ");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title.replace("\n"," ");
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content.replace("\n"," ");
	}

	public String getAuthors() {
		return authors.toString();
	}

	public void addAuthor(String author) {
		this.authors.add(author);
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = dateConverter(date);
	}

	public LocalDate dateConverter(String d){
		return LocalDate.parse(d.substring(0,10));
	}



}