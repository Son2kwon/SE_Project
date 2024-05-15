import { Link } from "react-router-dom";
import LogOut from "../login/LogOut";
const Admin = ()=>{
  return (
    <div>
        <LogOut/><br/>
        <Link to="/admin/create-project">
          <button type="submit">프로젝트 생성</button>
        </Link>
        <Link to="/admin/manage-account">
          <button type="submit">계정 관리</button>
        </Link>

    </div>
  );
}
export default Admin