package com.example.tamozhpenies.clientSum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.tamozhpenies.user.User;

import java.util.List;


@Service
public class ClientSumService {
    @Autowired
    ClientSumRepo clientSumRepo;
    public List<ClientSum> getSums() {
        return clientSumRepo.findAll();
    }
    public List<ClientSum> getSumsOfClient(Long id) {
        return clientSumRepo.findByUserId(id);
    }
}
