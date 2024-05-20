package com.garima.fitnesscentre.models.Data;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name="admin")
@Data
public class Admin implements UserDetails

{

    private static final long serialVersionUID =2l;
      @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Size(min=2, message = "Username must be at least 2 characters long")
    private String username;

    @Size(min=4, message = "Password must be at least 4 characters long")
    private String password;

    @Email(message = "Please enter a valid email")
    private String email;
    @Column(name="first_name")
        private String firstName;
 @Column(name="last_name")
         private String lastName;

 @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
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


    
}
