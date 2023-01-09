package org.gab.ClouDuck.users;

import com.amazonaws.regions.Regions;
import org.gab.ClouDuck.crypt.Cryptographer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {

        this.repository = repository;
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

    public User findById(String id) {

        return repository.findById(id).orElseThrow();
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
