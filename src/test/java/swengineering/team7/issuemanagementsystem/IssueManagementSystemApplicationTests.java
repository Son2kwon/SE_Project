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
        String tag="#";
        String tagset[] = tag.split("#");
        List<String> temp_tagset = new ArrayList<>(Arrays.asList(tagset));
        temp_tagset.remove(0);
        tagset = temp_tagset.toArray(new String[temp_tagset.size()]);
    }
}
