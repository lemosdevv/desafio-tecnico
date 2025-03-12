package br.mateuslemos.services;

import br.mateuslemos.entity.Client;
import br.mateuslemos.entity.Address;
import br.mateuslemos.repository.ClientRepository;
import br.mateuslemos.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/%s/json/";

    public Client createClient(Client client) {
        preencherEnderecoPorCep(client);
        return clientRepository.save(client);
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client updateClient(Long id, Client client) {
        if (clientRepository.existsById(id)) {
            preencherEnderecoPorCep(client);
            client.setId(id);
            return clientRepository.save(client);
        } else {
            return null;
        }
    }

    public boolean deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        } else {
            return false; 
        }
    }

    private void preencherEnderecoPorCep(Client client) {
        if (client.getAddress() != null && client.getAddress().getCep() != null && !client.getAddress().getCep().isEmpty()) {
            String url = String.format(VIA_CEP_URL, client.getAddress().getCep());
            ViaCepResponse response = restTemplate.getForObject(url, ViaCepResponse.class);
            if (response != null && response.getLogradouro() != null) {
                Address address = client.getAddress();
                address.setStreet(response.getLogradouro());
                address.setNeighborhood(response.getBairro());
                address.setCity(response.getLocalidade());
                address.setState(response.getUf());
                addressRepository.save(address);
            }         
        }
    }
}

class ViaCepResponse {
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

    public String getLogradouro() { return logradouro; }
    public String getBairro() { return bairro; }
    public String getLocalidade() { return localidade; }
    public String getUf() { return uf; }
}
