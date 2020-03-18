package ru.javawebinar.topjava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfilesResolver;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver implements ActiveProfilesResolver {

    @Autowired
    Environment env;

    @Override
    public String[] resolve(Class<?> aClass) {
        return new String[]{Profiles.getActiveDbProfile()};
//        final String activeProfile = System.getProperty("spring.profiles.active");
//        String[] prif = env.getActiveProfiles();
//        return new String[] { activeProfile == null ? "no" : activeProfile };
    }
}