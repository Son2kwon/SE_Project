package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    protected String id;

    @Column(unique = true)
    private String username;
    private String password;
    private String role;
    private String Contract;
    public User(){}
    public User(String id, String username, String password, String contract){
        this.id = id;
        this.username=username;
        this.password=password;
        this.Contract=contract;
        //this.role="admin";
    }
    // User:Project 다:다     두 엔터티 연결하는 중간 테이블 생성
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> inchargeProjects = new HashSet<>();

    // User:Issue 1:다   create
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    private Set<Issue> issues = new HashSet<>();

    // User:Issue 1:다   Assign
    @ManyToMany(mappedBy = "assignedUsers")
    private Set<Issue> assignedIssues = new HashSet<>();

    // User:Comment 1:다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @ElementCollection
    private Map<String,Integer> IssueResolve = new HashMap<>();

    public void incrementResolve(String tag) {
        if(tag!=null){
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
    }

    public Map<String,Integer> getIssueResolve() { return IssueResolve; }

    public static User makeUserOf(String id, String username, String password, String role, String contract) {
        User user = new User();
        user.id = id;
        user.username = username;
        user.password = password;
        user.role = role;
        user.Contract = contract;
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
  
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    //Getter & Setter
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public String getPassword() {
        return password;
    }

    public String getContract() {
        return Contract;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public Set<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(Set<Issue> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Project> getInchargeProjects() {
        return inchargeProjects;
    }

    public void setInchargeProjects(Set<Project> inchargeProjects) {
        this.inchargeProjects = inchargeProjects;
    }

    public void setPassword(String passowrd) {
        this.password = passowrd;
    }

    public void setContract(String contract) {
        this.Contract=contract;
    }

    public void addIssue(Issue newissue) { this.issues.add(newissue); };

    public void removeIssue(Issue target) {
        issues.remove(target);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }


    public void addinchargeProject(Project project){
        inchargeProjects.add(project);
    }

    public void removeinchargeProjects(Project project){
        inchargeProjects.remove(project);
    }

}
