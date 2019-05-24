package roboticHand;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import roboticHand.DAO.ActionRepository;
import roboticHand.DAO.QuestionRepository;
import roboticHand.DAO.UserRepository;
import roboticHand.Service.ActionServiceImpl;
import roboticHand.Service.QuestionServiceImpl;
import roboticHand.Service.UserServiceImpl;

/*
Class has configurations for views and beans
 */
//@EnableWebMvc
@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/redirect");
    }

    //Manages view pages for application
    @Bean
    public InternalResourceViewResolver viewResolver() {
      InternalResourceViewResolver resolver = new InternalResourceViewResolver();
      //determines the folder for views
      resolver.setPrefix("/WEB-INF/pages/");
      //determines the suffix (type of view files)
      resolver.setSuffix(".jsp");
      return resolver;
    }

    //Determines resources (like js and css) and its location
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

    }

    //Tell that when calling userRepository, application needs to use UserServiceImpl
    @Bean
    public UserRepository userRepository(){
        return new UserServiceImpl();
    }

    //Tell that when calling actionRepository, application needs to use ActionServiceImpl
    @Bean
    public ActionRepository actionRepository() {
      return new ActionServiceImpl();
    }

    //Tell that when calling questionRepository, application needs to use QuestionServiceImpl
    @Bean
    public QuestionRepository questionRepository() {
        return new QuestionServiceImpl();
    }
}