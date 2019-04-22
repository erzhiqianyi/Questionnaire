<template>
  <div class="app-container">
    <div style="width: 90%;">
      <el-steps :active="active" finish-status="success">
        <el-step
          v-for="  item in selectType.steps"
          v-bind:key="item.code"
          :title="item.name"
          :icon="item.icon"
          :description="item.description"
        >
          <span slot="description" v-if="item.code == 'setType'">{{selectType.name}}</span>
        </el-step>
      </el-steps>
      <el-button style="margin-top: 12px;" @click="pre" :disabled="active == 0">上一步</el-button>
      <el-button style="margin-top: 12px;" @click="next" :disabled="active == maxActive">下一步</el-button>
    </div>
    <div v-if=" activeStep == 'setType' " class="card-type">
      <el-row :gutter="32">
        <el-col :xs="24" :sm="24" :lg="8" v-for=" item in types" v-bind:key="item.name">
          <div class="chart-wrapper" @mouseover="changeSelect(item.name)">
            <div>
              <el-card
                class="box-card-component"
                shadow="hover"
                v-bind:class="{ active: item.name == selectType.name}"
              >
                <div slot="header" class="box-card-header">{{item.name}}</div>
                <div style="position:relative;"></div>
                <div class="card-button">
                  <el-button type="primary" plain round @click="next()">创建</el-button>
                </div>
              </el-card>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div v-if="activeStep == 'create'" class="card-type">
      <el-form label-width="100px">
        <el-form-item>
          <span slot="label">{{selectType.name}}名称</span>
          <el-input v-model="questionnaire.name"></el-input>
        </el-form-item>
        <el-form-item label="">
          <span slot="label">{{selectType.name}}说明</span>
          <el-input v-model="questionnaire.remark" type="textarea" rows="5"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="next">立即创建</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div v-if="activeStep == 'question'" class="card-type">
      <div>
        <el-form label-width="100px">
          <h1>{{questionnaire.name}}</h1>
          <p>{{questionnaire.remark}}</p>
        </el-form>
      </div>

      <div>
        <el-radio-group v-model="radio">
    <el-radio :label="3">备选项</el-radio>
    <el-radio :label="6">备选项</el-radio>
    <el-radio :label="9">备选项</el-radio>
  </el-radio-group>
      </div>

    </div>
    <div v-if="active == 4">预览问卷</div>
    <div v-if="active == 5">发布</div>
  </div>
</template>
 

 <script>
import { fetchQuestionnaireType } from "@/api/questionnaire/add";
import splitPane from "vue-splitpane";

export default {
  name: "AddQuestionnaire",
  components: { splitPane },
  data() {
    return {
      active: 0,
      activeStep: "setType",
      selectType: {},
      maxActive: 1,
      questionnaire: {
        name: "大学生消费调查问卷",
        remark: "调查学生消费情况"
      },
      radio:3,
      types: []
    };
  },
  created() {
    this.setDefaultType();
  },
  methods: {
    next() {
      if (this.active++ > this.maxActive) this.active = this.axActive;
      let currentStep = this.selectType.steps[this.active];
      this.activeStep = currentStep.code;
    },
    pre() {
      if (this.active-- <= 0) {
        this.active = 0;
      }
      let currentStep = this.selectType.steps[this.active];
      this.activeStep = currentStep.code;
    },
    setDefaultType() {
      this.types = fetchQuestionnaireType();
      this.selectType = this.types[0];
      this.maxActive = this.selectType.steps.length;
    },
    changeSelect(name) {
      this.active = 0;
      this.selectType = this.types.find(function(type) {
        return type.name == name;
      });
    }
  }
};
</script>
<style  scoped>
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
  border: solid 3px;
  border-color: #64d9d6;
  box-shadow: 5px #64d9d6;
}
.lable {
  padding: 10px;
  font-size: 30px;
}
.input {
  width: 60%;
  margin: 10px;
  font-size: 30px;
}

.components-container {
  position: relative;
  height: 200px;
}

.left-container {
  background-color: #f38181;
  height: 100%;
}

.right-container {
  background-color: #fce38a;
  height: 100%;
}

.top-container {
  background-color: #fce38a;
  width: 100%;
  height: 100%;
}

.bottom-container {
  width: 100%;
  background-color: #95e1d3;
  height: 100%;
}
</style>

