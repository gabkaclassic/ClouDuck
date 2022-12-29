package org.gab.ClouDuck.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column
    private String key;
    
    @Column
    private String rootFolder;
    
    @Basic
    private Date expire;
    
    public User(String key, Date expire) {
        
        this.key = key;
        this.expire = expire;
    }
}
