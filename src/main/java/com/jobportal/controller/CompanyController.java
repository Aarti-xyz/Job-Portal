package com.jobportal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jobportal.entity.Company;
import com.jobportal.repository.CompanyRepository;

@Controller
public class CompanyController {

    @Autowired
    private CompanyRepository companyRepo;

    // View all companies
    @GetMapping("/admin/companies")
    public String viewCompanies(Model model) {

        model.addAttribute("companies", companyRepo.findAll());

        return "admin/companies";
    }

    // Open Add Company page
    @GetMapping("/admin/add-company")
    public String addCompanyPage(Model model) {

        model.addAttribute("company", new Company());

        return "admin/add-company";
    }

    // Save Company
    @PostMapping("/admin/save-company")
    public String saveCompany(@ModelAttribute Company company) {

        companyRepo.save(company);

        return "redirect:/admin/companies";
    }

    // Open Edit Company page
    @GetMapping("/admin/edit-company/{id}")
    public String editCompany(@PathVariable int id, Model model) {

        Company company = companyRepo.findById(id).orElse(null);

        model.addAttribute("company", company);

        return "admin/edit-company";
    }

    // Update Company
    @PostMapping("/admin/update-company")
    public String updateCompany(@ModelAttribute Company company) {

        companyRepo.save(company);

        return "redirect:/admin/companies";
    }

    // Delete Company
    @GetMapping("/admin/delete-company/{id}")
    public String deleteCompany(@PathVariable int id) {

        companyRepo.deleteById(id);

        return "redirect:/admin/companies";
    }

}
