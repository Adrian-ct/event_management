//package com.example.pajproject.EJB;
//
//import com.example.pajproject.model.Company;
//import jakarta.ejb.Stateless;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//import java.util.List;
//
//@Stateless
//public class CompanyService {
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    public Company createCompany(Company company) {
//        try {
//            entityManager.persist(company);
//            return company;
//        } catch (Exception e) {
//            throw new RuntimeException("Error creating company", e);
//        }
//    }
//
//    public Company getCompany(Long id) {
//        try {
//            return entityManager.find(Company.class, id);
//        } catch (Exception e) {
//            throw new RuntimeException("Error retrieving company with id " + id, e);
//        }
//    }
//
//    public List<Company> getAllCompanies() {
//        try {
//            TypedQuery<Company> query = entityManager.createQuery("SELECT c FROM Company c", Company.class);
//            return query.getResultList();
//        } catch (Exception e) {
//            throw new RuntimeException("Error retrieving all companies", e);
//        }
//    }
//
//    public Company updateCompany(Company company) {
//        try {
//            return entityManager.merge(company);
//        } catch (Exception e) {
//            throw new RuntimeException("Error updating company", e);
//        }
//    }
//
//    public void deleteCompany(Long id) {
//        try {
//            Company company = getCompany(id);
//            if (company != null) {
//                entityManager.remove(company);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Error deleting company with id " + id, e);
//        }
//    }
//}