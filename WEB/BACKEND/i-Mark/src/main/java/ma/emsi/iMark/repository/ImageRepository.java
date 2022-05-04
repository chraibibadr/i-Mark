package ma.emsi.iMark.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.iMark.model.Image;

@Repository
public interface ImageRepository extends MongoRepository<Image, Integer> {

}
