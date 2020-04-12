package net.gentledot.springcodeproject.web;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisConnectionTest {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Test
    void sqlSessionFactoryTest() {
        System.out.println(sqlSessionFactory);
    }

    @Test
    void sqlSessionTest() {
        try(SqlSession sqlSession = sqlSessionFactory.openSession()) {
            System.out.println(sqlSession);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
