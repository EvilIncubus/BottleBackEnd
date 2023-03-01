package org.bottleProject.service.impl;

import org.bottleProject.dao.ProfileDao;
import org.bottleProject.dao.RoleDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.entity.Profile;
import org.bottleProject.entity.User;
import org.bottleProject.configuration.security.CustomUserDetailsUserFactory;
import org.bottleProject.configuration.security.CustomUserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final ProfileDao profileDao;

    public SecurityUserDetailsService(UserDao userDao, RoleDao roleDao, ProfileDao profileDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.profileDao = profileDao;
    }
    @Override
    public CustomUserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        Profile profile = profileDao.findById(user.getUserId());
        String address = profileDao.getProfileAddress(profile);
        user.setRoles(roleDao.getUserRole(user));
        return CustomUserDetailsUserFactory.create(user, profile, address);
    }
}
