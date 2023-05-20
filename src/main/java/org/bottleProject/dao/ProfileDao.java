package org.bottleProject.dao;

import org.bottleProject.entity.Profile;

import java.util.List;

public interface ProfileDao extends Dao<Profile>{
    String getProfileAddress(Profile profile);

    Profile getProfileByUserId(long id);

    void updateProfile(Profile profile);
}
