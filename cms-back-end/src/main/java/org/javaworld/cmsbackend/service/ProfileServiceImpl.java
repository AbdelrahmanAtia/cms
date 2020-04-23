package org.javaworld.cmsbackend.service;

import org.javaworld.cmsbackend.entity.User;
import org.javaworld.cmsbackend.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private UserService userService;
	
	@Override
	public Profile getCurrentUserProfile() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Profile profile = new Profile();
		profile.setEmail(user.getEmail());
		profile.setPassword(user.getPassword());
		profile.setName(user.getName());
		profile.setPhone(user.getPhone());
		return profile;
	}

	@Override
	public Profile updateCurrentUserProfile(Profile profile) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user.setEmail(profile.getEmail());
		user.setPassword(profile.getPassword());
		user.setName(profile.getName());
		user.setPhone(profile.getPhone());
		User updatedUser = userService.update(user);
		Profile updatedProfile = new Profile();
		updatedProfile.setEmail(updatedUser.getEmail());
		updatedProfile.setPassword(updatedUser.getPassword());
		updatedProfile.setName(updatedUser.getName());
		updatedProfile.setPhone(updatedUser.getPhone());
		return updatedProfile;
	}

}
