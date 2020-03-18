package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(profiles = {Profiles.POSTGRES_DB, Profiles.DATAJPA})
public class MealServiceDataJPATest extends MealServiceTest {
}
