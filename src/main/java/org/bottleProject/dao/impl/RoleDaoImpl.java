package org.bottleProject.dao.impl;

import org.bottleProject.dao.RoleDao;
import org.bottleProject.entity.Role;
import org.bottleProject.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class RoleDaoImpl extends AbstractDaoImpl<Role> implements RoleDao {

    public RoleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Role> getAll(int size, int offset) {
        return null;
    }

    @Override
    public Role create(Role role) {
        return null;
    }

    @Override
    public Role update(Role role, Long id) {
        return null;
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public Role findById(Long id) {
        return null;
    }

    @Override
    public void setUserRole(User user) {
        for (Role role : user.getRoles()){
            Integer id = getJdbcTemplate().queryForObject("Select role_id from role where role_name = ?;",
                   Integer.class, role.getRoleName());
            getJdbcTemplate().update("INSERT INTO user_role (user_id, role_id) VALUES(?,?);",
                    user.getUserId(), id);
        }
    }

    @Override
    public List<Role> getUserRole(User user) {
        return getJdbcTemplate().query("Select role.role_name from role join user_role on user_role.role_id = role.role_id Where user_role.user_id = ?;",
                BeanPropertyRowMapper.newInstance(Role.class), user.getUserId());

    }

    @Override
    public void updateUserRole(User user) {
        for (Role role : user.getRoles()) {
            Integer roleId = getJdbcTemplate().queryForObject("Select role_id from role where role_name = ?;",
                    Integer.class, role.getRoleName());
            Integer userRoleId = getJdbcTemplate().queryForObject("Select user_role_id from user_role where role_id = ? and user_id = ?;",
                    Integer.class, roleId, user.getUserId());
            getJdbcTemplate().update("UPDATE user_role SET user_id = ?, role_id = ? WHERE user_role_id = ? ;",
                    user.getUserId(), roleId, userRoleId);
        }
    }
}
