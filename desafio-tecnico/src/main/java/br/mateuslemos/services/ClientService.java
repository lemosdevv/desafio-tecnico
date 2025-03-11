package br.mateuslemos.services;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.mateuslemos.entity.Client;
import br.mateuslemos.repository.ClientRepository;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repopsitory;

    private static final String CEP_REGEX = "^[0-9]{5}-[0-9]{3}$";

    public Client saveClient(Client client) {
        validateClient(client);
        return repopsitory.save(client);
    }

    public List<Client> listClients() {
        return repopsitory.findAll();
    }

    public Client findClientById(Long id) {
        return repopsitory.findById(id).orElse(null);
    }

    public void deleteClient(Long id) {
        repopsitory.deleteById(id);
    }

    private void validateClient(Client client) {
        if(client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        if(client.getAddress() == null || client.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }

        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        if(!isValidEmail(client.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        if(client.getCep() == null || client.getCep().isEmpty()) {
            throw new IllegalArgumentException("CEP is required");
        }
        if (!isValidCep(client.getCep())) {
            throw new IllegalArgumentException("Invalid CEP");   
        }
    }
            private boolean isValidEmail(String email) {
                return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
            }

            private boolean isValidCep(String cep) {
                return Pattern.matches(CEP_REGEX, cep);
            }


}
