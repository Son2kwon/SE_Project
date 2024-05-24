package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "PL")
public class PL extends User{
        public PL() {
            setRole("PL");
        }
}
