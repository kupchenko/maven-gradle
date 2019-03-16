package me.kupchenko;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hello world!
 */
@Configuration
public class App {
    private Integer number = 21;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(App.class);
        context.refresh();
        App bean = context.getBean("app", App.class);
        System.out.println("Hello World!");
        System.out.println(bean.number);
    }

    @Bean
    public App getApp() {
        return new App();
    }
}
