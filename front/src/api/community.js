import request from '@/utils/request'

export  function listCommunity() {
  return request({
    url: '/community/listCommunity',
    method: 'get'
  })
} 

// 获取所有的社区信息
export  function communitySearch(data) {
    return request({
      url: '/community/listCommunity',
      method: 'post',
      data
    })
  }
// 获取所有省份信息
  export  function listProvince() {
    return request({
      url: '/province/listProvince',
      method: 'get'
    })
  }
  // 根据省份Id获取对于的城市Id
  export  function listCity(provinceId) {
    return request({
      url: '/city/listCityByProvinceId?provinceId='+provinceId,
      method: 'get'
    })
  }
    // 根据省份Id获取对于的城市Id
    export  function listRegion(cityId) {
      return request({
        url: '/region/listRegionByCityId?cityId='+cityId,
        method: 'get'
      })
    }
 // 保持或者修改社区信息
 export  function saveOrUpdateCommunity(data) {
  return request({
    url: '/community/savaOrUpdateCommunity',
    method: 'post',
    data
  })
}