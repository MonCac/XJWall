<template>
    <div class="personalData">
<!--        头像及个人简介-->
        <div class="avaOfpersonalData">
<!--            头像-->
            <div class="avaOf_avaOfpersonalData">
                <el-upload
                        style="text-align: center;"
                        class="avatarOfuploader"
                        action="http://localhost:8080/file/upload"
                        :show-file-list="false"
                        :on-success="handleAvatarSuccess"
                >
                    <img v-if="allInforOfPerson.avatarUrl" :src="user.avatarUrl" class="avatar">
                </el-upload>
            </div>
<!--           个人简介 -->
        </div>
<!--        基本信息-->
        <div class="baseDataOfPersonalData">
<!--            表头-->
            <div style=" flex: 2;display: flex;align-items: center;border-bottom: 1px solid #F2F2F2 ">
                    <span style="flex: 10;font-size: 18px;font-weight: 600;padding-left: 30px">基本信息</span>
                <div v-if="!this.baseDataEdit" style=";flex:2">
                  <div>
                      <el-button type="primary" round  @click="baseDataEditShow">确定修改</el-button>
                  </div>
                </div>
            </div>
<!--            表身-->
            <div class="showEditInforOfEditInfor" style="flex: 8;display: flex;flex-direction: column ;" v-if="!this.baseDataEdit" >
                <div style="flex:1;display: flex;align-items: center;">
                    <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px;letter-spacing: 1px;">
                        <span >用户昵称:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 13;letter-spacing: 2px;">
                        <el-input v-model="allInforOfPerson.nickname" style="width: 100px;text-align: center" placeholder="请输入内容"></el-input>
                    </div>
                </div>
                <div style="flex:1;display: flex;align-items: center;;letter-spacing: 1px;">
                    <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px;letter-spacing: 1px;">
                        <span style="">用户&ensp;ID:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 13">
                        <el-input v-model="allInforOfPerson.id" :disabled="true" style="width: 100px;text-align: center" placeholder="请输入内容"></el-input>
                    </div>
                </div>
                <div style="flex:1;display: flex;align-items: center;letter-spacing: 1px;">
                    <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px">
                        <span>性&ensp;&ensp别:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 13">
                        <el-input v-model="allInforOfPerson.sex" style="width: 100px;text-align: center" placeholder="请输入内容"></el-input>
                    </div>
                </div>
                <div style="flex:1;display: flex;align-items: center;letter-spacing: 1px;">
                    <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px">
                        <span>个人简介:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 13">
                        <el-input type="textarea" :rows="3" v-model="allInforOfPerson.brief" style="width: 400px;text-align: center"
                                  placeholder="请输入内容"
                                  maxlength="30"
                                  show-word-limit
                        ></el-input>
                    </div>
                </div>
            </div>

            <div class="showInforOfEditInfor" style="flex: 8;display: flex;flex-direction: column" v-if="this.baseDataEdit" @click="baseDataEditShowTwo">
                <div style="flex:1;display: flex;align-items: center;">
                    <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px;letter-spacing: 1px;">
                        <span >用户昵称:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 13;letter-spacing: 2px;">
                        <span>{{allInforOfPerson.nickname}}</span>
                    </div>
                </div>
                <div style="flex:1;display: flex;align-items: center;;letter-spacing: 1px;">
                    <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px;letter-spacing: 1px;">
                        <span style="">用户&ensp;ID:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 13">
                        <span>{{allInforOfPerson.id}}</span>
                    </div>
                </div>
                <div style="flex:1;display: flex;align-items: center;letter-spacing: 1px;">
                    <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px">
                        <span>性&ensp;&ensp;别:</span>
                    </div>
                    <div style="font-size: 14px;color: #555666;flex: 13">
                        <span>{{allInforOfPerson.sex}}</span>
                    </div>
                </div>
                <div style="flex:1;display: flex;align-items: center;letter-spacing: 1px;">
                 <div style="flex:1; font-size: 14px;color: #555666;margin: 0px 40px">
                        <span>个人简介:</span>
                    </div>
                <div style="font-size: 14px;color: #555666;flex: 13">
                        <span>{{allInforOfPerson.brief}}</span>
                    </div>
            </div>
            </div>
        </div>

    </div>
</template>

<script>
    import {Axios as request} from "axios";
    export default {
        name: "UserInforEditView",
        data(){
            return{
                user: localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
                baseDataEdit:true,
                allInforOfPerson:{},
            }

        },
        created() {
            this.loadAllInforOfUser()
        },
        methods:{
            loadAllInforOfUser(){
                this.request.post("/user/searchAllInfor",this.user).then(res =>{
                    this.allInforOfPerson = res.data
                })
            },
            handleAvatarSuccess(res){
                this.user.avatarUrl=res;
                const form = this.allInforOfPerson
                form.avatarUrl = res;
                this.request.post("/user", form).then(res => {
                    if (res) {
                        this.$message.success("保存成功！")
                        this.$emit("fatherRefreshInfo")
                    } else {
                        this.$message.error("保存失败！")
                    }
                })
            },
            baseDataEditShowTwo(){
                this.baseDataEdit=!this.baseDataEdit
            },
            baseDataEditShow(){
                const form = this.allInforOfPerson
                this.request.post("/user", form).then(res => {
                    if (res) {
                        this.$message.success("保存成功！")
                        this.$emit("fatherRefreshInfo")
                    } else {
                        this.$message.error("保存失败！")
                    }
                })
                this.baseDataEdit=!this.baseDataEdit
            }
        }
    }
</script>

<style scoped>
    .avatarOfuploader{
        border-radius: 50%;
    }
.avatar{
    width: 110px;
    height: 110px;
    border-radius: 50%;
}
.showInforOfEditInfor{
    cursor: pointer;
}
.showInforOfEditInfor:hover{
    background-color: #EEEEEE;
}
.personalData{
    gap: 20px;
    display: flex;
    flex-direction: column;
    width: 1000px;
    height: 1000px;
}
    .avaOfpersonalData{
        background-color: #FFFFFF;
        display: flex;
    flex: 2;
    }
    .baseDataOfPersonalData{
        background-color: #FFFFFF;
        flex: 5;
        flex-direction: column;
        display: flex;
    }
    .avaOf_avaOfpersonalData{
        flex: 2;
    }
</style>