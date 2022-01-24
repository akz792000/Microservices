package org.project.customer;

import lombok.AllArgsConstructor;
import org.project.clients.fraud.FraudCheckResponse;
import org.project.clients.fraud.FraudClient;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    //private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email()).build();

        // todo: check if email valid
        // todo: check if email not taken

        customerRepository.saveAndFlush(customer);

        /*
        // send message to the fraud module directly
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());
         */

        /*
        // change url to connect to eureka server
        // send message to the fraud with load balanced in config file
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());
        */

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        // todo: send notification
    }

}
