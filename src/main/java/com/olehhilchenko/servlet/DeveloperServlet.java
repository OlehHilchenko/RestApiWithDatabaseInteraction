package com.olehhilchenko.servlet;

import com.google.gson.Gson;
import com.olehhilchenko.controller.GenericController;
import com.olehhilchenko.model.Account;
import com.olehhilchenko.model.Developer;
import com.olehhilchenko.model.Skill;
import com.olehhilchenko.service.GenericService;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class DeveloperServlet extends HttpServlet {

    private static final Developer d = new Developer();
    private static GenericService developerService;

    static {
        developerService = GenericController.getService(d);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        long id = parserURL(req.getRequestURI());
        PrintWriter out = resp.getWriter();

        if (id > 0) {
            Developer developer = (Developer) developerService.getById(id);
            if (developer == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.println(developerProxyHibernateToDeveloperToString(developer));
        } else {
            List<Developer> developers = developerService.getAll();
            if (developers == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            for (Developer dev : developers) {
                out.println(developerProxyHibernateToDeveloperToString(dev));
                out.flush();
            }
        }
    }

    private String developerProxyHibernateToDeveloperToString(Developer d) {
        Developer result = new Developer();
        Gson gson = new Gson();

        result.setId(d.getId());
        result.setFirstName(d.getFirstName());
        result.setLastName(d.getLastName());
        Account account = new Account(d.getAccount().getId(), d.getAccount().getAccountData());
        result.setAccount(account);
        Set<Skill> skills = new HashSet<>();
        for (Skill skill : d.getSkills()) {
            skills.add(new Skill(skill.getId(), skill.getName()));
        }
        result.setSkills(skills);
        return gson.toJson(result);
    }

    private long parserURL(String url) {
        StringTokenizer stringTokenizer = new StringTokenizer(url, "/");
        stringTokenizer.nextToken();
        if (stringTokenizer.hasMoreTokens()) {
            return Long.parseLong(stringTokenizer.nextToken());
        } else {
            return 0;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inline = "";
        try (Scanner sc = new Scanner(req.getInputStream());) {
            while (sc.hasNext()) {
                inline += (sc.nextLine());
            }
        }

        Gson gson = new Gson();
        Developer developer = gson.fromJson(inline, Developer.class);

        developerService.update(developer);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String inline = "";
        try (Scanner sc = new Scanner(req.getInputStream());) {
            while (sc.hasNext()) {
                inline += (sc.nextLine());
            }
        }

        Gson gson = new Gson();
        Developer developer = gson.fromJson(inline, Developer.class);

        long id = developerService.save(developer);
        if (id > 0)
            resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long id = parserURL(req.getRequestURI());
        if (id > 0) {
            Developer deletedDeveloper = (Developer) developerService.getById(id);
            if (deletedDeveloper == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                developerService.deleteById(deletedDeveloper);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
