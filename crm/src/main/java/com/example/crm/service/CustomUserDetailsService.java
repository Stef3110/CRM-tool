package com.example.crm.service;

import com.example.crm.model.Personnel;
import com.example.crm.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private PersonnelRepository personnelRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Personnel personnel = personnelRepository.findByUsername(username);

        if(personnel != null) {
            return new org.springframework.security.core.userdetails.User(
                    personnel.getUsername(), personnel.getPassword(),  mapRolesToAuthorities(personnel.getRole())
            );
        }
        else {
            throw new UsernameNotFoundException("Invalid username");
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
        Collection<String> roles = new ArrayList<>();
        roles.add(role);
        Collection<? extends GrantedAuthority> mapRoles = roles.stream().map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
        return mapRoles;
    }
}
