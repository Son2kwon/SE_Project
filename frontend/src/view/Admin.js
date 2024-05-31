import "../styles/Admin.css"
import { Link } from "react-router-dom";
import LogOut from "../login/LogOut";

const Admin = ()=>{
  return (
    <div>
      <LogOut/><br/>
      <div className="container">
        <Link to="/admin/create-project" className="button-link">
          <button type="submit" className="button">프로젝트 생성</button>
        </Link>
        <Link to="/admin/manage-account" className="button-link">
          <button type="submit" className="button">계정 관리</button>
        </Link>
        <Link to="/admin/issue-analysis" className="button-link">
          <button type="submit" className="button">이슈 통계</button>
        </Link>
      </div>
    </div>
  );
}
export default Admin