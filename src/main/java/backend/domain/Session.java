package backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examples_channels")
@Data
@NoArgsConstructor @AllArgsConstructor
public class Session {


    @Id
    private String principal;

    private String state;

    private Long timestamp;

}
