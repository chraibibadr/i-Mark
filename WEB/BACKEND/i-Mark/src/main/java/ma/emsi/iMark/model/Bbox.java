package ma.emsi.iMark.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="bbox")
public class Bbox {

	@Transient
	public static final String SEQUENCE_NAME="bbox_sequence";
	
	
	@Id
	private int id;
	
	private float xmin;
	private float ymin;
	private float xmax;
	private float ymax;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getXmin() {
		return xmin;
	}
	public void setXmin(float xmin) {
		this.xmin = xmin;
	}
	public float getYmin() {
		return ymin;
	}
	public void setYmin(float ymin) {
		this.ymin = ymin;
	}
	public float getXmax() {
		return xmax;
	}
	public void setXmax(float xmax) {
		this.xmax = xmax;
	}
	public float getYmax() {
		return ymax;
	}
	public void setYmax(float ymax) {
		this.ymax = ymax;
	}
	
	
}
