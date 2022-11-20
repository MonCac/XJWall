<template>
    <div class="setOfUserView">
<!--        安全信息绑定区-->
        <div class="safeDataView">
            <!--            表头-->
            <div style=" flex: 2;display: flex;align-items: center;border-bottom: 1px solid #F2F2F2 ">
                <span style="flex: 10;font-size: 18px;font-weight: 600;padding-left: 30px">账号设置</span>
            </div>
            <!--            表身-->
            <div class="showSafeDataViewTwo" style="flex: 8;display: flex;flex-direction: column"  >
                <div style="flex:1;display: flex;align-items: center;">
                    <div style="flex:15; font-size: 14px;color: #555666;margin: 0px 40px;letter-spacing: 1px;">
                        <span >密&ensp;&ensp;码:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 3;letter-spacing: 2px;">
                        <el-link type="primary" style="letter-spacing: 15px;" @click="ChangePassword">修改密码</el-link>
                    </div>
                </div>
                <div style="flex:1;display: flex;align-items: center;letter-spacing: 1px;">
                    <div style="flex:15; font-size: 14px;color: #555666;margin: 0px 40px">
                        <span>注销账号:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 3">
                        <el-popover
                                placement="bottom"
                                width="160"
                                v-model="visible">
                            <p>你确定要注销账号吗？</p>
                            <div style="text-align: right; margin: 0">
                                <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                                <el-button type="primary" size="mini" @click="deleteAccount" >确定</el-button>
                            </div>
                            <el-button slot="reference" type="danger" round style="width: 100px">注销账号</el-button>
                        </el-popover>
                    </div>
                </div>
            </div>

        </div>
        <el-dialog
                title="修改密码"
                :visible.sync="dialogVisibleChangePassword"
                width="30%"
                :before-close="handleClose">
            <el-input placeholder="请输入密码" v-model="inputPasswordFirst" show-password></el-input>
            <el-input placeholder="请再次输入密码" v-model="inputPasswordAgin" style="margin-top: 20px" show-password></el-input>
            <span slot="footer" class="dialog-footer">
    <el-button type="primary" @click="changePasswordSuccess">确 定</el-button>
  </span>
        </el-dialog>
    </div>



</template>

<script>
    export default {
        name: "SetOfUserView",
        data(){
            return{
                safetyLevel:0,
                user: localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
                allInforOfPerson:{},
                visible: false,
                showView1:false,
                showView2:false,
                dialogVisibleChangePassword: false,
                inputPasswordFirst:"",
                inputPasswordAgin:"",
            }
        },
        created() {
            this.loadAllInforOfUser()
        },
        methods:{
            deleteAccount(){
                this.request.delete("/user/"+this.user.id).then(res =>{
                    if(res){
                        this.$message.success("注销成功！")
                        this.$store.commit("logoutFront")
                    }else{
                        this.$message.error("注销失败！")
                    }
                    this.load()
                })
            },
            ChangePassword(){
                this.dialogVisibleChangePassword=!this.dialogVisibleChangePassword
            },
            handleClose(done) {
                this.$confirm('正在修改个人信息，你确定退出吗？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            changePasswordSuccess(){
                if(this.inputPasswordAgin!=this.inputPasswordFirst)
                {
                    this.$message.error("两次密码输入不同，请重新输入！")
                    return false
                }else{
                    const form = this.allInforOfPerson
                    form.password=this.inputPasswordFirst
                    this.request.post("/user", form).then(res => {
                        if (res) {
                            this.$message.success("修改成功，请重新登录！")
                            this.$store.commit("logoutFront")

                        } else {
                            this.$message.error("保存失败！")
                        }
                    })
                }
            }

        }
    }

</script>

<style scoped>
.setOfUserView{
    display: flex;
    flex-direction: column;
    height: 600px;
    gap: 10px;
    width: 1000px;
}
    .safeDataView{
        background-color: #FFFFFF;
        flex: 6;
        flex-direction: column;
        display: flex;
        margin-bottom: 50px;
    }
</style>