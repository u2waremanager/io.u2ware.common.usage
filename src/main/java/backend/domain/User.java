package backend.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;

    private Collection<GrantedAuthority> authorities;

    private AttributesSet roles = new AttributesSet();

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String searchKeyword;
}