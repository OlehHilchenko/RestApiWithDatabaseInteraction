package com.olehhilchenko.servlet;

import com.google.gson.Gson;
import com.olehhilchenko.controller.GenericController;
import com.olehhilchenko.model.Skill;
import com.olehhilchenko.service.GenericService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SkillServlet extends HttpServlet {

    private static GenericService genericService;

    static {
        genericService = GenericController.getService(new Skill());
    }

    private String developerProxyHibernateToDeveloperToString(Skill d) {
        Gson gson = new Gson();
        Skill skill = new Skill(d.getId(), d.getName());
        return gson.toJson(skill);
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        long id = parserURL(req.getRequestURI());
        PrintWriter out = resp.getWriter();

        if (id > 0) {
            Skill skill = (Skill) genericService.getById(id);
            if (skill == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.println(developerProxyHibernateToDeveloperToString(skill));
        } else {
            List<Skill> skills = genericService.getAll();
            if (skills == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            for (Skill a : skills) {
                out.println(developerProxyHibernateToDeveloperToString(a));
                out.flush();
            }
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
        Skill skill = gson.fromJson(inline, Skill.class);

        genericService.update(skill);
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
        Skill skill = gson.fromJson(inline, Skill.class);

        long id = genericService.save(skill);
        if (id > 0)
            resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long id = parserURL(req.getRequestURI());
        if (id > 0) {
            Skill deletedSkill = (Skill) genericService.getById(id);
            if (deletedSkill == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                genericService.deleteById(deletedSkill);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
