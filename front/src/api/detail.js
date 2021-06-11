import request from '@/utils/request'
//所有跟商品规格相关的异步请求


//获取角色列表
export  function searchParticular(data) {
    return request({
      url: '/particular/searchParticular',
      method: 'post',
      data   //只能是data
    })
  }


//保存角色
export  function saveDetail(data) {
    return request({
      url: '/detail/saveDetail',
      method: 'post',
      data:data   //只能是data
    })
  } 
  export  function savaEmigration(data) {
    return request({
      url: '/detail/emigration',
      method: 'post',
      data:data   //只能是data
    })
  } 
  export  function getLevelByCommId(data) {
    return request({
      url: '/level/getLevelByCommId?id='+data,
      method: 'get',
    })
  } 
