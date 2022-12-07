package com.example.gradletest3.service.movie.image;

import com.example.gradletest3.dao.movie.image.MovieImageDAO;
import com.example.gradletest3.dao.movie.image.MovieImageDTO;
import org.springframework.stereotype.Service;

@Service
public class MovieImageService {
    private final MovieImageDAO movieImageDAO;

    public MovieImageService(MovieImageDAO movieImageDAO) {
        this.movieImageDAO = movieImageDAO;
    }

    public int addImage(MovieImageDTO movieImageDTO) {

        return movieImageDAO.addImage(movieImageDTO);
    }
}
