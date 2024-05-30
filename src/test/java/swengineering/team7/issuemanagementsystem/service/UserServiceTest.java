package swengineering.team7.issuemanagementsystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.Admin;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown(){}

    @Test
    void testGetAllUser (){
        Admin admin = new Admin("id","admin","password","contract");
        User user1 = new User();
        user1.setId("1");

        User user2 = new User();
        user2.setId("2");

        User user3 = new User();
        user3.setId("3");

        User user4 = new User();
        user4.setId("4");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);

        List<UserInformationDTO> ans = new ArrayList<>();
        ans.add(UserInformationDTO.from(user1));
        ans.add(UserInformationDTO.from(user2));
        ans.add(UserInformationDTO.from(user3));
        ans.add(UserInformationDTO.from(user4));

        when(userRepository.findById("id")).thenReturn(Optional.of(admin));
        when(userRepository.findAll()).thenReturn(users);

        List<UserInformationDTO> get = userService.getAllUser(admin.getId());
        for(int i=0;i<4;i++)
        {
            assertEquals(ans.get(i).getId(), get.get(i).getId());
        }
    }
}
