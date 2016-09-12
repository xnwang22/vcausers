package org.nbme.vca.users.controller;


import org.junit.Test;
import org.nbme.vca.users.VcaUsersApplication;
import org.nbme.vca.users.model.VcaUser;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

@SpringApplicationConfiguration(classes = VcaUsersApplication.class)
@TestPropertySource(properties = "security.require-ssl=true")
public class VcaUserControllerTests /*extends SecurityIntegrationTest*/ {
@Test
    public void testDiffFull()
{
    VcaUserController controller = new VcaUserController();
    VcaUser vcaUser1 = new VcaUser();
    vcaUser1.setEmail("vcauser1@nbme.org");
    vcaUser1.setActive(false);
    vcaUser1.setfName("vcauser1");
    vcaUser1.setlName("test1");
    vcaUser1.setuName("vcauser1");

    VcaUser vcaUser2 = new VcaUser();
    vcaUser2.setEmail("vcauser2@nbme.org");
    vcaUser2.setActive(true);
    vcaUser2.setfName("vcauser2");
    vcaUser2.setlName("test2");
    vcaUser2.setuName("vcauser2");
    String jsonString = controller.diff(vcaUser1, vcaUser2);
    String expected="{\"accountEnabled\" : true, \"givenName\" : \"vcauser2\", \"surname\" : \"test2\"}";
    assertEquals(expected, jsonString);

}
    @Test
    public void testDiffRoles()
    {
        VcaUserController controller = new VcaUserController();
        VcaUser vcaUser1 = new VcaUser();
        vcaUser1.setEmail("vcauser1@nbme.org");
        vcaUser1.setActive(false);
        vcaUser1.setfName("vcauser1");
        vcaUser1.setlName("test1");
        vcaUser1.setuName("vcauser1");
        vcaUser1.setRoles(Arrays.asList("admin", "parti", "user"));

        VcaUser vcaUser2 = new VcaUser();
        vcaUser2.setEmail("vcauser2@nbme.org");
        vcaUser2.setActive(true);
        vcaUser2.setfName("vcauser2");
        vcaUser2.setlName("test2");
        vcaUser2.setuName("vcauser2");
        vcaUser2.setRoles(Arrays.asList("parti", "suser"));

        Map<String, List<String>> diffUserRole = controller.diffUserRole(vcaUser1.getRoles(), vcaUser2.getRoles());
       assertTrue(diffUserRole.get("delete").size() == 2);
        assertTrue(diffUserRole.get("add").size() == 1);

    }

}