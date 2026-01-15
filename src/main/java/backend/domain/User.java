package backend.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import backend.domain.auditing.AuditedEntity;
import backend.domain.properties.AttributesSet;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "examples_users")
@Data @EqualsAndHashCode(callSuper = true)
public class User extends AuditedEntity implements UserDetails{

    @Id
    private String username;

    private AttributesSet roles;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Transient @JsonIgnore 
    public Collection<GrantedAuthority> getAuthorities() {
        return getAuthorities(roles);
    }

    @Transient @JsonIgnore 
    public static Collection<GrantedAuthority> getAuthorities(AttributesSet roles) {
        if(roles == null) return Collections.emptyList();
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
    }    

    @Transient @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String searchKeyword;


    @Transient @JsonIgnore 
   public boolean isAccountNonExpired() {
      return true;
   }

    @Transient @JsonIgnore 
   public boolean isAccountNonLocked() {
      return true;
   }

    @Transient @JsonIgnore 
   public boolean isCredentialsNonExpired() {
      return true;
   }

    @Transient @JsonIgnore 
   public boolean isEnabled() {
      return true;
   }

}