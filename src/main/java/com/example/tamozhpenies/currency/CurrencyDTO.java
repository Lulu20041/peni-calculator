package com.example.tamozhpenies.currency;

import java.time.LocalDate;

public record CurrencyDTO(Long Cur_ID, LocalDate Date, String Cur_Abbreviation, int Cur_Scale, String Cur_Name, double Cur_OfficialRate) {
}
