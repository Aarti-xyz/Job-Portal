package com.jobportal.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jobportal.entity.Company;
import com.jobportal.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repo;
    public List<Company> getAllCompanies() {
        return repo.findAll();
    }
    public void saveCompany(Company company) {
        repo.save(company);
    }
    public void deleteCompany(int id) {
        repo.deleteById(id);
    }
    public Company getCompany(int id) {
        return repo.findById(id).orElse(null);
    }
}
