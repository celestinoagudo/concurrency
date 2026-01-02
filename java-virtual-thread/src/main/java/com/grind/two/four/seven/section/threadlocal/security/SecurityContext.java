package com.grind.two.four.seven.section.threadlocal.security;

public record SecurityContext(Integer userId, UserRole userRole) {

    public boolean hasPermission(UserRole requiredRole) {
        return userRole.ordinal() <= requiredRole.ordinal();
    }
}
