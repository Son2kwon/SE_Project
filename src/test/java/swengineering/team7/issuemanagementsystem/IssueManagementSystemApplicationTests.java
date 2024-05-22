package swengineering.team7.issuemanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IssueManagementSystemApplicationTests {

    @Test
    void testJpa() {
        User user = User.makeUserOf("1","1","1","1");
        User user1 = User.makeUserOf("1","1","1","1");

        Set<User> users = Set.of(user);
        System.out.println(users.contains(user1));
    }
}
