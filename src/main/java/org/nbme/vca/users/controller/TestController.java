package org.nbme.vca.users.controller;

import org.nbme.vca.users.config.AdConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by rwang on 8/12/2016.
 * 
 * This controller is to autheticate users and return tokens from Azure ad.
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    //CONST

    /*@Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;*/

    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    AdConfig adConfig;

    /**
     * This method expects an authoization header that is basic.
     * @param request
     * @param model
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/addVideo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity addVideo(HttpServletRequest request, Model model) throws Exception {
        {

        	logger.info("add video");
        	return new ResponseEntity("ok", HttpStatus.OK);
        }
    }
    @PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/getVideo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getVideo(HttpServletRequest request, Model model) throws Exception {
        {

            logger.info("get video");
            return new ResponseEntity("ok", HttpStatus.OK);
        }
    }

  /*  @RequestMapping( value = "/endPoints", method = RequestMethod.GET )
    public ResponseEntity getEndPointsInView(Model model )
    {
        Set<RequestMappingInfo> mappingInfos = requestMappingHandlerMapping.getHandlerMethods().keySet();
        model.addAttribute( "endPoints", mappingInfos);
        Map<String, String> map = new HashMap<>();
        for(RequestMappingInfo entry : mappingInfos)
        {

            map.put(entry.getPatternsCondition().toString(), entry.getMethodsCondition() + "\t consumes " + entry.getConsumesCondition() + "\t produces " + entry.getProducesCondition() );
            logger.info("Mapping =" + entry.toString());
        }
        return new ResponseEntity(new TreeMap<String, String>(map), HttpStatus.OK);
    }*/


}
