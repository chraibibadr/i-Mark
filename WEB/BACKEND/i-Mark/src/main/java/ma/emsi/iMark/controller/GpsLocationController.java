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

import ma.emsi.iMark.model.GpsLocation;
import ma.emsi.iMark.repository.GpsLocationRepository;

@RestController
@RequestMapping("/gpsLocations")
public class GpsLocationController {

	@Autowired
	private GpsLocationRepository gpsLocationRepository;
	
	@GetMapping
	public List<GpsLocation> getAll(){
		return gpsLocationRepository.findAll();
	}
	
	@PostMapping
	public void addGpsLocation(@RequestBody GpsLocation gpsLocation) {
		gpsLocationRepository.save(gpsLocation);
	}
	
	@GetMapping("/{id}")
	public GpsLocation getGpsLocation(@PathVariable int id) {
		return gpsLocationRepository.findById(id).get();
	}
	
	@PutMapping
	public void updateGpsLocation(@RequestBody GpsLocation gpsLocation) {
		GpsLocation i=gpsLocationRepository.findById(gpsLocation.getId()).get();
		if(gpsLocation!=i) {
			i.setLatitude(gpsLocation.getLatitude());
			i.setLongitude(gpsLocation.getLongitude());
			gpsLocationRepository.save(i);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public void deleteGpsLocation(@PathVariable int id) {
		gpsLocationRepository.deleteById(id);
	}
}
