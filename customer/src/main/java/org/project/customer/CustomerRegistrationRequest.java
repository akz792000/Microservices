package org.project.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
