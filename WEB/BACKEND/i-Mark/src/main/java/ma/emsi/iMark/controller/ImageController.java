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

import ma.emsi.iMark.model.Image;
import ma.emsi.iMark.repository.ImageRepository;
import ma.emsi.iMark.service.SequenceGeneratorService;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping
	public List<Image> getAll(){
		return imageRepository.findAll();
	}
	
	@PostMapping
	public void addImage(@RequestBody Image image) {
		image.setId(sequenceGeneratorService.getSequenceNumber(Image.SEQUENCE_NAME));
		imageRepository.save(image);
	}
	
	@GetMapping("/{id}")
	public Image getImage(@PathVariable int id) {
		return imageRepository.findById(id).get();
	}
	
	@PutMapping
	public void updateImage(@RequestBody Image image) {
		Image i=imageRepository.findById(image.getId()).get();
		if(image!=i) {
			i.setDate_captured(image.getDate_captured());
			i.setFile_url(image.getFile_url());
			i.setHeight(image.getHeight());
			i.setWidth(image.getWidth());
			i.setGps_location(image.getGps_location());
			i.setObjects(image.getObjects());
			imageRepository.save(i);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteImage(@PathVariable int id) {
		imageRepository.deleteById(id);
	}
	
}
