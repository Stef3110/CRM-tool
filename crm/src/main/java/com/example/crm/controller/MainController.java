package com.example.crm.controller;

import com.example.crm.dto.CancelSubscriptionCustomerDto;
import com.example.crm.dto.NewContactDto;
import com.example.crm.dto.NewCustomerDto;
import com.example.crm.model.Contact;
import com.example.crm.model.Customer;
import com.example.crm.model.Personnel;
import com.example.crm.repository.ContactRepository;
import com.example.crm.repository.CustomerRepository;
import com.example.crm.repository.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private PersonnelRepository personnelRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ContactRepository contactRepository;


    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/manager/personnels")
    public String personnels(Model model) {
        List<Personnel> emp = personnelRepository.findAllByRole("ROLE_EMP");
        model.addAttribute("emp", emp);
        return "personnels";
    }

    @GetMapping("/emp/customers")
    public String customers(Model model) {
        List<Customer> customers = customerRepository.findAll();
        List<Customer> subscribedCustomers = new ArrayList<>();
        List<Customer> canceledCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.isSubscribed()) {
                subscribedCustomers.add(customer);
            } else if (customer.isCanceled()) {
                canceledCustomers.add(customer);
            }
        }
        model.addAttribute("customers", customers);
        model.addAttribute("subscribedCustomers", subscribedCustomers);
        model.addAttribute("canceledCustomers", canceledCustomers);
        return "customers";
    }


    @GetMapping("/emp/customers/{mid}")
    public String customer(@PathVariable long mid, Model model) {
        Optional<Customer> customer = customerRepository.findById(mid);
        if(customer.isPresent()) {
            model.addAttribute("customer", customer.get());
            return "customerdetail";
        }
        else {
            return "error";
        }
    }


    @GetMapping("/emp/new_customer")
    public String newCustomer(Model model) {
        NewCustomerDto customerDto = new NewCustomerDto();
        model.addAttribute("customerDto", customerDto);
        return "newcustomer";
    }
    @PostMapping("/emp/new_customer")
    public String postNameOrder(@ModelAttribute NewCustomerDto customerDto, Model model) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setGender(customerDto.getGender());
        customer.setCanceled(false);
        customer.setSubscribed(false);
        customerRepository.save(customer);
        customerRepository.flush();
        model.addAttribute("customer", customer);
        return "confirmedcustomer";
    }

    @GetMapping("/emp/subscribed_customers")
    public String subscribed_customers(Model model) {
        List<Customer> customers = customerRepository.findBySubscribed(true);
        model.addAttribute("customers", customers);
        CancelSubscriptionCustomerDto customerDto = new CancelSubscriptionCustomerDto();
        model.addAttribute("customerDto", customerDto);
        return "subscribedcustomers";
    }

    @PostMapping("/emp/cancel_subscribed_customers")
    public String cancelCustomerSub(@ModelAttribute CancelSubscriptionCustomerDto customerDto, Model model) {
        Optional<Customer> optional = customerRepository.findById(customerDto.getId());
        if(optional.isPresent()) {
            Customer customer = optional.get();
            customer.setCanceled(true);
            customer.setSubscribed(false);
            customerRepository.save(customer);
            return "redirect:/emp/subscribed_customers";
        }
        else {
            return "error";
        }
    }


    @GetMapping("/emp/cancelled_customers")
    public String cancelled_customers(Model model) {
        List<Customer> customers = customerRepository.findBySubscribed(false);
        model.addAttribute("customers", customers);
        CancelSubscriptionCustomerDto customerDto = new CancelSubscriptionCustomerDto();
        model.addAttribute("customerDto", customerDto);
        return "cancelledCustomers";
    }

    @PostMapping("/emp/subscribe_customer")
    public String subscribeCustomer(@ModelAttribute CancelSubscriptionCustomerDto customerDto, Model model) {
        Optional<Customer> optional = customerRepository.findById(customerDto.getId());
        if(optional.isPresent()) {
            Customer customer = optional.get();
            customer.setCanceled(false);
            customer.setSubscribed(true);
            customerRepository.save(customer);
            return "redirect:/emp/cancelled_customers";
        }
        else {
            return "error";
        }
    }

    @GetMapping("/emp/new_contact")
    public String newContact(Model model) {
        NewContactDto contactDto = new NewContactDto();
        List<Customer> customers = customerRepository.findAll();
        List<Personnel> personnels = personnelRepository.findAll();
        model.addAttribute("contactDto", contactDto);
        model.addAttribute("customers", customers);
        model.addAttribute("personnels", personnels);
        return "newContact";
    }

    @PostMapping("/emp/new_contact")
    public String postContact(@ModelAttribute NewContactDto contactDto, Model model) {
        Optional<Customer> optional = customerRepository.findById(contactDto.getCustomerId());
        Optional<Personnel> personnelOptional = personnelRepository.findById(contactDto.getPersonnelId());
        Customer customer = optional.get();
        Personnel personnel = personnelOptional.get();
        Contact contact = new Contact();
        contact.setDetails(contactDto.getDetails());
        contact.setCustomer(customer);
        contact.setDate_set(new Date());
        contact.setPersonnel(personnel);
        contactRepository.save(contact);
        contactRepository.flush();
        model.addAttribute("contact", contact);
        return "confirmedContact";
    }
    @GetMapping("/emp/contacts")
    public String contact(Model model) {
        List<Contact> contacts = contactRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "contacts";
    }

    @GetMapping("/emp/contacts/{mid}")
    public String contact(@PathVariable long mid, Model model) {
        Optional<Contact> contact = contactRepository.findById(mid);
        if(contact.isPresent()) {
            model.addAttribute("contact", contact.get());
            return "contactDetails";
        }
        else {
            return "error";
        }
    }


    @GetMapping("/emp/dashboard")
    public String emp_dashboard() {
        return "empDashboard";
    }
    @GetMapping("/manager/dashboard")
    public String manager_dashboard() {
        return "managerDashboard";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
