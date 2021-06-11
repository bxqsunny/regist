<template>
  <div class="app-container">
     <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="名称">
        <el-input
          v-model="searchName"
          placeholder="社区名称"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="search()" icon="el-icon-search"
          >查询</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="
            dialogFormVisible = true;
            communityEntity.data = {};
            communityEntity.data.province = {};
            communityEntity.data.city = {};
            communityEntity.data.region = {};
          "
          icon="el-icon-plus"
          >户籍</el-button
        >
      </el-form-item>
    </el-form>


    <el-table
      ref="multipleTable"
      :data="communityList"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column label="社区Id" width="100" prop="id">
      </el-table-column>
      <el-table-column label="社区名称" width="100" prop="name"> </el-table-column>
      <el-table-column prop="describe" label="社区描述" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="address" label="社区地址" show-overflow-tooltip>
         <template slot-scope="scope">
          <div>
            {{scope.row.province.provinceName}}-{{scope.row.city.cityName}}-{{scope.row.region.regionName}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="
              communityEntity.data = {};
            communityEntity.data.province = {};
            communityEntity.data.city = {};
            communityEntity.data.region = {};
              updateRow(scope.row);
            "
            type="text"
            size="small"
          >
            修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      style="float: right"
      @current-change="handleCurrentChange"
      :page-size="communityEntity.limit"
      layout="prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

  <!-- 添加或者修改社区信息 -->
  
<el-dialog :title="communityEntity.data.id?'修改社区信息':'添加社区信息'" :visible.sync="dialogFormVisible">
  <el-form >
    <el-form-item label="社区名称" >
      <el-input autocomplete="off" v-model="communityEntity.data.name" style="width:70%"></el-input>
    </el-form-item>
    <el-form-item label="所属地区" >
      <el-select v-model="communityEntity.data.province.provinceId" 
       placeholder="请选择社区所属省份" filterable style="width:25%" @change="provinceChange">
        <el-option v-for="(pro,index) in provinces" :key="index" 
        :label="pro.provinceName" :value="pro.provinceId"></el-option>
      </el-select>
       <el-select v-model="communityEntity.data.city.cityId" 
       placeholder="请选择社区所属城市" filterable style="width:25%" @change="cityChange">
        <el-option v-for="(ct,index) in citys" :key="index" 
        :label="ct.cityName" :value="ct.cityId"></el-option>
      </el-select>
       <el-select v-model="communityEntity.data.region.regionId" @change="$forceUpdate()"
       placeholder="请选择社区所属区域" filterable style="width:25%">
        <el-option v-for="(rg,index) in regions" :key="index" 
        :label="rg.regionName" :value="rg.regionId"></el-option>
      </el-select>
    </el-form-item>
     <el-form-item label="社区描述" >
       <el-input
       style="width:80%"
        type="textarea"
        :rows="2"
        placeholder="请输入社区描述"
        v-model="communityEntity.data.describe">
      </el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">取 消</el-button>
    <el-button type="primary" @click="savaOrUpdate">确 定</el-button>
  </div>
</el-dialog>

  </div>
</template>

<script>
import {communitySearch,listProvince,listCity,listRegion,saveOrUpdateCommunity  } from "@/api/community";
export default {
  data() {
    return {
        total:0,
        communityList:[],
        searchName:'',
        communityEntity:{
          page:1,
          limit:10,
          data:{
            province:{},
            city:{},
            region:{}
          }
        },
        provinces:[],
        citys:[],
        regions:[],
        dialogFormVisible:false
    };
  },
  methods: {
    search(){
        this.communityEntity.data.name = this.searchName;
        this.searchCommunity();
    },
    // 搜索所有社区信息，包含社区所在地址
    searchCommunity(){
      let _this = this;
      communitySearch(this.communityEntity).then(res=>{
        // console.log(res)
        if(res.data){
          _this.communityList = res.data;
          _this.total = res.total;
        }
      })
    },
    // 当前页该表
    handleCurrentChange(val){
        this.communityEntity.page = val;
        this.searchCommunity();
    },
    // 修改当前行社区的信息
    updateRow(row){
        // console.log(row)
        this.communityEntity.data = row;
        // 获取该社区的区域信息和城市信息
        this.listCity(row.province.provinceId);
        this.dialogFormVisible = true;
    },
    // 获取所有省份信息
    listProvince(){
      let _this = this;
        listProvince().then(res=>{
          _this.provinces = res.data;
          // console.log(_this.provinces)
        })
    },
    // 根据省份Id获取城市Id
    listCity(provinceId){
      // 获取之前，需要将之前的城市信息和区域信息删除
      let _this = this;
      listCity(provinceId).then(res=>{
        _this.citys = res.data;
      })
    },
    // 根据城市Id获取区域信息
    listRegion(cityId){
      // 获取之前，需要将之前的区域信息删除
      let _this = this;
      listRegion(cityId).then(res=>{
        _this.regions = res.data;
      })
    },
    provinceChange(val){
      // 清除实体中的数据
      this.communityEntity.data.city = {},
      this.communityEntity.data.region = {},
      this.listCity(val);
    },
    cityChange(val){
      // 清除实体中的区域信息
      this.communityEntity.data.region = {},
      this.listRegion(val);
    },
    savaOrUpdate(){
      let _this = this;
      saveOrUpdateCommunity(this.communityEntity.data).then(res=>{
         if (res.tip) {
            this.$message({
              type: res.tip,
              message: res.data,
            });
            _this.dialogFormVisible = false;
            _this.searchCommunity();
          }
      })
    }
  },
  created(){
    this.searchCommunity();
    this.listProvince();
  }
};
</script>