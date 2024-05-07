import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const LogOut = () => {
    const navigate = useNavigate();
	const handleLogout = () => {
    	// 로그아웃 처리 로직을 구현합니다.

    	localStorage.removeItem("token");
    	localStorage.removeItem("email");
    	localStorage.removeItem("role");
    	localStorage.removeItem("storeid");
    	// 페이지 이동
    	navigate("/login");
  	};
  
  return (
  	<div className="user-profile">
  		<div className="user-logout-btn-container">
        	<button className="user-logout-btn" onClick={handleLogout}>
          		로그아웃
        	</button>
      	</div>
	</div>
	);
};
export default LogOut