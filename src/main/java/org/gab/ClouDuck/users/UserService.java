package org.gab.ClouDuck.users;

import org.gab.ClouDuck.exceptions.ExpiredKeyException;
import org.gab.ClouDuck.exceptions.UserNotFoundException;
import org.gab.ClouDuck.responses.RegistrationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Calendar;
import java.util.UUID;

@Service
public class UserService {
    
    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
    
        this.repository = repository;
    }
    
    public String rootFolderByKey(String key) throws ExpiredKeyException, UserNotFoundException {
    
        var user = repository.findById(key).orElseThrow(() -> new UserNotFoundException(key));
        
        if(checkIfKeyExpired(user))
            throw new ExpiredKeyException(user.getExpire());
        
        return user.getRootFolder();
    }
    
    private boolean checkIfKeyExpired(User user) {
    
        var now = new Date(java.util.Calendar.getInstance().getTime().getTime());
        
        return user.getExpire().before(now);
    }
    
    public RegistrationResponse registrationOrUpdateKey(String key) {
        
        String rootFolder;

        var user = (key == null) ? createUser() : repository.findById(key).orElseGet(this::createUser);
        user.setRootFolder(UUID.randomUUID().toString());
        
        repository.save(user);
        
        return RegistrationResponse.success(user);
    }
    
    private User createUser() {
    
        var expireDate = Calendar.getInstance();
        expireDate.add(Calendar.DAY_OF_MONTH, 1);
        
        return new User(
                UUID.randomUUID().toString(),
                new Date(expireDate.getTime().getTime())
        );
    }
    
}
