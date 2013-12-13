package pl.edu.uw.dsk.dev.farel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ WebAppContext.class, PersistenceContext.class })
public class FarelApplicationContext { }
