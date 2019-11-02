package cn.houyuehui.repository;

import cn.houyuehui.dto.MyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * @Author: jsonhou
 * @Date: 2019/11/1 20:16
 */
@Repository
public class JdbcTokenReposityImpl extends JdbcDaoSupport implements PersistentTokenRepository {
    public JdbcTokenReposityImpl(DataSource dataSource){
        this.setDataSource(dataSource);
    }

    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {
        String insertSql="INSERT INTO persistent_logins(username,series,token,last_used) VALUES (?,?,?,?)";
        getJdbcTemplate().update(insertSql,persistentRememberMeToken.getUsername(),persistentRememberMeToken.getSeries(),persistentRememberMeToken.getTokenValue(),persistentRememberMeToken.getDate());
    }

    @Override
    public void updateToken(String s, String s1, Date date) {
        String updateSql="UPDATE persistent_logins SET token=?,last_used=? WHERE series=?";
        getJdbcTemplate().update(updateSql,s,date,s1);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String s) {
        String selectSql="SELECT username,series,token,last_used FROM persistent_logins WHERE series=?";
        List<PersistentRememberMeToken> persistentRememberMeTokenList= getJdbcTemplate().query(selectSql, new MyRowMapper(),s);
        if (persistentRememberMeTokenList.size()>0) {
            return persistentRememberMeTokenList.get(0);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void removeUserTokens(String s) {
        String deleteSql="DELETE FROM persistent_logins WHERE series=?";
        getJdbcTemplate().update(deleteSql,s);
    }
}
