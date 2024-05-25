import { useNavigate } from "react-router-dom";

const LogOut = () => {
    const navigate = useNavigate();
	const handleLogout = () => {
    	// 로그아웃 처리 로직을 구현합니다.

    	sessionStorage.removeItem("token");
    	sessionStorage.removeItem("email");
    	sessionStorage.removeItem("role");
    	sessionStorage.removeItem("storeid");
    	// 페이지 이동
    	window.location.href='/login'
  	};
  
  return (
	<button onClick={handleLogout}>로그아웃</button>
	);
};
export default LogOut