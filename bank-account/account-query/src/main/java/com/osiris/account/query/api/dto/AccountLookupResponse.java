package com.osiris.account.query.api.dto;

import com.osiris.account.common.dto.BaseResponse;
import com.osiris.account.query.domain.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountLookupResponse extends BaseResponse {
    private List<BankAccount> accounts;

    public AccountLookupResponse(String message){
        super(message);
    }
}
