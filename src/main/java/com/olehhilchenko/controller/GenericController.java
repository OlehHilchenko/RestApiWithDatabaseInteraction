package com.olehhilchenko.controller;

import com.olehhilchenko.model.Account;
import com.olehhilchenko.model.Developer;
import com.olehhilchenko.model.Skill;
import com.olehhilchenko.service.AccountServiceImpl;
import com.olehhilchenko.service.DeveloperServiceImpl;
import com.olehhilchenko.service.GenericService;
import com.olehhilchenko.service.SkillServiceImpl;

public class GenericController {

    public static GenericService getService(Object object){
        if (object instanceof Developer){
            return new DeveloperServiceImpl();
        }else if (object instanceof Account){
            return new AccountServiceImpl();
        }else if (object instanceof Skill){
            return new SkillServiceImpl();
        }else return null;
    }
}
