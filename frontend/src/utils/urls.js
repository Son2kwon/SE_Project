const base_url = 'http://localhost:8080'
const URLs = {
  LOGIN: base_url + '/login',
  REGISTER: base_url + '/register',
  SEARCH: base_url + '/search',
  CREATEPROJECT: base_url + '/createproject',
  GetAllUser: base_url + '/getAllUser',
  GetRole: base_url + '/getRole',
  DELAccount: base_url + '/deleteAccount',
  GetProjectList: base_url + '/getProjectList',
  CreateIssue: base_url +'/createIssue',
  GetDev: base_url+'/getDev',
  AssignDev: base_url +'/assignDev',
  GetIssueCountByDate: base_url + '/getIssueCountByDate',
  GetIssueCountByTag:  base_url + '/getIssueCountByTag',
};
export default URLs;