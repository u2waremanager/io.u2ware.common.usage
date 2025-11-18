package backend.domain;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


import backend.api.bars.BarRepository;
import backend.api.foos.FooRepository;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.jpa.repository.query.MutableSpecification;
import io.u2ware.common.data.jpa.repository.query.PartTreeSpecification;

@SpringBootTest
@AutoConfigureMockMvc
public class U2wareCommonDataTests {

    protected Log logger = LogFactory.getLog(getClass());


    @Autowired
    protected FooRepository fooRepository;


    @Autowired
    protected BarRepository barRepository;

    @Test
    void contextLoads() throws Exception {

//	Example<?> example = Example.of(f);
//	Object value = example.getProbe();
//	Class<?> type = example.getProbeType();
//
//	DirectFieldAccessFallbackBeanWrapper beanWrapper = new DirectFieldAccessFallbackBeanWrapper(value);
//	ExampleMatcher matcher = example.getMatcher();
//	ExampleMatcherAccessor exampleAccessor = new ExampleMatcherAccessor(matcher);
//
//	String currentPath = "";
//	String attribute = "name";
//
//	ExampleMatcher.PropertyValueTransformer transformer = exampleAccessor.getValueTransformerForPath(currentPath);
//	Optional<Object> optionalValue = (Optional)transformer.apply(Optional.ofNullable(beanWrapper.getPropertyValue(attribute)));
//		System.err.println(optionalValue.get());
//		System.err.println(optionalValue.get().getClass());


        fooRepository.save(new Foo("a", "a",1));
        fooRepository.save(new Foo("a", "b",2));
        fooRepository.save(new Foo("a", "c",3));
        fooRepository.save(new Foo("a", "d",4));


        Foo f = new Foo();
        f.setAge(1);
        f.setName("b");

        // Specification<Foo> s0 = new PartTreeSpecification<Foo>( "findByNameAndAge", f);
        // fooRepository.findAll(s0);

        Specification<Foo> s1 = new PartTreeSpecification<Foo>("findByNameIgnoreCase", "XXXX");
        List<Foo> r = fooRepository.findAll(s1);
        logger.info(r);
        // fooRepository.findByNameIgnoreCase("a");



        Collection<String> names = Arrays.asList("b", "c");
        // List<Foo> r1 = fooRepository.findByNameIn(names);
        // logger.info(r1);

        // List<Foo> r5 = fooRepository.findByAgeBetween(2,3);//.findByNameNotIn(names);
        // logger.info(r5);


        List<Foo> r3 = fooRepository.findAll(new PartTreeSpecification<Foo>("findByNameIn", names));
        logger.info(r3);

        List<Foo> r4 = fooRepository.findAll(new PartTreeSpecification<Foo>("findByNameNotIn", names));
        logger.info(r4);


        logger.info("-----------------------------------------------");
        Pageable pageable = PageRequest.of(0,10);

        MutableSpecification<Foo> specification = new MutableSpecification<>();
        JpaSpecificationBuilder.of(Foo.class)
                .where()
                .and().eq("name", "a")
                .build(specification);

        Page<Foo> r5 = fooRepository.findAll(specification, pageable);
        logger.info(r5.getContent());

    }
}
