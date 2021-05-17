package zone.czh.woi.woim.example.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zone.czh.woi.woim.annotation.EnableWOIM;


@SpringBootApplication
@EnableWOIM //todo 注解开启WOIM
public class WoiWOIMExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WoiWOIMExampleApplication.class, args);
    }
}
