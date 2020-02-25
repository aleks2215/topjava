package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    private MealRepository repository = new InMemoryMealRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action == null ? "all" : action) {
            case "all":
                log.info("getAll");
                req.setAttribute("meals", getTos(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
                break;
            case "delete":
                int id = getId(req);
                log.info("Delete {}", id);
                repository.delete(id);
                resp.sendRedirect("meals");
                break;
            case "create":
                Meal mealCreate = new Meal(LocalDateTime.now(), "", 1000);
                req.setAttribute("meal", mealCreate);
                req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
                break;
            case "update":
                Meal mealUpdate = repository.get(getId(req));
                req.setAttribute("meal", mealUpdate);
                req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
                break;
        }
//        req.setAttribute("meals", getMealToList());
//        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    private int getId(HttpServletRequest request) {
        String id = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(id);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.parseInt(req.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        repository.save(meal);
        resp.sendRedirect("meals");
    }
}
