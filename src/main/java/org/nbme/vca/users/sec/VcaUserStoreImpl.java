package org.nbme.vca.users.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Date: 11/4/14
 * Time: 2:16 PM
 */
@Repository
@Profile("!dev")
public class VcaUserStoreImpl implements VcaUserStore {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public VcaUserStoreImpl(@Qualifier("rolesDataSource") DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<String> getRoles(String username) throws UsernameNotFoundException {

        String sql = "SELECT r.rolename FROM " +
                "webconsole_user u, " +
                "webconsole_user_role ur, " +
                "webconsole_role r \n" +
                "WHERE u.username = :username " +
                "AND u.key = ur.user_key " +
                "AND ur.role_key = r.key";

        SqlParameterSource namedParameters = new MapSqlParameterSource("username", username);
        return namedParameterJdbcTemplate.queryForList(sql, namedParameters, String.class);
    }

}
