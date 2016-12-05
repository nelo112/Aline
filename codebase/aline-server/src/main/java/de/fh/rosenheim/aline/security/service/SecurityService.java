package de.fh.rosenheim.aline.security.service;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.domain.User;
import de.fh.rosenheim.aline.model.security.SecurityUser;
import de.fh.rosenheim.aline.security.utils.Authorities;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * This service offers different kinds of authentication.
 * Useful when more complex checks have to be done.
 */
@Service
public class SecurityService {

    private final UserDetailsService userDetailsService;

    public SecurityService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Users can access own data
     * Division Heads can access data of everybody in their division
     * Front Office can access everything
     */
    public boolean canAccessUserData(SecurityUser principal, User data) {
        return principal != null && data != null &&
                (isDivisionHeadForUser(principal, data) || isSelf(principal, data) || isFrontOffice(principal));
    }

    /**
     * Users can book for themselves
     * Front Office can book for everybody
     *
     * @return true if principal is allowed to book or username is null
     */
    public boolean canBookForUser(SecurityUser principal, String username) {
        return (username == null || principal.getUsername().equals(username) || isFrontOffice(principal));
    }

    /**
     * Division Heads can change data of everybody in their division
     * Front Office can change everything
     */
    public boolean canCurrentUserChangeBookingStatus(Booking data) {
        SecurityUser principal = getCurrentUser();
        return principal != null && data != null &&
                (isDivisionHeadForUser(principal, data.getUser()) || isFrontOffice(principal));
    }

    /**
     * Users can delete their own bookings
     * Front Office can delete everything
     */
    public boolean canCurrentUserDeleteBooking(Booking data) {
        SecurityUser principal = getCurrentUser();
        return principal != null && data != null &&
                (isSelf(principal, data.getUser()) || isFrontOffice(principal));
    }

    /**
     * Front Office can access the data
     * if user is the division head of the division (or no division is given) he can access the data
     */
    public boolean canGetDivisionUsers(SecurityUser principal, String division) {
        return isFrontOffice(principal) ||
                (isDivisionHead(principal)
                        && (division == null || division.length() < 1 || principal.getDivision().equals(division))
                );
    }

    /**
     * Is the given principal the division head of the given user
     */
    public boolean isDivisionHeadForUser(SecurityUser principal, User data) {
        return principal.getAuthorities().contains(new SimpleGrantedAuthority(Authorities.DIVISION_HEAD)) &&
                principal.getDivision().equals(data.getDivision());
    }

    /**
     * Does the user data belong to the given principal
     */
    public boolean isSelf(SecurityUser principal, User data) {
        return principal.getUsername().equals(data.getUsername());
    }

    /**
     * Checks if the given principal has front office authorities
     */
    public boolean isCurrentUserFrontOffice() {
        return isFrontOffice(getCurrentUser());
    }

    /**
     * Checks if the given principal has front office authorities
     */
    public boolean isFrontOffice(SecurityUser principal) {
        return principal.getAuthorities().contains(new SimpleGrantedAuthority(Authorities.FRONT_OFFICE));
    }

    /**
     * Checks if the given principal has division head authorities
     */
    public boolean isDivisionHead(SecurityUser principal) {
        return principal.getAuthorities().contains(new SimpleGrantedAuthority(Authorities.DIVISION_HEAD));
    }

    /**
     * Checks if the given principal has TOP_DOG authorities
     */
    public boolean isTopDog(String username) {
        return getUser(username).getAuthorities().contains(new SimpleGrantedAuthority(Authorities.TOP_DOG));
    }

    /**
     * Loads the data of the user in the current securityContext
     */
    private SecurityUser getCurrentUser() {
        return getUser(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private SecurityUser getUser(String username) {
        return (SecurityUser) this.userDetailsService.loadUserByUsername(username);
    }
}
