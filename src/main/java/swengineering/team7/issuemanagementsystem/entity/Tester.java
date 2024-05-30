package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tester")
public class Tester extends User{
        public Tester () {
            setRole("tester");
        }
        public Tester(String id, String username, String password, String contract){
            setId(id);
            setUsername(username);
            setPassword(password);
            setContract(contract);
            setRole("Tester");
        }
}