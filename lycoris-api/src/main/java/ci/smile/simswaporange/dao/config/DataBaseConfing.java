package ci.smile.simswaporange.dao.config;

import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import ci.smile.simswaporange.utils.ParamsUtils;
@Configuration
public class DataBaseConfing {
	@Autowired
	private ParamsUtils paramsUtils;
	 @Bean(name = "mysqlDb")
	 @ConfigurationProperties(prefix = "spring.datasource")
	 public DataSource customDataSource() {
	  DriverManagerDataSource dataSource = new DriverManagerDataSource();
	  	dataSource.setDriverClassName(paramsUtils.getUserDataSource());
		dataSource.setUrl(paramsUtils.getUrlDb());
		dataSource.setUsername(paramsUtils.getUserDb());
		dataSource.setPassword(paramsUtils.getPassDb());
	  
	  return dataSource; 
	 }
	 @Bean(name = "mysqlJdbcTemplate")
	 public JdbcTemplate mysqlJdbcTemplate(@Qualifier("mysqlDb") DataSource dsMySQL) {
	  return new JdbcTemplate(dsMySQL);
	 }
}

