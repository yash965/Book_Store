package com.Yash.Book_Store.Service;

import com.Yash.Book_Store.Entity.User;
import com.Yash.Book_Store.Repository.User_Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class User_Service implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(User_Service.class);

    private final User_Repository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User_Service(User_Repository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String plainTextPassword)
    {
        try {
            if(userRepository.findByUsername(username).isPresent())
            {
                throw new IllegalStateException("Username already Exists");
            }
        } catch (IllegalStateException e) {
            log.error("User - [{}] already exists", username);
            throw new RuntimeException(e);
        }

        User newUser = new User();

        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(plainTextPassword));
        newUser.setRole("ROLE_USER");

        return userRepository.save(newUser);
    }

    /**
     * This method is required by the UserDetailsService interface.
     * Spring Security calls it during authentication to fetch user details.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found" + username));
    }
}
