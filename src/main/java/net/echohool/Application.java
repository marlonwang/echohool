package net.echohool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 *初始运行
 */

@EnableAutoConfiguration
@ImportResource("classpath:spring.xml")
public class Application 
{
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	
    public static void main( String[] args )
    {
        LOGGER.info("studentinfo applicaton is running !");
        SpringApplication.run(Application.class, args);
    }
    
}
