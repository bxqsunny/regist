<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="名称">
        <el-input
          v-model="emigrationMap.data.name"
          placeholder="人员名称"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" icon="el-icon-search"
          >查询</el-button
        >
      </el-form-item>
     
    </el-form>

    <el-table
      ref="multipleTable"
      :data="emigrationlList"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column label="户口号" width="100" prop="registid">
      </el-table-column>
      <el-table-column label="名称" width="100" prop="name"> </el-table-column>
      <el-table-column prop="sex" label="性别" width="60">
        <template slot-scope="scope">
          <div>{{ scope.row.sex | sexFormat }}</div>
        </template>
      </el-table-column>
      <el-table-column
        prop="emigrationdata"
        label="迁出日期"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <span>{{ scope.row.emigrationdata | timeFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="identity" label="身份证号" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="communityName" label="迁出地址" show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="迁出前楼层" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{ scope.row.info }}-{{ scope.row.floor }}</div>
        </template>
      </el-table-column>
    </el-table>
     <el-pagination
      style="float: right"
      :page-size="emigrationMap.limit"
      layout="prev, pager, next, jumper"
      :total="total"
      @current-change="handleCurrentChange"
    >
    </el-pagination>
  </div>
</template>

<script>
import { emgirtionList } from "@/api/emigration";
export default {
  data() {
    return {
      emigrationMap:{
          page:1,
          limit:3,
          data:{}
      },
      emigrationlList:[],
      total:0
    };
  },
  methods: {
      searchemgirtionList() {
      let _this = this;
        emgirtionList(this.emigrationMap).then((res) => {
          _this.emigrationlList = res.data;
          _this.total = res.total;
          // console.log(_this.pages)
        });
    },
    handleCurrentChange(val){
      this.emigrationMap.page = val;
      this.searchemgirtionList();
    },
    onSubmit(){
      this.emigrationMap.page = 1;
      this.searchemgirtionList();
    }
  },
  created(){
    this.searchemgirtionList();
  },
  filters:{
    sexFormat(source) {
      if (source == 1) {
        return "男";
      }
      return "女";
    },
  }
};
</script>