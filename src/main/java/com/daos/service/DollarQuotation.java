package com.daos.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DollarQuotation {
    private String buy;
    private String sell;
    private String agency;
    private String name;
    private String variation;
    private String zeroSell;
    private String decimals;
    private String bestBuy;
    private String bestSell;
    private String date;
    private String track;
}
