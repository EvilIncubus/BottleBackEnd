package org.bottleProject.configuration.security;

import org.bottleProject.entity.Profile;
import org.bottleProject.entity.Role;
import org.bottleProject.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetailsUserFactory {

    public CustomUserDetailsUserFactory() {
    }

    public static CustomUserDetailsImpl create(User user, Profile profile, String address){
        return new CustomUserDetailsImpl(
                user.getEmail(),
                user.getPassword(),
                profile.getFirstName(),
                profile.getLastName(),
                address,
                profile.getPhoneNumber(),
                profile.getCompany(),
                mapRoleToAuthorities(user.getRoles())
        );
    }
    private static List<? extends GrantedAuthority> mapRoleToAuthorities(List<Role> userRoles){
        return userRoles.stream()
                .map(role ->  new SimpleGrantedAuthority(role.getRoleName().toUpperCase())
                ).collect(Collectors.toList());
    }
}
