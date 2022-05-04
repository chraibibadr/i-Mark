package ma.emsi.iMark.model;

import java.util.Date;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="image")
public class Image {
	
	@Id
	private int id;
	
	private String file_url;
	
	private float width;
	private float height;
	private Date date_captured;
	
	private GpsLocation gps_location;
	
	private List<ObjectB> objects;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Date getDate_captured() {
		return date_captured;
	}

	public void setDate_captured(Date date_captured) {
		this.date_captured = date_captured;
	}

	public GpsLocation getGps_location() {
		return gps_location;
	}

	public void setGps_location(GpsLocation gps_location) {
		this.gps_location = gps_location;
	}

	public List<ObjectB> getObjects() {
		return objects;
	}

	public void setObjects(List<ObjectB> objects) {
		this.objects = objects;
	}
	
	
	
	
	
	

}
