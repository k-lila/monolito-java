package services;

import dao.IClientDAO;
import domain.Client;
import exceptions.DAOException;
import exceptions.ServiceException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import services.generic.GenericService;

@Stateless
public class ClientService extends GenericService<Client, Long> implements IClientService {
    private final IClientDAO clientDAO;

    @Inject
    public ClientService(IClientDAO iClientDAO) {
        super(iClientDAO);
        this.clientDAO = iClientDAO;
    }

    @Override
    public Client searchByCPF(String cpf) throws DAOException, ServiceException{
        try {
            return clientDAO.searchByCPF(cpf);
        } catch(Exception e) {
            return null;
        }
    }
}
