package com.revature.advice;

import com.revature.annotations.Authorized;
import com.revature.exceptions.NotLoggedInException;
import com.revature.services.AuthService;
import com.revature.services.AuthServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;

@Aspect
@Component
public class AuthAspect {

    // Spring will autowire a proxy object for the request
    // It isn't a request object itself, but if there is an active request
    // the proxy will pass method calls to the real request
    private final HttpServletRequest req;
    private final AuthService authService;

    public AuthAspect(HttpServletRequest req, AuthService authService) {
        this.req = req;
        // This is black magic
        // How does Spring Boot instantiate authService?
        // Only Spring Boot knows.
        this.authService = authService;
    }

    // This advice will execute around any method annotated with @Authorized
    // If the user is not logged in, an exception will be thrown and handled
    // Otherwise, the original method will be invoked as normal.
    // In order to expand upon this, you just need to add additional
    // values to the AuthRestriction enum.
    // Examples might be "Manager" or "Customer" along with suitable Role
    // values in the User class.
    // Then this method can be expanded to throw exceptions if the user does
    // not have the matching role.
    // Example:
    // User loggedInUser = (User) session.getAttribute("user");
    // Role userRole = loggedInUser.getRole();
    // if(authorized.value().equals(AuthRestriction.Manager) && !Role.Manager.equals(userRole)) {
    //     throw new InvalidRoleException("Must be logged in as a Manager to perform this action");
    // }
    // Then the RestExceptionHandler class can be expanded to include
    // @ExceptionHandler(InvalidRoleException.class)
    // which should return a 403 Forbidden such as:
    // String errorMessage = "Missing required role to perform this action";
    // return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    @Around("@annotation(authorized)")
    public Object authenticate(ProceedingJoinPoint pjp, Authorized authorized) throws Throwable {
        // I originally tried @CookieValue from org.springframework.web.bind.annotation.CookieValue
        // but it didn't cooperate, so I went with the simpler solution
        // using HttpServletRequest req.
        
        HttpServletRequest request 
            = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        
        AuthService aServe = authService;
        //We now use cookies, not sessions!

        //Get the username and password cookies
        String username = null;
        String password = null;
        
        Cookie[] cookies = request.getCookies();
        
        Cookie usernameCookie = WebUtils.getCookie(request, "user");
        Cookie passwordCookie = WebUtils.getCookie(request, "auth");
        
        // If the user is not logged in
        if(usernameCookie == null || passwordCookie == null 
                || !aServe.findByCredentials(usernameCookie.getValue(), passwordCookie.getValue()).isPresent()) {
            throw new NotLoggedInException("Must be logged in to perform this action."
                    + " Username: " + usernameCookie
                    + " password: " + passwordCookie
                    + " path: " + request.getPathInfo());
        }

        return pjp.proceed(pjp.getArgs()); // Call the originally intended method
    }
}
