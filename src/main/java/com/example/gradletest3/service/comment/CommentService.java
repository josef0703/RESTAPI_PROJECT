package com.example.gradletest3.service.comment;

import com.example.gradletest3.dao.comment.CommentDAO;
import com.example.gradletest3.dao.comment.CommentDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentDAO commentDAO;

    public CommentService(CommentDAO commentDAO) {
        this.commentDAO = commentDAO;
    }

    public int commentInsert(CommentDTO commentDTO) {
        return commentDAO.commentInsert(commentDTO);
    }

    public List<CommentDTO> getList(int b_num) {
        return commentDAO.getList(b_num);
    }

    public int getCount(int b_num) {
        return commentDAO.getCount(b_num);
    }

}
