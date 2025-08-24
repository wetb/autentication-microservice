package co.com.bancolombia.r2dbc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mysql")
public class MysqlConnectionProperties {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    // getters y setters
}
