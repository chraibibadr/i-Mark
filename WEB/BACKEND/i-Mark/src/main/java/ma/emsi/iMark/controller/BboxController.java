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

import ma.emsi.iMark.model.Bbox;
import ma.emsi.iMark.repository.BboxRepository;

@RestController
@RequestMapping("/bboxes")
public class BboxController {

	@Autowired
	private BboxRepository bboxRepository;
	
	@GetMapping
	public List<Bbox> getAll(){
		return bboxRepository.findAll();
	}
	
	@PostMapping
	public void addBbox(@RequestBody Bbox bbox) {
		bboxRepository.save(bbox);
	}
	
	@GetMapping("/{id}")
	public Bbox getBbox(@PathVariable int id) {
		return bboxRepository.findById(id).get();
	}
	
	@PutMapping
	public void updateBbox(@RequestBody Bbox bbox) {
		Bbox i=bboxRepository.findById(bbox.getId()).get();
		if(bbox!=i) {
			i.setXmax(bbox.getXmax());
			i.setYmax(bbox.getYmax());
			i.setXmin(bbox.getXmin());
			i.setYmin(bbox.getYmin());
			bboxRepository.save(i);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteBbox(@PathVariable int id) {
		bboxRepository.deleteById(id);
	}
}
