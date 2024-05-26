package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.entity.User;

public class UserInformationDTO {
    private String id;
    private String password;
    private String name;
    private String role;
    private String contract;

    public UserInformationDTO(String id, String password, String name, String contract) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.contract = contract;
    }

    public UserInformationDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setPassword(String password){this.password = password;}
    public String getPassword(){return password;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContract() {
        return this.contract;
    }

    public void setContract(String contract) {
        this.contract=contract;
    }

    // 엔티티 객체에서 DTO 객체로 변환하는 메소드
    public static UserInformationDTO from(User user) {
        UserInformationDTO userInformationDTO = new UserInformationDTO();
        userInformationDTO.setId(user.getId());
        userInformationDTO.setName(user.getUsername());
        userInformationDTO.setRole(user.getRole());
        userInformationDTO.setContract(user.getContract());
        userInformationDTO.setPassword(user.getPassword());
        return userInformationDTO;
    }
}
