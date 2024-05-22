import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import URLs from '../utils/urls'

const Login = () => {
  const navigate = useNavigate();
  
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loginCheck, setLoginCheck] = useState(false);
  

  const handleLogin = async (event) => {
    event.preventDefault();

    const response = await fetch(
        URLs.LOGIN,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: email,
          password: password,
        }),
      }
    );
    const result = await response.json();
    if (response.status === 200) {
      // Store token in local storage
      await Promise.all([
        sessionStorage.setItem("token", result.token),
        sessionStorage.setItem("email", result.email), // Save userId here
        sessionStorage.setItem("role",result.role),
      ])

      if(result.role==='admin'){
        window.location.href="/admin"
      }
      else{
        window.location.href="/"
      }
      setLoginCheck(false);
    } else {
      setLoginCheck(true);
      navigate('/login')
    }
  };

  return (
    <div className="login-container">
      <form className="login-form" onSubmit={handleLogin}>
        <h1>On&Off</h1>
        <label htmlFor="username">이메일</label>
        <input
          type="text"
          id="username"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <label htmlFor="password">비밀번호</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
         {loginCheck && (
        <label  style={{color: "red"}}>이메일 혹은 비밀번호가 틀렸습니다.</label>
        )}
        <button onClick={handleLogin}>로그인</button>

        <p className="signup-link">
          아직 회원이 아니신가요? <Link to="/signup">회원가입</Link>
        </p>
      </form>
    </div>
  );
};

export default Login;