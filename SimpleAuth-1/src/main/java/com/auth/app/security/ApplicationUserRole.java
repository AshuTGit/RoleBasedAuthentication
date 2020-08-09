package com.auth.app.security;

import static com.auth.app.security.ApplicationUserPermission.COURSE_READ;
import static com.auth.app.security.ApplicationUserPermission.COURSE_WRITE;
import static com.auth.app.security.ApplicationUserPermission.STUDENT_READ;
import static com.auth.app.security.ApplicationUserPermission.STUDENT_WRITE;

import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum ApplicationUserRole {

	STUDENT(Sets.newHashSet()), ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_WRITE, STUDENT_READ)), SUPER_ADMIN(Sets.newHashSet(COURSE_READ, STUDENT_READ));

	private final Set<ApplicationUserPermission> permissions;

	ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<ApplicationUserPermission> getPermissions() {
		return permissions;
	}
	
	public Set<GrantedAuthority> getGrantedAuthorities(){
		Set<GrantedAuthority> permissions= getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
		.collect(Collectors.toSet());
		permissions.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
		return permissions;
	}

}
