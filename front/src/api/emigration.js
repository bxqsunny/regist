import request from '@/utils/request'
//所有跟商品规格相关的异步请求


//获取角色列表
export  function emgirtionList(data) {
    return request({
      url: '/emigrationview/emgirtionList',
      method: 'post',
      data   //只能是data
    })
  }


