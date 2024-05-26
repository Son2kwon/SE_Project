package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "Dev")
public class Dev extends User{
        @ElementCollection
        private Map<String,Integer> IssueResolve = new HashMap<>();

        public Dev() {
            setRole("dev");
        }
        public void incrementResolve(String tag) {
            String tagset[] = tag.split("#");
            List<String> temp = new ArrayList<>(Arrays.asList(tagset));
            temp.remove(0);
            tagset = temp.toArray(new String[temp.size()]);
            for (String s : tagset) {
                if (IssueResolve.containsKey(s)) {
                    IssueResolve.put(s, IssueResolve.get(s) + 1);
                } else {
                    IssueResolve.put(s, 1);
                }
            }
        }

        public Map<String,Integer> getIssueResolve() { return IssueResolve; }
}
