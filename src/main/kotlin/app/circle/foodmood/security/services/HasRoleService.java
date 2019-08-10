package app.circle.foodmood.security.services;

import app.circle.foodmood.security.RoleName;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component("hasRoleService")
public class HasRoleService {
    public boolean hasPermission(RoleName... permissions) {
        // loop over each submitted role and validate the user has at least one
        Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (RoleName permission : permissions) {
            if (userAuthorities.contains(new SimpleGrantedAuthority(permission.name())))
                return true;
        }
        return false;
    }
}
