package ee.juhan.meetingorganizer.server.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Configuration for Heroku. <br> Retrieves connection details from System Enviroment variable.
 * <br>
 * <p>
 * For example <code>mysql://root:root@localhost:3306/test</code>.
 *
 * @author TKasekamp
 */
@Configuration
@Profile("default")
public class HerokuConfiguration {

	@SuppressWarnings("unused")
	private static final Logger LOG = LoggerFactory.getLogger(HerokuConfiguration.class);

	@Bean
	public DataSource dataSource() {
		String databaseUrl = System.getenv("DATABASE_URL");
		// LOG.info("Using Heroku configuration with variable: " + databaseUrl);

		String[] a = databaseUrl.split("@");
		String[] b = a[0].split(":");
		String username = b[1].replace("//", "");
		String password = b[2];
		String db = "jdbc:postgresql://" + a[1];

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// clever way to set the password from environment variables
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(db);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		return dataSource;

	}

}
