package SpringConfigs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("TgBot.Services.Schedule")
@EnableScheduling
@EnableAspectJAutoProxy
public class SpringServiceCfg {
    @PostConstruct
    public void init() {
        System.out.println("Планировщик задач включен!");
    }
}
