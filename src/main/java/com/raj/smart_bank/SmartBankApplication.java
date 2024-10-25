package com.raj.smart_bank;

//import io.swagger.v3.oas.annotations.ExternalDocumentation;
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.annotations.info.Contact;
//import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@OpenAPIDefinition(info = @Info(
//        title = "Smart Bank",
//        description = "Backend REST API's for Smart Bank",
//        version = "1.0",
//        contact = @Contact(
//                name = "md mehedi hasan raj",
//                email = "mehedihasanraj007@gmail.com",
//                url = "https://github.com/MehediHasanRaj/smart_bank"
//        )
//),
//        externalDocs = @ExternalDocumentation(
//                description = "Smart Bank documentation",
//                url = "https://github.com/MehediHasanRaj/smart_bank"
//        )
//)
public class SmartBankApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBankApplication.class, args);
    }

}
