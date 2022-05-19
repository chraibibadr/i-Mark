package ma.emsi.iMark.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.iMark.model.Point;

@Repository
public interface PointRepository  extends MongoRepository<Point, Integer>{

}
