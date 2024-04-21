package com.medical.healthcare.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.medical.healthcare.controller.dto.UserRegistrationDto;
import com.medical.healthcare.model.Role;
import com.medical.healthcare.model.User;
import com.medical.healthcare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        User user = userRepository.findByEmail(email);
//        if (user == null) {
//            throw new UsernameNotFoundException("Invalid username or password.");
//        }
//        return new org.springframework.security.core.userdetails.User(user.getEmail(),
//        passwordEncoder.encode(user.getPassword()),
//            mapRolesToAuthorities(user.getRoles()));
//
//    }
    @Override
    public UserDetails loadUserByUsername(String identificationNumber) throws UsernameNotFoundException {
            User user = userRepository.findByIdentificationNumber(identificationNumber);
            if (user == null) {
                throw new UsernameNotFoundException("Invalid identification Number or password.");
            }
            return new org.springframework.security.core.userdetails.User(user.getIdentificationNumber(),
            passwordEncoder.encode(user.getPassword()),
                mapRolesToAuthorities(user.getRoles()));

        }
    @Override
    public User findByEmail(String email) {        
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByIdentificationNumber(String identificationNumber) {
        return userRepository.findByIdentificationNumber(identificationNumber);
    }


    @Override
    public User save(UserRegistrationDto registration) {
                User user = new User();
                user.setFirstName(registration.getFirstName());
                user.setLastName(registration.getLastName());
                user.setEmail(registration.getEmail());
                user.setPassword(registration.getPassword());
                user.setUserType(registration.getUserType());
                user.setIdentificationNumber(registration.getIdentificationNumber());
                if(registration.getUserType().equals("doctor")){
                    user.setRoles(Arrays.asList(new Role("ROLE_DOCTOR")));
                } else if (registration.getUserType().equals("pharmacist")) {
                    user.setRoles(Arrays.asList(new Role("ROLE_PHARMACIST")));
                }else {
                    user.setRoles(Arrays.asList(new Role("ROLE_USER")));
                }
                return userRepository.save(user);
            }

            private Collection < ? extends GrantedAuthority > mapRolesToAuthorities(Collection < Role > roles) {
                return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            }
            @Override
            public List<User> getAllUsers(){
                return userRepository.findAll();
            };

    @Override
    public void deleteById(long id){
        userRepository.deleteById(id);
    }


}