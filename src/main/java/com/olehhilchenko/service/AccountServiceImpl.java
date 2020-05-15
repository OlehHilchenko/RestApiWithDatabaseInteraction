package com.olehhilchenko.service;

import com.olehhilchenko.model.Account;
import com.olehhilchenko.repository.AccountRepository;
import com.olehhilchenko.repository.hibernate.AccountDAOImpl;

import java.util.List;

public class AccountServiceImpl implements GenericService<Long, Account> {

    private AccountRepository accountRepository = new AccountDAOImpl();

    @Override
    public long save(Account object) {
        return accountRepository.insert(object);
    }

    @Override
    public void update(Account object) {
        accountRepository.update(object);
    }

    @Override
    public Account getById(Long id) {
        return accountRepository.select(id);
    }

    @Override
    public void deleteById(Account object) {
        accountRepository.delete(object);
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.getAll();
    }
}
