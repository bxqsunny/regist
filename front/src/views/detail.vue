<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="名称">
        <el-input
          v-model="detailMap.data.name"
          placeholder="人员名称"
        ></el-input>
      </el-form-item>
      <el-form-item label="房屋信息">
        <el-select
          v-model="detailMap.data.info"
          placeholder="房屋楼层信息"
          clearable
        >
          <el-option
            v-for="(info, index) in floorList"
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
    </el-form>
    <el-table
      ref="multipleTable"
      :data="detailList"
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
        prop="immigrationdata"
        label="迁入日期"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <span>{{ scope.row.immigrationdata | timeFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="identity" label="身份证号" show-overflow-tooltip>
      </el-table-column>
      <el-table-column prop="address" label="家庭地址" show-overflow-tooltip>
      </el-table-column>
      <el-table-column label="所住楼层" show-overflow-tooltip>
        <template slot-scope="scope">
          <div>{{ scope.row.info }}-{{ scope.row.floor }}</div>
        </template>
      </el-table-column>
      <el-table-column label="操作" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="emigration(scope.row)"
            type="text"
            size="small"
          >
            迁出
          </el-button>
          <el-button
            @click.native.prevent="
              detailentity = {};
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
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-size="detailMap.limit"
      layout="prev, pager, next, jumper"
      :total="total"
    >
    </el-pagination>

    <!-- 添加或修改迁入信息 -->
    <el-dialog
      :title="detailentity.detailid ? '修改迁入信息' : '添加迁入信息'"
      :visible.sync="dialogFormVisible"
    >
      <el-form>
        <el-input type="hidden" v-model="detailentity.detailid"> </el-input>
        <el-form-item label="迁入人员信息">
          <div v-if="detailentity.detailId">
            <el-input
              autocomplete="off"
              :disabled="true"
              v-model="detailentity.name"
              style="width: 70%; margin-left: 8px"
            ></el-input>
          </div>

          <div v-else-if="detailentity.detailid == null">
            <el-select
              v-model="detailentity.accountid"
              placeholder="从户籍中选择迁入人员"
              filterable
            >
            <el-option
                v-for="(regist, index) in registStatue"
                :key="index"
                :label="regist.name"
                :value="regist.accountid"
              ></el-option>
            </el-select>
          </div>
        </el-form-item>
        <el-form-item label="居住房屋">
          <el-select
           @change="$forceUpdate()" 
            v-model="detailentity.floor"
            placeholder="选择房屋信息"
            filterable
          >
            <el-option
              v-for="(level, index) in levels"
              :key="index"
              :label="level.info + '-' + level.floor"
              :value="level.levelId"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="
            dialogFormVisible = false;
            savedetail();
          "
          >确 定</el-button
        >
      </div>
    </el-dialog>

     <el-dialog title="迁出管理" :visible.sync="dialogFormVisible2">
    <el-form >
      <el-form-item label="社区名称">
         <el-select placeholder="请选择迁入的社区"
          @change="LevelChange"
          filterable v-model="emigrationMap.commId">
          <el-option
            v-for="(com, index) in listCommunity"
            :key="index"
            :label="com.name"
            :value="com.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动区域" >
         <el-select
          @change="$forceUpdate()" 
            v-model="emigrationMap.floor"
            placeholder="选择房屋信息"
            filterable
          >
            <el-option
              v-for="(level, index) in levels"
              :key="index"
              :label="level.info + '-' + level.floor"
              :value="level.levelId"
            ></el-option>
          </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="dialogFormVisible2 = false">取 消</el-button>
      <el-button type="primary" @click="dialogFormVisible2 = false,saveDetail()">确 定</el-button>
    </div>
  </el-dialog>
  </div>
</template>

<script>
import { searchParticular, saveDetail, savaEmigration,getLevelByCommId } from "@/api/detail";
import { levelInfo, allLevle } from "@/api/floor";
import { selectStatue } from "@/api/regist";
import { listCommunity } from "@/api/community";
export default {
  data() {
    return {
      // 分页搜索条件
      detailMap: {
        page: 1,
        limit: 3,
        data: {},
      },
      emigrationMap:{},
      detailList: [],
      floorList: [],
      comm:[],
      pages: 0,
      total: 0,
      dialogFormVisible: false,
      dialogFormVisible2:false,
      detailentity: {},
      registStatue: [],
      levels: [],
    };
  },
  methods: {
    levelInfo() {
      let _this = this;
      levelInfo().then((res) => {
        _this.floorList = res.data;
      });
    },
    searchParticular() {
      let _this = this;
      searchParticular(this.detailMap).then((res) => {
        _this.detailList = res.data;
        _this.pages = res.pages;
        _this.total = res.total;
        // console.log(_this.pages)
      });
    },
    handleSizeChange() {
      this.detailMap.limit = val;
      this.registSearch();
    },
    handleCurrentChange(val) {
      this.detailMap.page = val;
      this.searchParticular();
    },
    onSubmit() {
      // 点击搜索需要将页面调至第一页
      this.detailMap.page = 1;
      this.searchParticular();
    },
    // 保存迁入人员信息
    savedetail() {
      saveDetail(this.detailentity).then((res) => {
        this.$message({
          message: res.msg,
          type: res.tip ? "success" : "warning",
        });
        this.searchParticular();
      });
    },
    selectStatue() {
      let _this = this;
      selectStatue().then((res) => {
        _this.registStatue = res.data;
      });
    },
    selectLevel() {
      let _this = this;
      allLevle().then((res) => {
        _this.levels = res.data;
      });
    },
    emigration(val) {
      this.emigrationMap.accountid = val.accountId;
      this.emigrationMap.detailid = val.detailid;
      this.dialogFormVisible2 = true;
    },
    updateRow(val) {
      this.detailentity.detailid = val.detailid;
      this.detailentity.floor = val.levelId;
      this.detailentity.name = val.name;
      this.detailentity.beforeComId = val;
      this.dialogFormVisible = true;
    },
   // 根据社区Id，获取楼层信息
    LevelChange(id){
      let _this = this;
      getLevelByCommId(id).then(res=>{
         _this.levels = res.data;
         this.$forceUpdate()
      })
    },
    // 获取社区信息
    listComm(){
      let _this = this;
      listCommunity().then(res=>{
        _this.listCommunity = res.data;
      })
    },
    saveDetail(){

      savaEmigration(this.emigrationMap).then(res=>{
          this.$message({
            message: res.msg,
            type: res.tip ? "success" : "warning",
          });
          this.searchParticular();
      })
    }
  },
  created() {
    this.listComm();
    this.searchParticular();
    this.levelInfo();
    this.selectLevel();
    this.selectStatue();
  },
  filters: {
    timeFormat(source) {
      // localdatatime 时间过滤器
      var date = new Date(source);
      var year = date.getFullYear();
      var month = date.getMonth() + 1;
      var day = date.getDate();
      if (month < 10) {
        month = "0" + month;
      }
      if (day < 10) {
        day = "0" + day;
      }
      return year + "-" + month + "-" + day;
    },
    sexFormat(source) {
      if (source == 1) {
        return "男";
      }
      return "女";
    },
  },
};
</script>