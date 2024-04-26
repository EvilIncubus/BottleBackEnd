package org.bottleProject.service.impl;

import org.bottleProject.dao.ProfileDao;
import org.bottleProject.dao.RoleDao;
import org.bottleProject.dao.UserDao;
import org.bottleProject.dto.CreateUserDto;
import org.bottleProject.dto.UserWithProfileDto;
import org.bottleProject.entity.Page;
import org.bottleProject.entity.Profile;
import org.bottleProject.entity.User;
import org.bottleProject.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final ProfileDao profileDao;

    public UserServiceImpl(UserDao userDao,ProfileDao profileDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.profileDao = profileDao;
    }

    @Override
    public User addUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(createUserDto.getPassword()));
        user.setAccountStatus("ACTIVE");
        user.setCreatedDate(LocalDateTime.now());
        user = userDao.create(user);
        user.setRoles(createUserDto.getRoles());
        roleDao.setUserRole(user);
        Profile profile = new Profile();
        profile.setFirstName(createUserDto.getFirstName());
        profile.setLastName(createUserDto.getLastName());
        profile.setPhoneNumber(createUserDto.getPhoneNumber());
        profile.setAddress(createUserDto.getEmail());
        profile.setCompany(createUserDto.getCompany());
        profile.setUserId((int) user.getUserId());
        profile.setProfilePhotoPath(createUserDto.getProfilePhoto());
        profileDao.create(profile);
        return user;
    }

    @Override
    public Page<UserWithProfileDto> getAllUsersWithProfile(int page, int size) {
        List<UserWithProfileDto> users = userDao.getListOfUsersWithProfile(page, size);
        return new Page<>(users, userDao.countListOfUsersWithProfile(), page, size);
    }

    @Override
    public void setUserAccountStatus(String email, String accountStatus) {
        userDao.setNewUserAccountStatus(email, accountStatus);
    }

    @Override
    public User updateUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(createUserDto.getPassword()));
        user.setAccountStatus("ACTIVE");
        user.setCreatedDate(LocalDateTime.now());
        user = userDao.updateUsers(user);
        user.setRoles(createUserDto.getRoles());
        roleDao.updateUserRole(user);
        Profile profile = new Profile();
        profile.setFirstName(createUserDto.getFirstName());
        profile.setLastName(createUserDto.getLastName());
        profile.setPhoneNumber(createUserDto.getPhoneNumber());
        profile.setAddress(createUserDto.getAddress());
        profile.setCompany(createUserDto.getCompany());
        profile.setUserId((int) user.getUserId());
        profile.setProfilePhotoPath(createUserDto.getProfilePhoto());
        profileDao.updateProfile(profile);
        return user;
    }

    @Override
    public List<UserWithProfileDto> getListOfCustomersForOperator() {
        return userDao.getListOfCustomersForOperator();
    }

    @Override
    public List<UserWithProfileDto> getSearchListOfCustomersForOperator(String search) {
        return userDao.getSearchListOfCustomersForOperator(search);
    }

}
