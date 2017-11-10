package ru.javawebinar.topjava;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.util.Arrays;

@Slf4j
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

//            System.out.println("-----------");
//            UserRepository userRepository = appCtx.getBean(InMemoryUserRepositoryImpl.class);
//            User u = userRepository.getByEmail("AdMiN@Foo.BAR");
//            printUser(u);
//            u = userRepository.getByEmail("uSER@foo.bar");
//            printUser(u);
//            u = userRepository.getByEmail("notFound@foo.bar");
//            printUser(u);
//            System.out.println("-----------");
            /* admin's meals */
            MealRestController restController = appCtx.getBean(MealRestController.class);
            restController.getAll().forEach(System.out::println);
        }

    }

    private static void printUser(User u) {
        if (u!=null)
            System.out.println("User: " + u);
        else
            System.out.println("User is null");
    }
}
