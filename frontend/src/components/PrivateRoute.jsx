import { Navigate, Outlet } from "react-router-dom";

const isLogin = !!sessionStorage.getItem("token");

const PrivateRoute = ({login2login, component:Component}) => {
  //login2login(true): 로그인한 상태에서 로그인 페이지로 이동하는 경우
  if(login2login){
    //console.log(sessionStorage.getItem('token'))
    return isLogin ? <Navigate to="/"/>: <Component/>;
  }
  //else: 로그인하지 않은 상태에서 로그인한 페이지로 접근하는 경우
  else
    return isLogin ? <Outlet /> : <Navigate to="/login" />;
};

export default PrivateRoute;