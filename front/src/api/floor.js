import request from '@/utils/request'
//所有跟商品规格相关的异步请求


//获取楼层信息列表
export  function levelInfo() {
    return request({
      url: '/level/levelInfo',
      method: 'get',
    })
  }
// 获取所有的楼层信息
  export  function allLevle() {
    return request({
      url: '/level/allLevle',
      method: 'get',
    })
  }


// 保存角色
export  function getPageLevel(levelmap) {
    return request({
      url: '/level/getPageLevel',
      method: 'post',
      data:levelmap   //只能是data
    })
  } 
  export  function savaLevel(levelmap) {
    return request({
      url: '/level/savaLevel',
      method: 'post',
      data:levelmap   //只能是data
    })
  } 