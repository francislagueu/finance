package com.osiris.account.query.api.queries;

import com.osiris.account.query.api.dto.EqualityType;
import com.osiris.account.query.domain.AccountRepository;
import com.osiris.account.query.domain.BankAccount;
import com.osiris.messaging.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountQueryHandler implements QueryHandler{
    private final AccountRepository accountRepository;

    @Autowired
    public AccountQueryHandler(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<BankAccount> bankAccounts = accountRepository.findAll();
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        if(bankAccount.isEmpty()){
            return null;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        baseEntityList.add(bankAccount.get());
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        var bankAccount = accountRepository.findByAccountHolder(query.getAccountHolder());
        if(bankAccount.isEmpty()){
            return null;
        }
        List<BaseEntity> baseEntityList = new ArrayList<>();
        baseEntityList.add(bankAccount.get());
        return baseEntityList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountWithBalanceQuery query) {
        return query.getEqualityType() == EqualityType.GREATER_THAN ?
                accountRepository.findByBalanceGreaterThan(query.getBalance()) :
                accountRepository.findByBalanceLessThan(query.getBalance());
    }
}
