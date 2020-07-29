package com.mohamed.movies.repositories;

import com.mohamed.movies.entities.MovieDetials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieDetailsRepository extends CrudRepository<MovieDetials,Long> {
}
