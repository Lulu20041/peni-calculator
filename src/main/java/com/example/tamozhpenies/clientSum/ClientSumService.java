package com.example.tamozhpenies.clientSum;

import com.example.tamozhpenies.currency.CurrencyService;
import com.example.tamozhpenies.user.UserService;
import org.springframework.stereotype.Service;
import com.example.tamozhpenies.user.User;

import java.time.LocalDate;
import java.util.List;


@Service
public class ClientSumService {

    final ClientSumRepo clientSumRepo;
    final UserService userService;
    final CurrencyService currencyService;

    public ClientSumService(ClientSumRepo clientSumRepo, UserService userService, CurrencyService currencyService) {
        this.clientSumRepo = clientSumRepo;
        this.userService = userService;
        this.currencyService = currencyService;
    }

    public List<ClientSum> getSums() {
        return clientSumRepo.findAll();
    }

    public List<ClientSum> getSumsOfClient(Long id) {
        return clientSumRepo.findAllByUserId(id);
    }

    public List<ClientSum> getSumsOfClientByName(String username) {
        return clientSumRepo.findAllByUsername(username);
    }

    //Добавить уплату клиента
    public void addSum(LocalDate date, double sum, Long currencyId, String username) {

        User client = userService.getUserByUsername(username);

        ClientSum clientSum = new ClientSum();
        clientSum.setDate(date);
        clientSum.setSum(sum);
        clientSum.setCurrency(currencyService.findCurrencyById(currencyId));

        clientSum.setUser(client);
        client.addClientSum(clientSum);

        clientSumRepo.save(clientSum);
    }

    public void deleteClientSum(Long id) {
        clientSumRepo.deleteById(id);
    }
}
