package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.Entity;

@Entity
@Table(name = "Admin")
public class Admin extends User{
        public Admin() {
            setRole("admin");
        }
}
