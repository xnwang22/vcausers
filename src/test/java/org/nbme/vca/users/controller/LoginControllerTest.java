package org.nbme.vca.users.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.nbme.vca.users.TestConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Created by rwang on 8/15/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class})
public class LoginControllerTest {
    @Spy
    private RestTemplate restTemplate;
    @InjectMocks
    private LoginController loginController = new LoginController();
    @Test
    public void testGetAccessTokenFromRest()
    {

        String authenticationResult = loginController.getAccessTokenFromRest();
        System.out.println(authenticationResult);
    }

    /*Reading [java.lang.String] as "application/json;charset=utf-8" using [org.springframework.http.converter.StringHttpMessageConverter@75459c75]
{"token_type":"Bearer",
"scope":"user_impersonation",
"expires_in":"3599",
"ext_expires_in":"0","expires_on":"1471284560",
"not_before":"1471280660",
"resource":"https://rwangtempnbme.onmicrosoft.com/VCA_rest_api",
"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ik1uQ19WWmNBVGZNNXBPWWlKSE1iYTlnb0VLWSIsImtpZCI6Ik1uQ19WWmNBVGZNNXBPWWlKSE1iYTlnb0VLWSJ9.eyJhdWQiOiJodHRwczovL3J3YW5ndGVtcG5ibWUub25taWNyb3NvZnQuY29tL1ZDQV9yZXN0X2FwaSIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0LzA0YWY5NTExLTRlN2ItNDM4MC04M2RlLTE5MjNhNjczNWI0YS8iLCJpYXQiOjE0NzEyODA2NjAsIm5iZiI6MTQ3MTI4MDY2MCwiZXhwIjoxNDcxMjg0NTYwLCJhY3IiOiIxIiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6ImI5NjYxZjc1LTZhODQtNDA0OS1hMDQxLWNlMDM3N2MzN2Y1YyIsImFwcGlkYWNyIjoiMSIsImZhbWlseV9uYW1lIjoiV2FuZyIsImdpdmVuX25hbWUiOiJSb2JpbiIsImlwYWRkciI6IjY1LjIwNy44NS4xNiIsIm5hbWUiOiJSd2FuZ1VzZXIiLCJvaWQiOiJlMGRhMjIyYy1jYWVjLTRmMGUtOWEwNC0yNjY3MmVlYTg0MDgiLCJyb2xlcyI6WyJOQk1FX0FETUlOIl0sInNjcCI6InVzZXJfaW1wZXJzb25hdGlvbiIsInN1YiI6ImlKbjU5Y2NCMkVNR0dQSlk0UTJrLTU0emdzMFBidzBtX2dlNXpOLXhiRlEiLCJ0aWQiOiIwNGFmOTUxMS00ZTdiLTQzODAtODNkZS0xOTIzYTY3MzViNGEiLCJ1bmlxdWVfbmFtZSI6InJ3YW5nQHJ3YW5ndGVtcG5ibWUub25taWNyb3NvZnQuY29tIiwidXBuIjoicndhbmdAcndhbmd0ZW1wbmJtZS5vbm1pY3Jvc29mdC5jb20iLCJ2ZXIiOiIxLjAifQ.REPcLYQydIvUgXSoG6xqsaHiDgV_wo3F4htU2qB-6ukJtk7bgqSDEiMLeHOihaQnrEuf0vpiwyRIPf_G98VMrMeSFpZPC5knVzlsdUxVpOkKTlXOphGfY3gbr5fBcWDRPCE8HjbfgwLyT9jKCp7foc4bltD5YgD71J5NCo_dqa9dtNqpcKc50n_ET07-KznIZLDBPORhFt23aqHBJjYLqJILxCdn13eO_7QhCB4D7rhOcDez4dwvfH5wq95-g5NW4ZZtqgsXsYrMv51Bb3BKVp3ZZVhAlZPGHEWJJBK7PYAn8oX7w4YUDmCMWukYPtuALEnxDEsUyMzuCwGudJETDA",
"refresh_token":"AQABAAAAAADRNYRQ3dhRSrm-4K-adpCJxLKkL7jPfoRJrbk6GCI69Q2z_SSzMMY3ZF61EN62GFfhoCWvt4vLD8Kk2_VIXYZm2ROS7c3PKsZ9pUPkV-kzzqH6EnXIvsw2UKP1Ujvsfgu1CZ6XvXfdr6BulnYkBh8ae6B-dMebPXaTDOjnXiPcXiluEd0pfumZvnGtV3z45YBilbKS7DeE919VYtXZGi1VtZQw7dGElck_9z9CSaqA-RvQPT9yZDhxkiYZEjJBjWT7b-TbNfbCxyx3zAJl1pJVDExzchYyBNNnc4dGFPvAGU3ZuevuzTYmKLfYOcq9h9cwnO3bCbb7-mfaSNXAvrdUTYxg6rpqQBS4BOm0iykkJFjtgOsb7DIfTwsPkbxyehYRCmKZqDvPW0Nf7RP3d5tVnf42-4k4_O_kX3DI_VhAs-Jcqjd-qWqjRY5jsdp78TOSblstcbg0OY_v7jCFurjKG1QBR7dxZgUGPE8-zKKIUDohOIRiLNqsIbkimruQDxiJx5YV_E1qLNfnvwrlosbCioNl9umsNTZl2dA-n3Pi3A2OgOS67gC6-a--5GS_eRjglnF-d-OyDrfegUDPkJ5n4DnIeruKMn6TlEcbln-DC8RDYfv0RPDg3DmAzQ1hKwcWnv8APgk06M6-GB-OZfRY4xlbKTnhAjfN9i4-Fu6lzFzi5AmVAXA9gaErxj0MiJD_M_Uk2zhBPvJT_Jj3c7l7IAA"}*/
}