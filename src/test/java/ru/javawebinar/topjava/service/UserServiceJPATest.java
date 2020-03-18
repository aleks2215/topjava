package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(profiles = {Profiles.POSTGRES_DB, Profiles.JPA})
public class UserServiceJPATest extends UserServiceTest {
}
