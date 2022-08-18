package org.example.config;

import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class Initializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{WebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext servletContext){
        try{
            super.onStartup(servletContext);
        } catch (ServletException e){}

        registerHiddenFieldFilter(servletContext);
    }

    public void registerHiddenFieldFilter(ServletContext servletContext){
        servletContext.addFilter(
                "hiddenHttpMethodFilter", new HiddenHttpMethodFilter())
                .addMappingForUrlPatterns(null, true, "/*");
    }
}
