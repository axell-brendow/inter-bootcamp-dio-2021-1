package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    public static Connection getConnection() {
        try (InputStream input = ConnectionFactory.class
                .getClassLoader().getResourceAsStream("connection.properties"))
        {
            Properties props = new Properties();
            props.load(input);

            var driver = props.getProperty("jdbc.driver");
            var host = props.getProperty("db.host");
            var port = props.getProperty("db.port");
            var database = props.getProperty("db.name");
            var user = props.getProperty("db.user.login");
            var password = props.getProperty("db.user.password");

            String connectionUrl = String.format(
                    "jdbc:%s://%s:%s/%s",
                    driver, host, port, database
            );

            //Carregar a classe específica de implementação do driver na memória da JVM. (A partir da versão JDBC 4 não é mais necessário!!!)
            //Class.forName("com.mysql.jdbc.Driver");

            try {
                return DriverManager.getConnection(connectionUrl, user, password);
            } catch (SQLException e) {
                System.out.println("FALHA ao se conectar ao banco MySQL!");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
