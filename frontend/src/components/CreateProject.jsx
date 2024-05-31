//Project field - id,name,startDate,dueDate,set<user>
//id: 유저 input vs 시스템에서 1씩 증가
//user: PL 1명, Dev/Tester N명
import "../styles/CreateProjectForm.css"
import { useState, useEffect } from "react"
import { useNavigate } from 'react-router-dom';
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';
import URLs from '../utils/urls';
import axios from 'axios'
import Select from 'react-select';
import styled from "styled-components";

const StyledSelect = styled(Select)`width:200px`

const hasEmptyValues = (list)=>{
  return list.some(item=>item==='')
}

const CreateProject=()=>{
  const [name,setName] = useState('');
  const [startDate,setStartDate] = useState(new Date());
  const [endDate,setEndDate] = useState(new Date());
  const [userList,setUserList] = useState([])
  const [selectedTesters, setSelectedTesters] = useState([]);
  const [selectedDevs, setSelectedDevs] = useState([]);
  const [selectedPL, setSelectedPL] = useState(null);

  const navigate = useNavigate();

  const checkAllInputFilled=()=>
    name !== "" &&
    selectedPL != null &&
    !hasEmptyValues(selectedTesters) &&
    !hasEmptyValues(selectedDevs) 
  
  const handleCreateProject=async(e)=>{
    e.preventDefault();
    if(!checkAllInputFilled()){
      alert("빈칸을 모두 채워주세요!")
    }
    const relatedUser = {
      pl: selectedPL ? selectedPL.value : '', // pl은 단순 문자열
      tester: selectedTesters.map(tester => tester.value),
      dev: selectedDevs.map(dev => dev.value),
    };
    
    try {
      const response = await axios.post(
        URLs.CREATEPROJECT,
        {
          token: sessionStorage.getItem('token'),
          name: name,
          startDate: startDate,
          dueDate: endDate,
          relatedUser: relatedUser
        },
        {
          headers: {
            "Content-Type": "application/json"
          }
        }
      );
      if (response.status === 201) {
        alert('프로젝트 생성 성공');
        navigate('/admin');
      }
    } catch (error) {
      console.error('에러 발생:', error);
    }
  };

  const fetchData = async() => {
    try{
    const response = await axios({
      url: URLs.GetAllUser,
      method: 'get',
      params: {token: sessionStorage.getItem('token')}
    })
    const idList = response.data.map(item => item.id);
    const uniqueIdList = [...new Set(idList)];
    return uniqueIdList;
  }catch(error){
    console.error('Error fetching assignees:', error);
    return [];
    }
  }

  useEffect(() => {
    const getUsers = async () => {
      const users = await fetchData();
      setUserList(users.map(user => ({ value: user, label: user })));
    };
    getUsers();
  }, []);
  
  const [selectedDropdowns, setSelectedDropdowns] = useState({
    pl:'',
    testers: [''],
    devs: [''],
  })

  const handleSelectChange = (selectedOption, name) => {
    switch (name) {
      case "pl":
        // PL을 선택한 경우 다른 선택된 값을 초기화합니다.
        setSelectedTesters([]);
        setSelectedDevs([]);
        setSelectedPL(selectedOption);
        break;
      case "testers":
        setSelectedTesters(selectedOption);
        break;
      case "devs":
        setSelectedDevs(selectedOption);
        break;
      default:
        break;
    }
  
    // 선택된 값들을 합쳐서 다시 옵션을 필터링합니다.
    const selectedValues = [...selectedTesters.map(option => option.value), ...selectedDevs.map(option => option.value)];
    setUserList(prevList => filterOptions(prevList, selectedValues, selectedPL));
  };

  const filterOptions = (options, selectedTesters, selectedDevs, selectedPL) => {
    const selectedValues = [];
    if (selectedPL && selectedPL.value) {
      selectedValues.push(selectedPL.value);
    }
    if (selectedTesters && Array.isArray(selectedTesters)) {
      selectedValues.push(...selectedTesters.map(option => option.value));
    }
    if (selectedDevs && Array.isArray(selectedDevs)) {
      selectedValues.push(...selectedDevs.map(option => option.value));
    }
    // 선택된 PL 값이 있다면 해당 값을 제외한 옵션 반환
    return options.filter(option => !(selectedPL && selectedPL.value && option.value === selectedPL.value) && 
                                      !selectedValues.includes(option.value));
  };

  return(
    <div className="project-form">
      <h2>프로젝트 생성</h2>
      <form onSubmit={handleCreateProject}>
        <label>이름</label> <input class="title" type="text" onChange={(e)=>setName(e.target.value)}/>
        <br/>시작일 <DatePicker class="date" dateFormat='yyyy년 MM월 dd일' selected={startDate} onChange={date=>setStartDate(date)}/>
        <br/>마감일 <DatePicker class="date" dateFormat='yyyy년 MM월 dd일' selected={endDate} onChange={date=>setEndDate(date)}/>
        <br/>
        <div><label>PL</label>
          <StyledSelect
            name="pl"
            options={filterOptions(userList, selectedTesters.concat(selectedDevs), selectedPL)}
            value={selectedPL}
            onChange={(selectedOption) => handleSelectChange(selectedOption, "pl")}
          />
        </div>
        <div>
          <div>
            <label>Tester</label>
            <StyledSelect
              isMulti
              name="testers"
              options={filterOptions(userList, [], selectedDevs, selectedPL)}
              value={selectedTesters}
              onChange={(selectedOption) => handleSelectChange(selectedOption, "testers")}
            />
          </div>
          <div>
            <label>Dev</label>
            <StyledSelect
              isMulti
              name="devs"
              options={filterOptions(userList, selectedTesters, [], selectedPL)}
              value={selectedDevs}
              onChange={(selectedOption) => handleSelectChange(selectedOption, "devs")}
            />
          </div>
        </div>
        <br/><button type="submit">프로젝트 생성</button>
      </form>
    </div>);
    
}
export default CreateProject