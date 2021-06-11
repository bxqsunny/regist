<template>
  <div class="app-container">
    <el-table :data="listUser" stripe style="width: 100%">
      <el-table-column prop="username" label="用户名" width="180">
      </el-table-column>
      <el-table-column prop="sex" label="性别" width="180"> </el-table-column>
      <el-table-column prop="phone" label="联系电话"> </el-table-column>
      <el-table-column label="操作" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-button
            @click.native.prevent="updateRow(scope.row)"
            type="text"
            size="small"
          >
            重置密码
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getInfo, searchlistUser, userReset } from "@/api/user";
export default {
  data() {
    return {
      userInfo: {},
      listUser: [],
      userData: {
        page: 1,
        limit: 3,
        data: {},
      },
    };
  },
  methods: {
    userInfomation() {
      let _this = this;
      getInfo().then((res) => {
        _this.userInfo = res;
      });
    },
    listUserInfo() {
      let _this = this;
      searchlistUser(this.userData).then((res) => {
        _this.listUser = res.data;
      });
    },
    updateRow(row) {
      this.$confirm("你确定要重置密码吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        // 确认重置
        userReset(row).then((res) => {
          this.$message({
              message: res.msg,
              type: res.tip ? "success" : "warning",
          });
        });
      });
    },
  },
  created() {
    this.userInfomation();
    this.listUserInfo();
  },
};
</script>