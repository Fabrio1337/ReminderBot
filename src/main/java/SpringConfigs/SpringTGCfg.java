package SpringConfigs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"TgBot", "TgBot.MessageActions"})
@EnableAspectJAutoProxy
public class SpringTGCfg {
}
