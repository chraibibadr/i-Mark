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
import ma.emsi.iMark.repository.PointRepository;
import ma.emsi.iMark.service.SequenceGeneratorService;

@RestController
@RequestMapping("/points")
public class PointController {

	@Autowired
	private PointRepository pointRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping
	public List<Point> getAll(){
		return pointRepository.findAll();
	}
	
	@PostMapping
	public void addPoint(@RequestBody Point point) {
		point.setId(sequenceGeneratorService.getSequenceNumber(Point.SEQUENCE_NAME));
		pointRepository.save(point);
	}
	
	@GetMapping("/{id}")
	public Point getPoint(@PathVariable int id) {
		return pointRepository.findById(id).get();
	}
	
	@PutMapping
	public void updatePoint(@RequestBody Point point) {
		Point i=pointRepository.findById(point.getId()).get();
		if(point!=i) {
			i.setX(point.getX());
			i.setY(point.getY());
			pointRepository.save(i);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deletePoint(@PathVariable int id) {
		pointRepository.deleteById(id);
	}
}
