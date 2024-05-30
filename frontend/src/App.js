import './styles/App.css';
import './styles/issueStyle.css'
import {useState,useEffect} from 'react'
import { Routes, Route, Navigate,useNavigate,} from 'react-router-dom';
import SignUp from './login/Register'
import Login from './login/Login'
import Admin from './view/Admin';
import HomePage from './view/HomePage';
import Search from './components/SearchIssue.jsx';
import PrivateRoute from './components/PrivateRoute';
import FallbackRoute from './components/FallbackRoute.tsx'
import CreateProject from './components/CreateProject';
import ManageAccount from './components/ManageAccount.jsx';
import CreateIssue from './components/CreateIssue.jsx';
import IssueStatistics from './components/IssueStatistics/IssueStatistics';

function App() {
  const navigate = useNavigate();
  const [token, setToken] = useState(sessionStorage.getItem('token'));
  const [role, setRole] = useState(sessionStorage.getItem('token')); // 토큰 상태 관리
  
  useEffect(() => {
    setToken(sessionStorage.getItem('token'));
  }, [token]);
  useEffect(() => {
    setRole(sessionStorage.getItem('role'));
  }, [role]);

  return (
    <div className="App">
      <Routes>
        
        <Route path = "/signup" element={<PrivateRoute login2login={true} component={SignUp}/>}/>
        <Route path="/login" element={<PrivateRoute login2login={true} component={Login}/>}/>

        <Route element={<PrivateRoute/>}>
          <Route path="/admin" element={
            <FallbackRoute
              component = {Admin}
              fallback = {Login}
              isAllow={token&&role==='admin'}
            />}
          />
          <Route path="/admin/create-project" element={
            <FallbackRoute
              component = {CreateProject}
              fallback = {Login}
              isAllow={token&&role==='admin'}
            />}
          />
          <Route path="/admin/manage-account" element={
            <FallbackRoute
              component = {ManageAccount}
              fallback = {Login}
              isAllow={token&&role==='admin'}
            />}
          />

          <Route path="/" element={<HomePage/>}/>
          <Route path="/project/:projectId/:projectName" element={
            <>
              <div className="search-container">
                <Search/>
              </div>
              <div className="create-issue-container">
                <CreateIssue/>
              </div>
            </>
          }/>
          <Route path="/issue-analysis" element={
          <IssueStatistics/>
        }/>
        </Route>
        
      </Routes>
    </div>
  );
}

export default App;
