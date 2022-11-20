<template>
   <div class="PersonalEdit">
        <div style="display: flex;gap: 10px;margin-top: 10px">
            <!--      编辑资料的侧边栏-->
            <div class="AsideEdit">
                <el-col :span="12" style="width: 230px;text-align: center">
                    <el-menu
                            router
                            class="el-menu-vertical-demo">
                        <el-menu-item index="/front/editMaterial/userInforEdit">
                            <i class="el-icon-menu"></i>
                            <span slot="title">个人资料</span>
                        </el-menu-item>
                        <el-menu-item index="/front/editMaterial/setOfUserView">
                            <i class="el-icon-document"></i>
                            <span slot="title">账号设置</span>
                        </el-menu-item>
                        <!-- <el-menu-item index="/front/editMaterial/setOfUserView">
                            <i class="el-icon-document"></i>
                            <span slot="title">帖子设置</span>
                        </el-menu-item> -->
                    </el-menu>
                </el-col>
            </div>
            <!--      详情展示-->
            <div class="detailed">
                <router-view @fatherRefreshInfo="fatherRefreshInfo" />
            </div>
        </div>
   </div>
</template>

<script>
    export default {
        name: "EditMaterials",
        methods:{
            fatherRefreshInfo(){
                this.$emit("grandFatherRefreshInfo")
            },
        }
    }
</script>

<style scoped>
.PersonalEdit{
   display: flex;
   gap: 10px;
   justify-content: center;
}

.AsideEdit{
    position: sticky;
    top: 40px;
    height: 500px;
}
.detailed{
    height: auto;
    width: 1000px;
}
</style>