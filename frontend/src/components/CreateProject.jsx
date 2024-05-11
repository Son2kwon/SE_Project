//Project field - id,name,startDate,dueDate,set<user>
//id: 유저 input vs 시스템에서 1씩 증가
//user: PL 1명, Dev/Tester N명
import {useState} from 'react';
import { useNavigate } from 'react-router-dom';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import URLs from '../utils/urls';

const CreateProject=()=>{
  const [name,setName] = useState('');
  const [startDate,setStartDate] = useState(new Date());
  const [endDate,setEndDate] = useState(new Date());
  const navigate = useNavigate();
  
  const handleCreateProject=async(e)=>{
    e.preventDefault();
    const response = await fetch(
        URLs.CREATEPROJECT,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({name:name,startDate:startDate,endDate:endDate})
        }
      );
    if (response.status === 201) {
        alert('프로젝트 생성')
        navigate('/admin')
    }
  }

  return(
  <div>프로젝트 생성
    <form onSubmit={handleCreateProject}>
      이름 <input type="text"/>
      <br/>시작일 <DatePicker dateFormat='yyyy년 MM월 dd일' selected={startDate} onChange={date=>setStartDate(date)}/>
      <br/>마감일 <DatePicker dateFormat='yyyy년 MM월 dd일' selected={endDate} onChange={date=>setEndDate(date)}/>
      <br/> <button type="submit">생성</button>
    </form>
  </div>);
    
}
export default CreateProject