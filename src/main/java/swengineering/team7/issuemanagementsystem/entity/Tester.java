package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.Entity;

@Entity
@Table(name = "Tester")
public class Tester extends User{
        public Tester () {
            setRole("tester");
        }
}