package org.gab.ClouDuck.users;

import com.amazonaws.regions.Regions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final ApplicationContext context;
    
    @Autowired
    public UserService(UserRepository repository, ApplicationContext context) {

        this.repository = repository;
        this.context = context;
    }
    
    public User createUser(String id, byte[] accessKey, byte[] secretKey, String region) {
        var newUser = new User(
                id,
                accessKey,
                secretKey,
                Regions.fromName(region)
        );

        return save(newUser);
    }

    public User save(User user) {
        return repository.save(user);
    }

    @Cacheable("login")
    public User findById(String id) {

        return repository.findById(id).orElseThrow();
    }


    @CacheEvict(value = {"login"})
    public void delete(String id) {
        repository.deleteById(id);
    }
}
