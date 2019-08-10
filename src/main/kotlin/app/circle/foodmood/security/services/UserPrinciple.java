package app.circle.foodmood.security.services;


import app.circle.foodmood.security.Role;
import app.circle.foodmood.security.User;
import app.circle.foodmood.utils.PrimaryRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String username;

    private String email;


    private Long companyId;

    private PrimaryRole primaryRole;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id, String name, Long companyId,
                         String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities, PrimaryRole primaryRole) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.companyId = companyId;
        this.primaryRole = primaryRole;
    }

    public static UserPrinciple build(User user) {
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(),
                user.getName(),
                user.getCompanyId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getPrimaryRole()
        );
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public Long getCompanyId() {
        return companyId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }

    public PrimaryRole getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(PrimaryRole primaryRole) {
        this.primaryRole = primaryRole;
    }
}
