//Project field - id,name,startDate,dueDate,set<user>
//id: 유저 input vs 시스템에서 1씩 증가
//user: PL 1명, Dev/Tester N명
import { useState, useEffect } from "react"
import { useNavigate } from 'react-router-dom';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import URLs from '../utils/urls';
import axios from 'axios'

const hasEmptyValues = (list)=>{
  return list.some(item=>item==='')
}

const CreateProject=()=>{
  const [name,setName] = useState('');
  const [startDate,setStartDate] = useState(new Date());
  const [endDate,setEndDate] = useState(new Date());

  const navigate = useNavigate();

  const checkAllInputFilled=()=>
    name !== "" &&
    selectedDropdowns.pl !== "" &&
    !hasEmptyValues(selectedDropdowns.testers) &&
    !hasEmptyValues(selectedDropdowns.devs) 
  
  const handleCreateProject=async(e)=>{
    e.preventDefault();

    if(!checkAllInputFilled()){
      alert("빈칸을 모두 채워주세요!")
      return;
    }

    const selectedDropdownsCopy = {
      pl: selectedDropdowns.pl,
      testers: [...selectedDropdowns.testers],
      devs: [...selectedDropdowns.devs]
    };
    
    try {
      const response = await axios.post(
        URLs.CREATEPROJECT,
        {
          token: sessionStorage.getItem('token'),
          name: name,
          startDate: startDate,
          endDate: endDate,
          related: selectedDropdownsCopy
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      if (response.status === 200) {
        alert('프로젝트 생성 성공');
        navigate('/admin');
      }
    } catch (error) {
      console.error('에러 발생:', error);
    }
  };

  const [plList,setPlList] = useState([])
  const [testerList,setTesterList] = useState([])
  const [devList,setDevList] = useState([])

  useEffect(()=>{
    const fetchData = async()=>{
      try {
          const plResponse = await axios.get(URLs.GETPl);
          const testerResponse = await axios.get(URLs.GETTester);
          const devResponse = await axios.get(URLs.GETDev);
  
          setPlList(plResponse.data);
          setTesterList(testerResponse.data);
          setDevList(devResponse.data);
        } catch (error) {
          console.error('Error fetching data: ', error);
        }
      };
      fetchData();
  }, []);

  const [selectedDropdowns, setSelectedDropdowns] = useState({
    pl:'',
    testers: [''],
    devs: [''],
  })
  const handleAddTesterDropdown = () => {
    setSelectedDropdowns(prevState => ({
      ...prevState,
      testers: [...prevState.testers, '']
    }));
  };

  const handleAddDevDropdown = () => {
    setSelectedDropdowns(prevState => ({
      ...prevState,
      devs: [...prevState.devs, '']
    }));
  };
  const handleRemoveTesterDropdown = (index) => {
    setSelectedDropdowns(prevState => {
      const updatedTesters = [...prevState.testers];
      updatedTesters.splice(index, 1);
      return { ...prevState, testers: updatedTesters };
    });
  };

  const handleRemoveDevDropdown = (index) => {
    setSelectedDropdowns(prevState => {
      const updatedDevs = [...prevState.devs];
      updatedDevs.splice(index, 1);
      return { ...prevState, devs: updatedDevs };
    });
  };

  const handleSelectDropdown = (value, type, index) => {
    setSelectedDropdowns(prevState => {
      if (type === 'pl') {
        return { ...prevState, pl: value };
      } else if (type === 'tester') {
        const updatedTesters = [...prevState.testers];
        updatedTesters[index] = value;
        return { ...prevState, testers: updatedTesters };
      } else if (type === 'dev') {
        const updatedDevs = [...prevState.devs];
        updatedDevs[index] = value;
        return { ...prevState, devs: updatedDevs };
      }
    });
  };

  return(
  <div>프로젝트 생성
    <form onSubmit={handleCreateProject}>
      이름 <input type="text" onChange={(e)=>setName(e.target.value)}/>
      <br/>시작일 <DatePicker dateFormat='yyyy년 MM월 dd일' selected={startDate} onChange={date=>setStartDate(date)}/>
      <br/>마감일 <DatePicker dateFormat='yyyy년 MM월 dd일' selected={endDate} onChange={date=>setEndDate(date)}/>
      
      <div>
      <div>
        <label>PL:</label>
        <select
          onChange={(e) => handleSelectDropdown(e.target.value, 'pl')}
          value={selectedDropdowns.pl}
        >
          <option value="">선택하세요</option>
          {plList.map((pl, index) => (
            <option key={index} value={pl}>{pl}</option>
          ))}
        </select>
      </div>
      <div>
        <label>Tester:</label>
        {selectedDropdowns.testers.map((tester, index) => (
          <div key={index}>
            <select
              onChange={(e) => handleSelectDropdown(e.target.value, 'tester', index)}
              value={tester}
            >
              <option value="">선택하세요</option>
              {testerList.map((testerOption, optionIndex) => (
                <option key={optionIndex} value={testerOption}>{testerOption}</option>
              ))}
            </select>
            {index === selectedDropdowns.testers.length - 1 && (
              <button onClick={handleAddTesterDropdown}>추가</button>
            )}
            {index > 0 && (
              <button type="button" onClick={() => handleRemoveTesterDropdown(index)}>삭제</button>
            )}
          </div>
        ))}
      </div>
      <div>
        <label>Dev:</label>
        {selectedDropdowns.devs.map((dev, index) => (
          <div key={index}>
            <select
              onChange={(e) => handleSelectDropdown(e.target.value, 'dev', index)}
              value={dev}
            >
              <option value="">선택하세요</option>
              {devList.map((devOption, optionIndex) => (
                <option key={optionIndex} value={devOption}>{devOption}</option>
              ))}
            </select>
            {index === selectedDropdowns.devs.length - 1 && (
              <button onClick={handleAddDevDropdown}>추가</button>
            )}
            {index > 0 && (
              <button type="button" onClick={() => handleRemoveDevDropdown(index)}>삭제</button>
            )}
          </div>
        ))}
      </div>
  </div>
      <br/> <button type="submit">생성</button>
    </form>
  </div>);
    
}
export default CreateProject