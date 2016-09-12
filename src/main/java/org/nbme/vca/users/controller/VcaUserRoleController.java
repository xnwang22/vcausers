package org.nbme.vca.users.controller;

import org.nbme.vca.users.model.VcaUserRole;
import org.nbme.vca.users.services.VcaUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by rwang on 8/12/2016.
 */
@RestController
//@RequestMapping(value = "/userRole")
public class VcaUserRoleController {
    private static final Logger logger = LoggerFactory.getLogger(VcaUserRoleController.class);
    private final static String AUTHORITY = "https://login.microsoftonline.com/common/";
    private final static String CLIENT_ID = "b9661f75-6a84-4049-a041-ce0377c37f5c";//"c3d4c489-9be6-4a8d-a5a7-6fc47ca3d2c5";
    private final static String USERNAME = "rwang@rwangtempnbme.onmicrosoft.com";
    private final static String PASSWORD = "Nbme123$";

    @Autowired
    private VcaUserRoleService vcaUserRoleService;

    @RequestMapping(value = "/userRole/{userName}/{groupName}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addUserrole(@PathVariable String userName, @PathVariable String groupName, HttpServletRequest request, Model model) throws Exception {
        {

          vcaUserRoleService.addUserRole(userName, groupName);
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/userRole/{userName}/{groupName}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity deleteUserRole(@PathVariable String userName, @PathVariable String groupName, HttpServletRequest request, Model model) throws Exception {
        {

            vcaUserRoleService.deleteUserRole(userName, groupName);
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/userRole/{userName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserRole(@PathVariable String userName, @PathVariable String groupName, HttpServletRequest request, Model model) throws Exception {
        {

            VcaUserRole vcaUserRole = vcaUserRoleService.getUserRole(userName);
            return new ResponseEntity(vcaUserRole, HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/userRoles/{userName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getUserRoles(@PathVariable String userName, @PathVariable String groupName, HttpServletRequest request, Model model) throws Exception {
        {

            List<VcaUserRole> vcaUserRoles = vcaUserRoleService.getUserRoles(userName);
            return new ResponseEntity(vcaUserRoles, HttpStatus.OK);
        }
    }

}
