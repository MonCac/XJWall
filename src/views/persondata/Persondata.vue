<template>
    <div class="personalCenterView">
<!--        最顶部-->
        <div class="topbar">
<!--            用户信息展示-->
            <div class="userBaseInfor">
<!--                头像区域-->
                <div class="avaOfPersonalCenter">
                    <div class="avatarOfUser" style="width: 100px;height: 100px;position: relative;top: -30px">
                        <img :src="personalInfor.avatarUrl" style="width: 100%;height: 100%;border-radius: 50%;">
                    </div>
                </div>
<!--                基本信息区域-->
                <div class="baseInformation">
<!--                    用户名区域-->
                    <div class="nicknameOfpersonal" style="flex: 1;">
                        <span style="flex: 1;line-height: 47px;padding-left: 20px;color: #222226; font-size: 22px">{{personalInfor.nickname}}</span>
                        <span class="el-icon-message" style="padding-left: 15px;font-size: 12px;"></span>
                        <span style="flex: 13;line-height: 47px;padding-left: 5px;color: #222226; font-size: 12px;color: #999AAA">{{personalInfor.email}}</span>
                    </div>
<!--                    数据显示区域-->
                    <div class="dataView" style="color: #999aaa; flex: 1;display: flex;justify-content: center;align-items: center;font-size: 14px;letter-spacing: 1px;">
                        <div style="text-align: center;flex: 1;border-right: 1px solid #F2F2F2;">
                            <span style="font-size: 16px;font-weight: bold"> {{personalInfor.count}}</span>帖子数量
                        </div>
                        <div style="text-align: center;flex: 1;border-right: 1px solid #F2F2F2">
                             <span style="font-size: 16px;font-weight: bold"> {{readNumber}}</span>被浏览量
                        </div>
                        <div style="text-align: center;flex: 1;border-right: 1px solid #F2F2F2">
                          <span style="font-size: 16px;font-weight: bold">{{collectedNumber}}</span>被收藏量
                        </div>
                        <div style="text-align: center;flex: 4">
                        </div>
                    </div>
                </div>
<!--                编辑资料区域-->
                <div class="buttonArea">
                    <el-button v-if="id==user.id"  class="el-icon-tickets" round type="primary" style="  letter-spacing: 1px;height: 40px" @click="pushIntoEditView">编辑资料</el-button>
                </div>
            </div>
        </div>

<!--        用户文章及其他内容展示-->
        <div class="resourcesOfPersonal">
<!--            用户的资源展示-->
            <div class="resources">
                    <div>
                        <el-menu
                                :default-active="'/front/personalCenter/newArticleOfPersonal'"
                                 class="el-menu-demo"
                                 mode="horizontal"
                                 router
                                 @select="handleSelect"
                        >

                            <el-menu-item
                                    index="/front/personalCenter/newArticleOfPersonal"
                                    :route="{name:'NewArticleOfPersonal',query:{id:this.id}}"
                            >最新帖子</el-menu-item>
                            <el-menu-item  index="/front/personalCenter/personalCollectView"
                                           :route="{name:'PersonalCollectView',query:{id:this.id}}" >收藏</el-menu-item>
                        </el-menu>
                    </div>
                <div>
                    <router-view/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PersonalCenter",
        data(){
            return{
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
                drawer: false,
                proposal:false,
                id:this.$route.query.id,
                personalInfor:localStorage.getItem("personalInfor") ? JSON.parse(localStorage.getItem("personalInfor")) : {},
                readNumber:0,
                collectedNumber:0,
            }
        },
        created() {
            this.loadPersonalInfor()
            this.loadReadNumber()
            this.loadCollectedNumber()
        },
        methods:{
            loadPersonalInfor(){
                this.request.get("/user/PersonalCenter/"+this.id).then(res=>{
                        this.personalInfor = res.data
                        localStorage.removeItem("personalInfor")
                        localStorage.setItem("personalInfor",JSON.stringify(res.data))
                })
            },
            pushIntoEditView(){
                 this.$router.push('/front/editMaterial/userInforEdit')
            },
            loadReadNumber()
            {
                this.request.get("/user/findReadNumber/"+this.id).then(res=>{
                   this.readNumber =  res.data
                })
            },
            loadCollectedNumber(){
                this.request.get("/user//findCollectionNumber/"+this.id).then(res=>{
                    this.collectedNumber = res.data
                })
            }
        }
    }
</script>

<style scoped>
    .personalCenterView{
        gap: 10px;
        display: flex;
        flex-direction: column;
        align-items: center;
        height: 1000px;
    }
    .topbar{
        width: 2000px;
        flex: 3;
        display: flex;
        flex-direction: column;
        align-items: center;


    }
    .userBaseInfor{
        width: 1200px;
        display: flex;
        flex: 1;
    }
    .resourcesOfPersonal{
        gap: 10px;
        width: 1200px;
        justify-content: center;
        display: flex;
        flex: 13;
    }
    .resources{
        flex: 10;
    }
    .avaOfPersonalCenter{
        display: flex;
       justify-content: center;
        flex: 2;
        background-color: #FFFFFF;
    }
    .baseInformation{
        background-color: #FFFFFF;
        justify-content: center;
        display: flex;
        flex-direction: column;
        flex: 10;

    }
    .buttonArea{
        display: flex;
        align-items: center;
        justify-content: center;
        flex: 3;
        background-color:#FFFFFF;
    }

</style>