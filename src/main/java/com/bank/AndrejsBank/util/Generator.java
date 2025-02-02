package com.bank.AndrejsBank.util;

import com.bank.AndrejsBank.documents.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.jdbc.connections.spi.JdbcConnectionAccess;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;

import javax.imageio.spi.ServiceRegistry;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.UUID;
import java.util.stream.Stream;

@Slf4j
public class Generator implements IdentifierGenerator {
@Override
public Object generate(SharedSessionContractImplementor session, Object object){

    BankAccount bnk = (BankAccount)object;

    String prefix = "cli";
    JdbcConnectionAccess jdbcConnectionAccess = session.getJdbcConnectionAccess();
    String nameQuery = "select name from user";
    String surnameQuery = "select surname from user";
    try(Connection connection = jdbcConnectionAccess.obtainConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(nameQuery);
        ResultSet surnameResultSet = statement.executeQuery(surnameQuery);
    ) {
        if (resultSet.next() && surnameResultSet.next()) {
            return  surnameResultSet.getString(1).toUpperCase().substring(0,3)+resultSet.getString(1).toUpperCase().substring(0,3)+"_"+ UUID.randomUUID().toString().replace("-","").substring(0,12);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
}