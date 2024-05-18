package swengineering.team7.issuemanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan(basePackages = {"swengineering.team7.issuemanagementsystem"})
@SpringBootApplication
public class IssueManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(IssueManagementSystemApplication.class, args);
    }

}
