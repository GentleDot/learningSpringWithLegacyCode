package net.gentledot.springcodeproject.database;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
