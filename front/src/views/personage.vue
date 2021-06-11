<template>
  <div class="app-container">
    <el-form :model="userInfo" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
   <el-form-item label="用户名" prop="username">
    <el-input v-model="userInfo.username" autocomplete="off" style="width:200px"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="password">
    <el-input type="password" v-model="userInfo.password" autocomplete="off" style="width:200px"></el-input>
  </el-form-item>
  <el-form-item label="确认密码" prop="checkPass">
    <el-input type="password" v-model="userInfo.checkPass" autocomplete="off" style="width:200px"></el-input>
  </el-form-item>
  <el-form-item label="手机号" prop="phone">
    <el-input v-model.number="userInfo.phone" style="width:200px"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
    <el-button @click="resetForm('ruleForm')">重置</el-button>
  </el-form-item>
</el-form>
  </div>
</template>

<script>
import { getInfo,updateUser} from "@/api/user";
export default {
  data() {
     var checkAge = (rule, value, callback) => {
        if (!value) {
          return callback(new Error('手机号不能为空'));
        }
       callback();
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value != this.userInfo.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return {
      userInfo: {},
       ruleForm: {
          checkPass: '',
          age: ''
        },
        rules: {
          password: [
            { validator: validatePass, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ],
          phone: [
            { validator: checkAge, trigger: 'blur' }
          ]
        }
      };
  },
  methods: {
    userInfomation() {
      let _this = this;
      getInfo().then((res) => {
        _this.userInfo = res;
        _this.userInfo.password = null;
      });
    },
    submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            updateUser(this.userInfo).then(res=>{
                this.$message({
                  message: res.msg,
                  type: res.tip ? "success" : "warning",
              });
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
  },
  created() {
    this.userInfomation();
  },
};
</script>