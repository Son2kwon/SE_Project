import { Link } from "react-router-dom";
import LogOut from "../login/LogOut";
const Admin = ()=>{
  return (
    <div>
        <LogOut/>
        <Link to="/admin/create-project">
            <button type="submit">프로젝트 생성</button>
        </Link>
    </div>
  );
}
export default Admin