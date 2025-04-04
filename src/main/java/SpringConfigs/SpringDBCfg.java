package SpringConfigs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("DB_Operations")
@EnableAspectJAutoProxy
public class SpringDBCfg {
}
