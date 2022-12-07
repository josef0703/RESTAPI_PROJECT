package com.example.gradletest3.service.movie.paylist;

import com.example.gradletest3.dao.movie.paylist.MoviePayListDAO;
import com.example.gradletest3.dao.movie.paylist.MoviePayListDTO;
import org.springframework.stereotype.Service;

@Service
public class MoviePayListService {
    private final MoviePayListDAO moviePayListDAO;

    public MoviePayListService(MoviePayListDAO moviePayListDAO) {
        this.moviePayListDAO = moviePayListDAO;
    }

    public int addPaylist(MoviePayListDTO moviePayListDTO) {
        return moviePayListDAO.addPayList(moviePayListDTO);
    }
}
