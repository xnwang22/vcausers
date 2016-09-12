package org.nbme.vca.users.IT;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nbme.vca.users.TestConfig;
import org.nbme.vca.users.config.AdConfig;
import org.nbme.vca.users.model.VcaUserRole;
import org.nbme.vca.users.services.VcaUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by rwang on 8/15/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { AdConfig.class, TestConfig.class})
public class UserRoleServiceIT {

    @Autowired
    VcaUserRoleService vcaUserRoleService;

    @Ignore
    public void testAddUserRole()
    {

        String user = vcaUserRoleService.addUserRole("rwang@b2c", "Participant");
        System.out.println("User " + user + " added role" );

    }

    @Test
    public void testDeleteUserRole()
    {

         vcaUserRoleService.deleteUserRole("rwang@b2c", "Participant");
        System.out.println("User role deleted" );

    }

    @Test
    public void testGetUserRole()
    {

        VcaUserRole userRole = vcaUserRoleService.getUserRole("rwang@b2c");
        System.out.println("User has role " + userRole);

    }

    @Test
    public void testGetUserRoles()
    {

        List<VcaUserRole> userRoles = vcaUserRoleService.getUserRoles("rwang@b2c");
        userRoles.stream().forEachOrdered(i->System.out.println(i));
    }

}