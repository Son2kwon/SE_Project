package swengineering.team7.issuemanagementsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import swengineering.team7.issuemanagementsystem.entity.Dev;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class IssueManagementSystemApplicationTests {

    @Test
    void testJpa() {
        Dev dev=new Dev();
        dev.incrementResolve("#a#b#c#d#e#f");
        dev.incrementResolve("#a#c#d#f");

        for(String key : dev.getIssueResolve().keySet()) {
            System.out.println(key + ":" + dev.getIssueResolve().get(key));
        }
    }
}
