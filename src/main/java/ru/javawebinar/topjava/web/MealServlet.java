package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.mock.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 19.08.2014
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealRepository repository;
    private ConfigurableApplicationContext appCtx;
    private MealRestController mrc;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mrc = appCtx.getBean(MealRestController.class);
    }

    @Override
    public void destroy() {
        appCtx.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        String idUser = request.getParameter("user");
        String parameter = request.getParameter("startDate");
        LocalDate startDate = null;
        LocalDate endDate = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        System.out.println(idUser);
        if(idUser!=null){
            AuthorizedUser.setId(Integer.parseInt(idUser));
            request.setAttribute("meals", mrc.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }

        if(request.getParameter("startDate")!=null){
            startDate = LocalDate.parse(request.getParameter("startDate"));
            endDate = LocalDate.parse(request.getParameter("endDate"));
        }
        if(request.getParameter("startTime")!=null){
            startTime = LocalTime.parse(request.getParameter("startTime"));
            endTime = LocalTime.parse(request.getParameter("endTime"));
        }

        if(id!= null){
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.valueOf(request.getParameter("calories")));
            meal.setUserId(AuthorizedUser.id());
            LOG.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            if(meal.isNew()){
                mrc.create(meal);
            }else {
                mrc.update(meal);
            }
            response.sendRedirect("meals");
        }
        if(request.getParameter("startDate")!=null){
            LocalDate finalStartDate = startDate;
            LocalDate finalEndDate = endDate;
            request.setAttribute("meals", mrc.getAll().stream().filter(u->(u.getDateTime().toLocalDate().isAfter(finalStartDate))&&
                    u.getDateTime().toLocalDate().isBefore(finalEndDate)).collect(Collectors.toList()));
            request.setAttribute("timeDate1", finalStartDate);
            request.setAttribute("timeDate2", finalEndDate);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        if(request.getParameter("startTime")!=null){
            LocalTime finalEndTime = endTime;
            LocalTime finalStartTime = startTime;
            request.setAttribute("meals", mrc.getAll().stream().filter(u->(u.getDateTime().toLocalTime().isAfter(finalStartTime))&&
                    u.getDateTime().toLocalTime().isBefore(finalEndTime)).collect(Collectors.toList()));
            request.setAttribute("timeFilter1", finalStartTime);
            request.setAttribute("timeFilter2", finalEndTime);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            LOG.info("getAll");
            request.setAttribute("meals", mrc.getAll());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

        } else if ("delete".equals(action)) {
            int id = getId(request);
            LOG.info("Delete {}", id);
            mrc.delete(id);
            response.sendRedirect("meals");

        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = action.equals("create") ?
                    new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                    mrc.get(getId(request));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("meal.jsp").forward(request, response);
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
