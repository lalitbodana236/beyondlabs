package com.beyondlabs.security;


import com.beyondlabs.model.Role;

public record UserPrincipal(String userId, Role role) {}

