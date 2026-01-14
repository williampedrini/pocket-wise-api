package com.pocketwise.application.account.dto;

import java.io.Serializable;

public record TransactionAmountDTO(String currency, String amount) implements Serializable {}
