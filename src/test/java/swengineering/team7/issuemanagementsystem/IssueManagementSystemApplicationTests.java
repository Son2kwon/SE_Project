package swengineering.team7.issuemanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IssueManagementSystemApplicationTests {

    @Autowired
    private UserRepository siteUserRepository;

    @Test
    void testJpa() {
        User user = new User();
        user.setUsername("1");
        user.setPassword("1");
        user.setEmail("1@gmail.com");
        user.setPhonenumber("010-1234-1234");
        user.setRole(1);
        this.siteUserRepository.save(user);

        User user2 = new User();
        user2.setUsername("2");
        user2.setPassword("2");
        user2.setEmail("2@gmail.com");
        user2.setPhonenumber("010-3333-7777");
        user2.setRole(0);
        this.siteUserRepository.save(user2);
    }
}
