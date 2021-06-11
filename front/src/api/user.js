import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/user/userInfo',
    method: 'post',
  })
}
export function searchlistUser(userdata) {
  return request({
    url: '/user/listUser',
    method: 'post',
    data:userdata
  })
}
// 重置用户密码
export function userReset(userdata) {
  return request({
    url: '/user/userReset',
    method: 'post',
    data:userdata
  })
}
export function updateUser(userdata) {
  return request({
    url: '/user/updateUser',
    method: 'post',
    data:userdata
  })
}
export function logout() {
  return request({
    url: '/vue-admin-template/user/logout',
    method: 'post'
  })
}
