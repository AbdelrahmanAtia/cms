package org.javaworld.cmsbackend.service;

import org.javaworld.cmsbackend.model.Profile;

public interface ProfileService {

	public Profile getCurrentUserProfile();

	public Profile updateCurrentUserProfile(Profile profile);

	public boolean isUniqueEmail(String email);

}
