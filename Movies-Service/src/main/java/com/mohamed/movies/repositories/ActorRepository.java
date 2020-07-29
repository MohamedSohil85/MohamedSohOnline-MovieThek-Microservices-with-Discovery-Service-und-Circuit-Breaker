package com.mohamed.movies.repositories;

import com.mohamed.movies.entities.Actor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends CrudRepository<Actor,Long> {
Optional<Actor>findActorByActorName(String name);

}
