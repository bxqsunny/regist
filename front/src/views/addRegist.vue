<template>
  <div class="app-container">
     <el-steps :active="active" finish-status="success">
      <el-step title="步骤 1">
      </el-step>
      <el-step title="步骤 2">
      </el-step>
      <el-step title="步骤 3">
      </el-step>
    </el-steps>
    <div v-if="active == 1">
      <!-- 添加户主的信息 -->

    </div>
    <div v-if="active ==2">
      <!-- 添加非户主信息 -->
      nddd
    </div>
    <div v-if="active ==3">
      nidaf
    </div>
    <el-button style="margin-top: 12px;" @click="next">下一步</el-button>

  </div>
</template>

<script>
 export default {
    data() {
      return {
        active: 1
      };
    },

    methods: {
      next() {
        if (this.active++ > 2) this.active = 0;
      }
    }
  }
</script>