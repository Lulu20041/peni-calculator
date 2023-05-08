package com.example.tamozhpenies.clientSum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tamozhpenies.user.User;

import java.util.List;


@Service
public class ClientSumService {
    final ClientSumRepo clientSumRepo;
    public ClientSumService(ClientSumRepo clientSumRepo) {
        this.clientSumRepo = clientSumRepo;
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
}
