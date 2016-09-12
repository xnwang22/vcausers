package org.nbme.vca.users.IT;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nbme.vca.users.TestConfig;
import org.nbme.vca.users.config.AdConfig;
import org.nbme.vca.users.model.AdUser;
import org.nbme.vca.users.model.PasswordProfile;
import org.nbme.vca.users.model.SignInName;
import org.nbme.vca.users.model.Vca2AdUser;
import org.nbme.vca.users.services.VcaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by rwang on 8/15/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = { AdConfig.class, TestConfig.class})
public class UserServiceIT {

    @Autowired
    VcaUserService vcaUserService;

    @Ignore
    public void testCreateUser()
    {
        PasswordProfile passwordProfile = new PasswordProfile("Nbme123$", false);
        List<SignInName> signInNames = new ArrayList<>();
        signInNames.add(new SignInName("userName","TestUser2"));
        signInNames.add(new SignInName("emailAddress","TestUser2@nbme.org"));
        Vca2AdUser vcaUser = new Vca2AdUser(false, "LocalAccount", "Test User 2", passwordProfile,  signInNames);
        AdUser user = vcaUserService.addUser(vcaUser);
        System.out.println("User " + user.getDisplayName() + " added ");

    }

    @Ignore
    public void testUpdateUser()
    {
        PasswordProfile passwordProfile = new PasswordProfile("Nbme1234", false);
        List<SignInName> signInNames = new ArrayList<>();
        signInNames.add(new SignInName("userName","TestUser2"));
        signInNames.add(new SignInName("emailAddress","TestUser2@nbme.org"));
        Vca2AdUser vcaUser = new Vca2AdUser(false, "LocalAccount", "Test User 2", passwordProfile,  signInNames);
        AdUser user = vcaUserService.updateUser(vcaUser);
        System.out.println("User " + user.getDisplayName() + " added ");

    }

    @Test
    public void testGetUser()
    {
        String userName ="32d3332d-81be-4a05-81a2-21c87a4d1dc4@vcab2cnbme.onmicrosoft.com";
        AdUser adUser = vcaUserService.getUserInfo(userName);
        System.out.println( adUser );
    }

    @Ignore
    public void testGetUsers()
    {
        List<AdUser> adUsers = vcaUserService.getUsers();
        adUsers.parallelStream().forEachOrdered(i->System.out.println(MessageFormat.format("userName = {0}, userPrinciple= {1}", i.getDisplayName(), i.getUserPrincipalName() )));
    }

    @Ignore
    public void testDeleteUser()
    {

        String userName = "32d3332d-81be-4a05-81a2-21c87a4d1dc4@vcab2cnbme.onmicrosoft.com";
        vcaUserService.deleteUser(userName);
        System.out.println("User " + userName + " deleted ");

    }
    @Test
    public void testPatchUser()
    {

        String userName ="User4";
        vcaUserService.patchUser(userName, "{}");
        System.out.println("User " + userName + " deleted ");

    }

}