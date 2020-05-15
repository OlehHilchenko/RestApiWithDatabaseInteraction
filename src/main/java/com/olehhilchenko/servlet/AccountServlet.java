package com.olehhilchenko.servlet;

import com.google.gson.Gson;
import com.olehhilchenko.controller.GenericController;
import com.olehhilchenko.model.Account;
import com.olehhilchenko.service.GenericService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class AccountServlet extends HttpServlet {

    private static GenericService genericService;

    static {
        genericService = GenericController.getService(new Account());
    }

    private String developerProxyHibernateToDeveloperToString(Account d) {
        Gson gson = new Gson();
        Account account = new Account(d.getId(), d.getAccountData());
        return gson.toJson(account);
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
            Account account = (Account) genericService.getById(id);
            if (account == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            out.println(developerProxyHibernateToDeveloperToString(account));
        } else {
            List<Account> accounts = genericService.getAll();
            if (accounts == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            for (Account a : accounts) {
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
        Account account = gson.fromJson(inline, Account.class);

        genericService.update(account);
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
        Account account = gson.fromJson(inline, Account.class);

        long id = genericService.save(account);
        if (id > 0)
            resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        long id = parserURL(req.getRequestURI());
        if (id > 0) {
            Account deletedAccount = (Account) genericService.getById(id);
            if (deletedAccount == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                genericService.deleteById(deletedAccount);
                resp.setStatus(HttpServletResponse.SC_OK);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
