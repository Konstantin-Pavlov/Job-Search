package kg.attractor.jobsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication

// to avoid this error:
// org.springframework.beans.factory.BeanDefinitionStoreException: I/O failure while processing configuration class [kg.attractor.jobsearch.mapper.CategoryMapperImpl]
// CategoryMapperImpl is in another branch (hibernate branch) at the moment
@ComponentScan(basePackages = "kg.attractor.jobsearch",
		excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "kg\\.attractor\\.jobsearch\\.mapper\\..*"))
//@MapperScan("kg.attractor.jobsearch.mapper")
public class JobSearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobSearchApplication.class, args);
	}

}
