package org.nbme.vca.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by rwang on 8/22/2016.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter
{

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer)
    {
        configurer.favorPathExtension(false);
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer)
    {
        configurer.setUseSuffixPatternMatch(false);
    }
}
