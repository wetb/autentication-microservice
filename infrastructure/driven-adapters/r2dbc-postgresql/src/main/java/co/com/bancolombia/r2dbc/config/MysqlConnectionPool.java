package co.com.bancolombia.r2dbc.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.mariadb.r2dbc.MariadbConnectionConfiguration;
import org.mariadb.r2dbc.MariadbConnectionFactory;
import org.mariadb.r2dbc.client.SslMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class MysqlConnectionPool {

    private static final int INITIAL_SIZE = 10;
    private static final int MAX_SIZE = 20;
    private static final int MAX_IDLE_TIME = 30; // en minutos

    @Bean
    public ConnectionPool getConnectionConfig(MysqlConnectionProperties properties) {
        MariadbConnectionConfiguration dbConfiguration = MariadbConnectionConfiguration.builder()
                .host(properties.getHost())
                .port(properties.getPort())
                .database(properties.getDatabase())
                .username(properties.getUsername())
                .password(properties.getPassword())
                .sslMode(SslMode.DISABLE)
                .allowPublicKeyRetrieval(true)
                .build();

        ConnectionFactory connectionFactory = new MariadbConnectionFactory(dbConfiguration);

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder()
                .connectionFactory(connectionFactory)
                .name("api-mariadb-connection-pool")
                .initialSize(INITIAL_SIZE)
                .maxSize(MAX_SIZE)
                .maxIdleTime(Duration.ofMinutes(MAX_IDLE_TIME))
                .validationQuery("SELECT 1")
                .build();

        return new ConnectionPool(poolConfiguration);
    }
}

