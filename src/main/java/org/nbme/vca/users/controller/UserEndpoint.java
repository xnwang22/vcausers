package org.nbme.vca.users.controller;

import org.nbme.vca.users.sec.CurrentUser;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_USER"})
@RestController
public class UserEndpoint {

    @RequestMapping("/api/user")
    public User getUser(@CurrentUser User user) {
        return user;
    }


}
