package backend.domain;



import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import backend.domain.auditing.AuditedEntity;
import backend.domain.properties.AttributesMap;
import backend.domain.properties.AttributesSet;
import backend.domain.properties.CryptoConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examples_items")
@Data @EqualsAndHashCode(callSuper = true)
public class Item extends AuditedEntity{
    
    @Id
    @GeneratedValue
    private UUID id;

    private String title;


    ////////////////////////////////////////////
    // Attributes
    ////////////////////////////////////////////
    @Column(length = 1024*100)
    @Convert(converter=CryptoConverter.class)
    private String cryptoValue;

    @Column(length = 1024*100)
    private AttributesMap jsonValue;


    @Column(length = 1024*100)
    private AttributesSet arrayValue;

    ////////////////////////////////////////////
    // ManyToOne 
    // {foo : "http://....."} or {foo : {id : "", name: "", _links : {}}}"
    ////////////////////////////////////////////
    @ManyToOne @RestResource(exported = false)
	@JsonProperty(access = Access.READ_ONLY)
	private Foo foo;
	
    @Transient
	@JsonProperty(access = Access.WRITE_ONLY)
	private Link fooLink;  

    ////////////////////////////////////////////
    // ManyToMany 
    // {bar : "http://....."} or {bar : {id : "", name: "", _links : {}}}"
    ////////////////////////////////////////////
	@ManyToMany(fetch=FetchType.EAGER) @JoinTable
	@RestResource(exported = false)
    private Set<Bar> bars = new HashSet<>();

    @Transient
	@JsonProperty(access = Access.WRITE_ONLY)
    private Set<Link> barsLinks = new HashSet<>();


    ////////////////////////////////////////////
    // ElementCollection 
    // {childs : [{name : ...}, {name : ...}, ]}
    ////////////////////////////////////////////
    @ElementCollection
	@CollectionTable(name="examples_items_childs", joinColumns=@JoinColumn(name="parent"))
	private Set<Child> childs = new HashSet<>();
	
	@Embeddable
	@Data @Builder @NoArgsConstructor @AllArgsConstructor
	public static class Child {		
		private String name;
		private Integer age;	
	}






    ////////////////////////////////////////////
    //
    ////////////////////////////////////////////
    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String searchKeyword;
}