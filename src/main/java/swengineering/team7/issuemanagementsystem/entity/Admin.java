package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Admin")
public class Admin extends User{
        public Admin() {
            setRole("admin");
        }
        public Admin(String id, String username, String password, String contract){
            setId(id);
            setUsername(username);
            setPassword(password);
            setContract(contract);
            setRole("admin");
        }
}
