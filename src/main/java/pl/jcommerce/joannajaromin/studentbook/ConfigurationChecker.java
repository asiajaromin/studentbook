//package pl.jcommerce.joannajaromin.studentbook;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
////Not working
//@Component
//public class ConfigurationChecker implements InitializingBean {
//
//    @Value("${spring.mail.password}")
//    private String password;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        if (this.password == null || this.password.equals("${spring.mail.password}")){
//            throw new IllegalAccessException("spring.mail.password property must be set");
//        }
//
//    }
//}
