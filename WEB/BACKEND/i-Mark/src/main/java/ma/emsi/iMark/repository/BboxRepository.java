package ma.emsi.iMark.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.iMark.model.Bbox;

@Repository
public interface BboxRepository extends MongoRepository<Bbox, Integer> {

}
