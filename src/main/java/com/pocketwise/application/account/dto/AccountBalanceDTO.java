package com.pocketwise.application.account.dto;

import java.io.Serializable;

public record AccountBalanceDTO(String name, AccountBalanceAmountDTO balanceAmount, String balanceType)
        implements Serializable {}
