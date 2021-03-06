package com.mohamed.movies.repositories;

import com.mohamed.movies.entities.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie,Long> {
    Optional<Movie>findMovieByMovieName(String name);
    List<Movie>findMoviesByReleaseDate(Date releasedate);
    List<Movie>getMoviesByStarringContaining(String actorName);
    List<Movie>findMoviesByMovieDetials_Country(String country);
    Optional<Movie>getMovieByMovieName(String name);



}
