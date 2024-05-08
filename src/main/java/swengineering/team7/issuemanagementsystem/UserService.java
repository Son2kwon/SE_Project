package swengineering.team7.issuemanagementsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;
import java.util.List;

@Service
public class UserService {
    private final UserRepository siteUserRepository;

    @Autowired
    public UserService(UserRepository siteUserRepository) {
        this.siteUserRepository = siteUserRepository;
    }

    public List<User> findAll() {
        return this.siteUserRepository.findAll();
    }

    private Specification<User> search(String input) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<User> q, CriteriaQuery<?> query,CriteriaBuilder cb) {
                query.distinct(true);
                return cb.like(q.get("username"),"%" + input + "%");
            }
        };
    }
}
