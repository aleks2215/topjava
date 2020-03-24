package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
//@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String getMeals(Model model) {
        int authUserId = SecurityUtil.authUserId();
        model.addAttribute("meals", MealsUtil.getTos(service.getAll(authUserId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/meals/{id}/delete")
    public String deleteMeal(Model model,
                             @PathVariable(value = "id") int id) {
        int authUserId = SecurityUtil.authUserId();
        service.delete(id, authUserId);
        return "redirect:/meals";
    }

    @GetMapping("/meals/{id}/update")
    public String showUpdateMealForm(Model model,
                                     @PathVariable(value = "id") int id) {
        int authUserId = SecurityUtil.authUserId();
        Meal meal = service.get(id, authUserId);
        model.addAttribute("type", "Edit");
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals/add")
    public String showAddMealForm(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("type", "Add");
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals/filter")
    public String filterMeals(Model model,
//                              @RequestParam(value = "startDate") String startDate,
//                              @RequestParam(value = "endDate") String endDate,
//                              @RequestParam(value = "startTime") String startTime,
//                              @RequestParam(value = "endTime") String endTime
                              HttpServletRequest request
    ) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        int authUserId = SecurityUtil.authUserId();
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, authUserId);
        model.addAttribute("meals", MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @PostMapping("/meals/{id}/update")
    public String updateMeal(HttpServletRequest request,
                             @PathVariable(value = "id") int id) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        int authUserId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        service.update(meal, authUserId);
        return "redirect:/meals";
    }

    @PostMapping("/meals/add")
    public String addMeal(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        int authUserId = SecurityUtil.authUserId();
        service.create(meal, authUserId);
        return "redirect:/meals";
    }
}
