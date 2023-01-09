package org.gab.ClouDuck.users;

import com.amazonaws.regions.Regions;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column
    private String id;
    @Column(name = "access_key")
    @Lob
    private byte[] accessKey;
    @Column(name = "secret_key")
    @Lob
    private byte[] secretKey;
    @Column(name = "region")
    @Enumerated(EnumType.STRING)
    private Regions region;
}
