package ma.emsi.iMark.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.iMark.model.ObjectB;

@Repository
public interface ObjectBRepository extends MongoRepository<ObjectB, Integer> {

}
