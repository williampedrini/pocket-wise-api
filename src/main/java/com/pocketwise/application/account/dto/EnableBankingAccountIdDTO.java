package com.pocketwise.application.account.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAccountIdDTO(@JsonProperty("iban") String iban) implements Serializable {}
