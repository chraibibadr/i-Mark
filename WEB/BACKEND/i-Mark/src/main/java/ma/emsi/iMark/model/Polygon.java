package ma.emsi.iMark.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="polygons")
public class Polygon {

	@Transient
	public static final String SEQUENCE_NAME="polygon_sequence";
	
	
	@Id
	private int id;
	
	private List<Point> coordonnees;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Point> getCoordonnees() {
		return coordonnees;
	}
	public void setCoordonnees(List<Point> coordonnees) {
		this.coordonnees = coordonnees;
	}
	
	
	
	
}
