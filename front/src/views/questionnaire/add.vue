<template>
  <div class="app-container">
    <div style="width: 90%;">
      <el-steps :active="active" finish-status="success">
        <el-step
          v-for="  item in selectType.steps"
          :title="item.name"
          :icon="item.icon"
          :description="item.description">
        <span slot="description" v-if="item.code == 'setType'" >
          {{selectType.name}}
        </span>

        
        </el-step>
        
      </el-steps>
      <el-button style="margin-top: 12px;" @click="pre" :disabled="active == 1">上一步</el-button>
      <el-button style="margin-top: 12px;" @click="next" :disabled="active == 7">下一步</el-button>
    </div>

    <div v-if=" activeStep == 'setType' " class="card-type">
      <el-row :gutter="32">
        <el-col :xs="24" :sm="24" :lg="8" v-for=" item in types">
          <div class="chart-wrapper " @mouseover="changeSelect(item.name)">
            <div>
              <el-card class="box-card-component"  shadow="hover"
              v-bind:class="{ active: item.name == selectType.name}"
               >
                <div slot="header" class="box-card-header">{{item.name}}</div>
                <div style="position:relative;"></div>
                <div class="card-button">
                  <el-button type="primary" plain round @click="next()" >创建</el-button>
                </div>
              </el-card>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>

    <div v-if="activeStep == 'create'">基本信息</div>
    <div v-if="active == 3">答案维度</div>
    <div v-if="active == 4">预览问卷</div>
    <div v-if="active == 5">发布</div>
  </div>
</template>
 

 <script>
import { fetchQuestionnaireType } from "@/api/questionnaire/add";

export default {
  data() {
    return {
      active: 1,
      activeStep: 'setType',
      selectType: {},
      maxActive:1,
      types: []
    };
  },
  created() {
    this.setDefaultType();
  },
  methods: {
    next() {
      if (this.active++ > this.maxActive) this.active = this.axActive
      let currentStep =  this.selectType.steps[this.active-1];
      this.activeStep = currentStep.code
    },
    pre() {
      if (this.active-- <= 1) {
        this.active = 1;
      }
      let currentStep =  this.selectType.steps[this.active-1];
      this.activeStep = currentStep.code
    },
    setDefaultType() {
     this.types =  fetchQuestionnaireType() 
     this.selectType = this.types[0]
     this.maxActive = this.selectType.steps.length
    },
    changeSelect(name){
      this.active = 1
      this.selectType = this.types.find(function(type) {
          return type.name == name
      })
    }
  }
};
</script>
<style lang="scss" scoped>
.card-type {
  background: #fff;
  padding: 16px 16px 0;
  margin: 32px auto;
  text-align: center;
}
.chart-wrapper {
  background: #fff;
  padding: 16px 16px 0;
  margin-bottom: 32px;
}
.card-button {
  margin-top: 10px;
  padding: 10xp;
}
.active {
  border: solid 3px ;
  border-color: #64D9D6;
  box-shadow: 5px #64D9D6; 
  }

</style>

