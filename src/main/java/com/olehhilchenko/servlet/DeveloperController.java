package com.olehhilchenko.servlet;

import com.google.gson.Gson;
import com.olehhilchenko.models.Account;
import com.olehhilchenko.models.Developer;
import com.olehhilchenko.models.Skill;
import com.olehhilchenko.service.DeveloperService;
import com.olehhilchenko.service.DeveloperServiceImplement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


public class DeveloperController extends HttpServlet {


    private DeveloperService developerService = new DeveloperServiceImplement();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        long id = parserURL(req.getRequestURI());
        PrintWriter out = resp.getWriter();

        if (id > 0) {
            Developer developer = developerService.get(id);
            if (developer == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.println(developerProxyHibernateToDeveloperToString(developer));
        } else {
            List<Developer> developers = developerService.getDeveloperList();
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

    private long parserURL(String url) throws ServletException {
        StringTokenizer stringTokenizer = new StringTokenizer(url, "/");
        stringTokenizer.nextToken();
        if (stringTokenizer.hasMoreTokens()) {
            return Long.parseLong(stringTokenizer.nextToken());
        } else {
            return 0;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inline = "";
        try (Scanner sc = new Scanner(req.getInputStream());) {
            while (sc.hasNext()) {
                inline += sc.nextLine();
            }
        }

        Gson gson = new Gson();
        Developer developer = gson.fromJson(inline, Developer.class);

        developerService.update(developer);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inline = "";
        try (Scanner sc = new Scanner(req.getInputStream());) {
            while (sc.hasNext()) {
                inline += sc.nextLine();
            }
        }

        Gson gson = new Gson();
        Developer developer = gson.fromJson(inline, Developer.class);

        long id = developerService.add(developer);
        if (id > 0)
            resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = parserURL(req.getRequestURI());
        if (id > 0) {
            Developer deletedDeveloper = developerService.get(id);
            if (deletedDeveloper == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                developerService.remove(deletedDeveloper);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
