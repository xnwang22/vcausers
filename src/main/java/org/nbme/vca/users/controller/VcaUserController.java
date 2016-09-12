package org.nbme.vca.users.controller;

import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.model.VcaUser;
import org.nbme.vca.users.services.VcaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rwang on 8/12/2016.
 */
@RestController
@RequestMapping(value = "/user")
public class VcaUserController {
    private static final Logger logger = LoggerFactory.getLogger(VcaUserController.class);
//    private final static String AUTHORITY = "https://login.microsoftonline.com/common/";
//    private final static String CLIENT_ID = "b9661f75-6a84-4049-a041-ce0377c37f5c";//"c3d4c489-9be6-4a8d-a5a7-6fc47ca3d2c5";

    @Autowired
    private VcaUserService vcaUserService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity createUser(@RequestBody VcaUser vcaUser, HttpServletRequest request, Model model) throws Exception {
        {
            AdUser user = vcaUserService.addUser(vcaUser);
//            AuthenticationResult token = getAccessTokenFromUserCredentials(USERNAME, PASSWORD);//getAccessTokenFromUserCredentials(USERNAME, PASSWORD);
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/{userName:.+}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserInfo(@PathVariable String userName, HttpServletRequest request, Model model) throws Exception {
        {
            AdUser user = vcaUserService.getUserInfo(userName);
            return new ResponseEntity(user, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity updateUser(@PathVariable VcaUser user, HttpServletRequest request, Model model) throws Exception {
        {
            AdUser adUser = vcaUserService.updateUser(user);
            return new ResponseEntity(adUser, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{userName}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteUser(@PathVariable String userName, HttpServletRequest request, Model model) throws Exception {
        {
            vcaUserService.deleteUser(userName);
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }

}
