import request from '@/utils/request'
//所有跟商品规格相关的异步请求


//获取角色列表
export  function registSearch(data) {
    return request({
      url: '/resident/listResident',
      method: 'post',
      data   //只能是data
    })
  }


//保存角色
export  function registsava(regist) {
    return request({
      url: '/resident/registsava',
      method: 'post',
      data:regist   //只能是data
    })
  } 
// 查询statue为0的
export  function selectStatue() {
  return request({
    url: '/resident/selectStatue',
    method: 'get',
  })
} 
export  function verification(resident) {
  return request({
    url: '/resident/verification',
    method: 'post',
    data:resident
  })
} 