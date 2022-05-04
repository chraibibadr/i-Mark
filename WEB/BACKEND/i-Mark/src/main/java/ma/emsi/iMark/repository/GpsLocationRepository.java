package ma.emsi.iMark.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.iMark.model.GpsLocation;

@Repository
public interface GpsLocationRepository extends MongoRepository<GpsLocation, Integer> {

}
