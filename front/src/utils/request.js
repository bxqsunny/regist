import axios from 'axios'

//穿件axios实例
const service = axios.create({
  baseURL:"http://localhost:8080/regist/", //process.env.VUE_APP_BASE_API, 
  timeout: 5000 
})

//配置axios的请求拦截器
service.interceptors.request.use(function(config){
      // 把token放入请求头中
      config.headers["token"] = sessionStorage.getItem("token")
      return config;  
})

// 响应拦截器
service.interceptors.response.use(function(response){

      //取后台返回的数据
      return response.data;

})

export default service
