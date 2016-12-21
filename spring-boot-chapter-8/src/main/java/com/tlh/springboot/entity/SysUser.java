package com.tlh.springboot.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class SysUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;
	private String username;
	private String password;

	// 和角色的多对多关系，级联为刷新，抓取方式为立即查询
	@ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	private List<SysRole> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		List<SysRole> roles = this.getRoles();
		if (roles != null) {
			for (SysRole role : roles) {
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			}
		}
		return authorities.isEmpty() ? null : authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

}
