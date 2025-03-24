package Aspects;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.ComponentScan;

@Aspect
@ComponentScan("DB_Operations")
public class DBLogging {
}
