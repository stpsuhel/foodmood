package app.circle.foodmood.controller.restController;


import app.circle.foodmood.model.Response;
import app.circle.foodmood.repository.RoleRepository;
import app.circle.foodmood.repository.UserRepository;
import app.circle.foodmood.security.Role;
import app.circle.foodmood.security.RoleName;
import app.circle.foodmood.security.User;
import app.circle.foodmood.security.jwt.JwtProvider;
import app.circle.foodmood.security.request.LoginForm;
import app.circle.foodmood.security.request.SignUpForm;
import app.circle.foodmood.security.response.JwtResponse;
import app.circle.foodmood.utils.PrimaryRole;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }


/*
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    hasRole('" + RoleConstant.Admin + "')
*/


    @PostMapping("/signup")
/*
    @PreAuthorize("hasRole('" + RoleConstant.Admin + "') OR hasRole('" + RoleConstant.ADMIN_WRITE + "')")
*/
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<String>("Fail -> Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<String>("Fail -> Email is already in use!",
                    HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), 2L);


        user.setPhone(signUpRequest.getPhone());


        Set<String> strRoles = signUpRequest.getRole();

        Set<Role> roles = new HashSet<Role>();

        strRoles.forEach(role -> {
            if ("admin".equals(role)) {
                Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                roles.add(adminRole);
                user.setPrimaryRole(PrimaryRole.CompanyManagement);
            } else {
                Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                roles.add(userRole);
            }
        });

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok().body("User registered successfully!");
    }


    @PostMapping("/token")
    public Response<JwtResponse> registerUserToken(@Valid @RequestBody SignUpForm signUpRequest) {
        Response<JwtResponse> response = new Response<JwtResponse>();
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {

            if (!signUpRequest.getFcmToken().isEmpty()) {
                User user = userRepository.getByUsername(signUpRequest.getUsername());

                user.setFcmToken(signUpRequest.getFcmToken());

                userRepository.save(user);
            }

            return getJwtResponseResponse(signUpRequest, response);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            response.setSuccessful(false);

            String[] message = {"Already Exits but password not matched"};
            response.setMessage(message);
            response.setSuccessful(false);
            return response;
        }

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()), 2L);
        user.setPhone(signUpRequest.getPhone());


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            user.setPrimaryRole(PrimaryRole.User);
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
            roles.add(userRole);
        });
        user.setRoles(roles);

        if (!signUpRequest.getFcmToken().isEmpty()) {
            user.setFcmToken(signUpRequest.getFcmToken());
        }
        User userData = userRepository.save(user);

        return getJwtResponseResponse(signUpRequest, response);
    }

    @NotNull
    private Response<JwtResponse> getJwtResponseResponse(@RequestBody @Valid SignUpForm signUpRequest, Response<JwtResponse> response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signUpRequest.getUsername(),
                        signUpRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        response.setSuccessful(true);

        response.setResult(new JwtResponse(jwt));
        response.setSuccessful(true);
        return response;
    }
}
