package backend2.backend.service;

import backend2.backend.dtos.CustomerDTO;
import backend2.backend.mappers.CustomerMapper;
import backend2.backend.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo){
        this.repo = repo;
    }

    public List<CustomerDTO> printAllCustomers() {
        System.out.println("printAllCustomers called!");
        return repo.findAll().stream().map(CustomerMapper::customerToCustomerDTODetailed).toList();
    }

    public List<String> printAllCustomersAuthorities2() {
        return repo.findById(2).map(c -> CustomerMapper.customerToCustomerDTODetailed(c).getAuthorities()).orElse(null);
    }

}
