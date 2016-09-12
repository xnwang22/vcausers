package org.nbme.vca.users.controller;

import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.model.builder.Vca2AdUserBuilder;
import org.nbme.vca.users.services.VcaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by rwang on 8/12/2016.
 * 
 * This controller exposes the user data from the AD. 
 */
@RestController
@RequestMapping(value = "/user/ad")
public class AdUserController {
    private static final Logger logger = LoggerFactory.getLogger(AdUserController.class);
    
    @Autowired
    private VcaUserService vcaUserService;
    
    @Autowired
	private Vca2AdUserBuilder adBuilder;

    /**
     * This method will return the AD user.
     * @param userName
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/{userName:.+}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserInfo(@PathVariable String userName)  {
        {
            AdUser user = vcaUserService.getUserInfo(userName);
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }

}
