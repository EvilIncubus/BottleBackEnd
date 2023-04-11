package org.bottleProject.dao;

import org.bottleProject.entity.Profile;

public interface ProfileDao extends Dao<Profile>{
    String getProfileAddress(Profile profile);

    Profile getProfileByUserId(long id);

    void updateProfile(Profile profile);
}
