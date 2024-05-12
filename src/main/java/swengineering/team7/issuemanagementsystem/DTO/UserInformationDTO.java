package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.entity.User;

public class UserInformationDTO {
    private Long id;
    private String name;
    private String role;
    private String contract;

    public UserInformationDTO(Long id, String name, String role, String contract) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.contract = contract;
    }

    public UserInformationDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return userInformationDTO;
    }
}
