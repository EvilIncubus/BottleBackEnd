package org.bottleProject.service.impl;

import org.bottleProject.configuration.security.JwtProvider;
import org.bottleProject.dao.ProfileDao;
import org.bottleProject.dao.RoleDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.dto.AuthenticationRequest;
import org.bottleProject.dto.CreateUserDto;
import org.bottleProject.entity.Profile;
import org.bottleProject.entity.User;
import org.bottleProject.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final ProfileDao profileDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDao userDao,ProfileDao profileDao, RoleDao roleDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.profileDao = profileDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User addUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(createUserDto.getPassword()));
        user.setRoles(createUserDto.getRoles());
        roleDao.setUserRole(user);
        Profile profile = new Profile();
        profile.setFirstName(createUserDto.getFirstName());
        profile.setLastName(createUserDto.getLastName());
        profile.setAddressId(createUserDto.getAddressId());
        profile.setPhoneNumber(createUserDto.getPhoneNumber());
        profile.setCompany(createUserDto.getCompany());
        profileDao.create(profile);
        return userDao.create(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }


}
