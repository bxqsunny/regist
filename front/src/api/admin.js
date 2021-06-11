import request from '@/utils/request'
//所有跟商品规格相关的异步请求


//获取角色列表
export  function templateSearch(data) {
    return request({
      url: '/wxbGoodsTemplate/pageTel',
      method: 'post',
      data   //只能是data
    })
  }


//保存角色
export  function templatesava(wxbGoodsTemplate) {
    return request({
      url: '/wxbGoodsTemplate/savaTemplate',
      method: 'post',
      data:wxbGoodsTemplate   //只能是data
    })
  } 
