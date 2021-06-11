import request from '@/utils/request'
//所有跟商品规格相关的异步请求


//获取角色列表
export  function detailInfo() {
    return request({
      url: '/removal/detailInfo',
      method: 'get',
    })
  }


//保存角色
export  function emigrationEchart() {
    return request({
      url: '/removal/emigrationEchart',
      method: 'get',
    })
  } 
