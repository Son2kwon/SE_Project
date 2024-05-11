import './App.css';
import {useState,useEffect} from 'react'
import { Routes, Route, Navigate,useNavigate,} from 'react-router-dom';
import SignUp from './login/Register'
import Login from './login/Login'
import ProtectedRoute from './components/ProtectedRoute.tsx'
import CreateProject from './components/CreateProject';
import Admin from './view/Admin';
import HomePage from './view/HomePage';

function App() {
  const navigate = useNavigate();
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [role, setRole] = useState(localStorage.getItem('token')); // 토큰 상태 관리
  
  useEffect(() => {
    setToken(localStorage.getItem('token'));
  }, [token]);
  useEffect(() => {
    setRole(localStorage.getItem('role'));
  }, [role]);

  return (
    <div className="App">
      <Routes>
        <Route path ="/signup" element={<SignUp/>}/>
        
        <Route path="/login" element={<Login/>}/>

        <Route path="/admin" element={
            <ProtectedRoute
              component = {Admin}
              fallback = {Login}
              isAllow={token&&role==='admin'}
            />}
        />
        <Route path="/admin/create-project" element={
            <ProtectedRoute
              component = {CreateProject}
              fallback = {Login}
              isAllow={token&&role==='admin'}
            />}
        />

        <Route path="/" element={token ? <HomePage/> : <Navigate to="/login" replace />} />
      </Routes>
    </div>
  );
}

export default App;
