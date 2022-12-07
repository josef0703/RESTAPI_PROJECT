package com.example.gradletest3;

import com.example.gradletest3.dao.board.BoardDAO;
import com.example.gradletest3.dao.search.Criteria;
import org.apache.logging.slf4j.SLF4JLogger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

class GradleTest3ApplicationTests {


    private BoardDAO dao;

    @Test
    void contextLoads() {
    }
    
    @Test
    public void testGetListPagin() {
        Criteria cri = new Criteria();
//        List list = dao.boardList(cri);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list);
//        }
//        System.out.println("List: " +list);

    }

}
