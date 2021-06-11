<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
       <el-form-item label="社区名称">
        <el-select
          v-model="levelMap.data.communityId"
          placeholder="社区"
          clearable
          filterable
        >
          <el-option
            v-for="(com, index) in listCommunity"
            :key="index"
            :label="com.name"
            :value="com.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="房屋信息">
        <el-select
          v-model="levelMap.data.info"
          placeholder="房屋楼层信息"
          clearable
        >
          <el-option
            v-for="(info, index) in levels"
            :key="index"
            :value="info.info"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit" icon="el-icon-search"
          >查询</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          @click="
            dialogFormVisible = true;
            exitest = 1;
            floorentity = {};
          "
          icon="el-icon-plus"
          >房屋信息</el-button
        >
      </el-form-item>
    </el-form>

    <el-table
      ref="multipleTable"
      :data="floorlList"
      tooltip-effect="dark"
      style="width: 100%"
    >
      <el-table-column label="id" width="100" prop="levelId"> </el-table-column>
      <el-table-column label="楼房信息" prop="info"> </el-table-column>
      <el-table-column label="楼房名称" prop="floor"> </el-table-column>
      <el-table-column label="所属社区" prop="commName"> </el-table-column>
      <el-table-column label="房屋描述" prop="describe"> </el-table-column>
      <el-table-column label="操作" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="updateRow(scope.row)"
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
      :page-size="levelMap.limit"
      layout="prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <el-dialog :title="floorentity.levelId?'修改楼房信息':'添加楼房信息'" :visible.sync="dialogFormVisible">
      <el-form>
        <el-input type="hidden" v-model="floorentity.levelId"> </el-input>
        <el-form-item label="是否已有">
          <el-select
            @change="$forceUpdate()"
            placeholder="选择房屋信息"
            filterable
            v-model="exitest"
          >
            <el-option :value="1" label="已存在"></el-option>
            <el-option :value="2" label="不存在"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="添加房屋信息">
          <div v-if="exitest == 2">
            <el-input
              autocomplete="off"
              v-model="floorentity.info"
              style="width: 70%; margin-left: 8px"
            ></el-input>
          </div>
          <div v-else-if="exitest == 1">
            <el-select
              v-model="floorentity.info"
              placeholder="从户籍中选择迁入人员"
              filterable
            >
              <el-option
                v-for="(level, index) in levels"
                :key="index"
                :value="level.info"
              ></el-option>
            </el-select>
          </div>
        </el-form-item>
           <el-form-item label="添加房屋名称">
            <el-input
              autocomplete="off"
              v-model="floorentity.floor"
              placeholder="2-201"
              style="width:70%; margin-left: 8px"
            ></el-input>
           </el-form-item>
           <el-form-item label="社区名称">
         <el-select placeholder="请选择迁入的社区"
          filterable v-model="floorentity.communityId">
          <el-option
            v-for="(com, index) in listCommunity"
            :key="index"
            :label="com.name"
            :value="com.id"
          ></el-option>
        </el-select>
      </el-form-item>
        <el-form-item>
           <el-form-item label="添加房屋简介">
            <el-input
              autocomplete="off"
               v-model="floorentity.describe"
              placeholder="空气清新"
              style="width:70%; margin-left: 8px"
            ></el-input>
           </el-form-item>
      </el-form-item>
         </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="
            dialogFormVisible = true;
            savefloor();
          "
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getPageLevel, levelInfo,savaLevel } from "@/api/floor";
import { listCommunity } from "@/api/community";
export default {
  data() {
    return {
      dialogFormVisible: false,
      levelMap: {
        page: 1,
        limit: 3,
        data: {},
      },
      exitest: 1,
      floorlList: [],
      total: 0,
      levels: [],
      level: {},
      floorentity: {},
    };
  },
  methods: {
    getPageLevelList() {
      let _this = this;
      getPageLevel(this.levelMap).then((res) => {
        _this.floorlList = res.data;
        _this.total = res.total;
      });
    },
    levelInfo() {
      let _this = this;
      levelInfo().then((res) => {
        _this.levels = res.data;
      });
    },
    onSubmit() {
      this.levelMap.page = 1;
      this.getPageLevelList();
    },
    savefloor(){
      this.listCommunity.forEach((item)=>{
        if(item.id == this.floorentity.communityId){
          this.floorentity.commName = item.name;
        }
      })
        savaLevel(this.floorentity).then(res=>{
             this.$message({
              message: res.msg,
              type: res.tip ? "success" : "warning",
            });
            this.getPageLevelList();
            this.dialogFormVisible = false;
        })
    },
        // 获取社区信息
    listComm(){
      let _this = this;
      listCommunity().then(res=>{
        _this.listCommunity = res.data;
      })
    },
    handleCurrentChange(val){
      this.levelMap.page = val;
       this.getPageLevelList();
    },
    updateRow(row){
      this.floorentity = row;
      this.exitest = 2;
      this.dialogFormVisible = true;
    }
  },
  created() {
     this.listComm();
    this.getPageLevelList();
    this.levelInfo();
  },
};
</script>