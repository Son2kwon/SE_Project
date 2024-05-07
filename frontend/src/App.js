import logo from './logo.svg';
import './App.css';
import {useState,useEffect} from 'react'
//import { Link, Route, Switch } from 'react-router-dom';
import { Routes, Route, useNavigate} from 'react-router-dom';
import SignUp from './login/Register'
import Login from './login/Login'
import ProtectedRoute from './login/ProtectedRoute'
import HomePage from './view/HomePage'


function App() {
  const [mode,setMode] = useState('WELCOME');
  const [id, setId] = useState(null);
  const [token, setToken] = useState(localStorage.getItem('token')); // 토큰 상태 관리
  const navigate = useNavigate();
  
  useEffect(() => {
    setToken(localStorage.getItem('token'));
  }, [token]);

  return (
    <div className="App">
      <Routes>
        <Route path ="/signup" element={<SignUp/>}/>
        <Route path="/login" element={<Login/>}/>
        <Route path="/" element={<ProtectedRoute token={token} component={HomePage} />} />
      </Routes>
    </div>
  );
}

export default App;
