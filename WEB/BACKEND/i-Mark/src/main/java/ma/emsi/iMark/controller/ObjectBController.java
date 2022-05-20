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

import ma.emsi.iMark.repository.ObjectBRepository;
import ma.emsi.iMark.service.SequenceGeneratorService;
import ma.emsi.iMark.model.ObjectB;
import ma.emsi.iMark.model.Polygon;

@RestController
@RequestMapping("/objects")
public class ObjectBController {

	@Autowired
	private ObjectBRepository objectRepository;
	
	@Autowired
	private PolygonController polygonController;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping
	public List<ObjectB> getAll(){
		return objectRepository.findAll();
	}
	
	@PostMapping
	public void addObject(@RequestBody ObjectB object) {
		object.setId(sequenceGeneratorService.getSequenceNumber(ObjectB.SEQUENCE_NAME));
		
		List<Polygon> p=object.getPolygons();
		for (Polygon polygon : p) {
			polygonController.addPolygon(polygon);
		}
		
		objectRepository.save(object);
	}
	
	@GetMapping("/{id}")
	public ObjectB getObject(@PathVariable int id) {
		return objectRepository.findById(id).get();
	}
	
	@PutMapping
	public void updateObject(@RequestBody ObjectB object) {
		ObjectB i=objectRepository.findById(object.getId()).get();
		if(object!=i) {
			i.setAnnotation(object.getAnnotation());
			i.setPolygons(object.getPolygons());
			objectRepository.save(i);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteObject(@PathVariable int id) {
		objectRepository.deleteById(id);
	}
}
