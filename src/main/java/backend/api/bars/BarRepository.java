package backend.api.bars;

import java.util.List;

import org.springframework.data.rest.core.annotation.RestResource;

import backend.domain.Bar;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;


public interface BarRepository extends RestfulJpaRepository<Bar, Long> {


    @RestResource(exported=false) 
    public List<Bar> findByName(String name);    

    @RestResource(exported=false) 
    public List<Bar> findByAge(Integer age);    

}
