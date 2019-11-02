package cn.houyuehui.dto;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author: jsonhou
 * @Date: 2019/11/2 14:52
 */
public class MyRowMapper implements RowMapper<PersistentRememberMeToken> {
    @Override
    public PersistentRememberMeToken mapRow(ResultSet resultSet, int i) throws SQLException {
        PersistentRememberMeToken persistentRememberMeToken=new PersistentRememberMeToken(
                resultSet.getString("username"),
                resultSet.getString("series"),
                resultSet.getString("token"),
                resultSet.getDate("last_used")
        );
        return persistentRememberMeToken;
    }
}
