function loginApi(data) {
  return $axios({
    'url': '/employee/login',
    'method': 'post',
    'headers':{
      'Content-Type': 'application/x-www-form-urlencoded;charset:utf-8',
    },
    data
  })
}

function logoutApi(){
  return $axios({
    'url': '/employee/logout',
    'method': 'post',
  })
}
