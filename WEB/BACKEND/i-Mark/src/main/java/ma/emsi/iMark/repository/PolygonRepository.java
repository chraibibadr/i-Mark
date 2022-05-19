package ma.emsi.iMark.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.iMark.model.Polygon;

@Repository
public interface PolygonRepository extends MongoRepository<Polygon, Integer> {

}
