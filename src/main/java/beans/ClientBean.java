package beans;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import domain.Client;
import exceptions.DAOException;
import exceptions.ServiceException;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import services.IClientService;

@Named("clientBean")
@ViewScoped
public class ClientBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String searchCPF;
    private Client client;

    @Inject
    private IClientService clientService;

    public ClientBean() {
        client = new Client();
    }

    public Client getClient() {
        return client;
    }

    public void clean() {
        client = new Client();
    }

    public void save() throws DAOException, ServiceException {
        client.setCreationDate(Instant.now());
        clientService.register(client);
        clean();
    }

    public void searchByCPF() throws DAOException, ServiceException {
        Client clientFound = clientService.searchByCPF(searchCPF);
        this.client = clientFound;
    }

    public void update() throws DAOException, ServiceException {
        clientService.edit(client);
        clean();
    }

    public void delete() throws DAOException, ServiceException {
        clientService.remove(client);
        clean();
    }

    public List<Client> getAllClients() throws DAOException, ServiceException {
        return (List<Client>) clientService.showAll();
    }

    public String getSearchCPF() {
        return searchCPF;
    }

    public void setSearchCPF(String searchCPF) {
        this.searchCPF = searchCPF;
    }
}
