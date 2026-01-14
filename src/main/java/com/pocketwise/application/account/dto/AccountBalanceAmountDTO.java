package com.pocketwise.application.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public record AccountBalanceAmountDTO(String currency, BigDecimal amount) implements Serializable {}
