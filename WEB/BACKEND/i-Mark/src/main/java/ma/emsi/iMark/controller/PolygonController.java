package ma.emsi.iMark.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ma.emsi.iMark.model.Point;
import ma.emsi.iMark.model.Polygon;
import ma.emsi.iMark.repository.PolygonRepository;
import ma.emsi.iMark.service.SequenceGeneratorService;

@RestController
@RequestMapping("/polygons")
public class PolygonController {

	@Autowired
	private PolygonRepository polygonRepository;
	
	@Autowired
	private PointController pointController;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping
	public List<Polygon> getAll(){
		return polygonRepository.findAll();
	}
	
	@PostMapping
	public void addPolygon(@RequestBody Polygon polygon) {
		polygon.setId(sequenceGeneratorService.getSequenceNumber(Polygon.SEQUENCE_NAME));
		
		List<Point> p=polygon.getCoordonnees();
		
		for (Point point : p) {
			pointController.addPoint(point);
		}
		
		polygonRepository.save(polygon);
	}
	
	@GetMapping("/{id}")
	public Polygon getPolygon(@PathVariable int id) {
		return polygonRepository.findById(id).get();
	}
	
	@PutMapping
	public void updatePolygon(@RequestBody Polygon polygon) {
		Polygon i=polygonRepository.findById(polygon.getId()).get();
		if(polygon!=i) {
			i.setCoordonnees(polygon.getCoordonnees());
			polygonRepository.save(i);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deletePolygon(@PathVariable int id) {
		polygonRepository.deleteById(id);
	}
}
