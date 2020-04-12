package net.gentledot.springcodeproject.configurations;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(value = "net.gentledot.springcodeproject")
public class DBConnectionConfig {

    private final DataSource dataSource;

    public DBConnectionConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        return sqlSessionFactoryBean.getObject();
    }
}
