package pl.edu.uw.dsk.dev.farel.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import pl.edu.uw.dsk.dev.farel.rest.Controllers;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = Controllers.class)
public class WebAppContext { }
