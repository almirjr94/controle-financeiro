package br.com.almir.infrastructure.balance.model;

public record BalanceResponse(
    double profit,
    double waste,
    double balance
) {

}
