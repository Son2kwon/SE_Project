import { useState,useEffect } from "react";
import axios from "axios";
import URLs from "../utils/urls";
import '../styles/tableStyle.css'
const ManageAccount=()=>{
  const [accountList,setAccountList] = useState([]);

  const fetchData = async() => {
    await axios({
      url: URLs.GetAllUser,
      method: 'get',
      params: {token: sessionStorage.getItem('token')}
    }).then(response=>{
        setAccountList(response.data)
        }
      )
      .catch(error => {
        console.error('Error fetching assignees:', error);
      });
  }
  useEffect(()=>{fetchData()},[])

  const handleDelete=async(event,userID)=>{
    event.preventDefault()
    try{
      await axios.post(URLs.DELAccount,{
        token:sessionStorage.getItem('token'),
        userId: userID,
      })
      fetchData()
    }catch(error){
      console.log("Error fetching account informations")
    }
  }

  return (
    Array.isArray(accountList)&&accountList.length>0 &&
    <div>
      <h2>User Data</h2>
      <table className="table" style={{width:"80%"}}>
        <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Role</th>
            <th>Charge</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {accountList.map((user, index) => (
            <tr key={index}>
              <td>{user.id}</td>
              <td>{user.name}</td>
              <td>{user.role}</td>
              <td>{user.charge}</td>
              <td>
                {user.role === '' && user.charge === '' ? (
                  <button onClick={(e) => handleDelete(e,user.id)}>삭제</button>
                ) : null}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
export default ManageAccount;