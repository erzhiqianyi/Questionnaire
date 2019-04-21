<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.title"
        :placeholder="$t('constant.name')"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >{{ $t('constant.search') }}</el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-plus">
        <router-link to="/questionnaire/create">{{ $t('questionnaire.addQuestionnaire') }}</router-link>
      </el-button>
    </div>
    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border=""
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column :label="$t('constant.id')" prop="id" align="left" min-width="5%">
        <template slot-scope="scope">
          <span>{{ scope.row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('questionnaire.title')" prop="id" align="left" min-width="30%">
        <template slot-scope="scope">
          <span>{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('constant.status')" min-width="10%" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.status == 'running'">
            <svg-icon icon-class="dot-running"/>运行中
          </span>
          <span v-else>
            <svg-icon icon-class="dot-stop"/>草稿
          </span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('questionnaire.recycle')" min-width="5%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.recycle }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('constant.createTime')" min-width="20%" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('constant.actions')"
        align="center"
        min-width="30%"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{row}">
          <el-button type="primary" icon="el-icon-edit">{{ $t('constant.edit') }}</el-button>
          <el-button type="success">
            <svg-icon icon-class="dashboard"/>
            {{ $t('constant.analysis') }}
          </el-button>
          <el-button type="danger" v-if="row.status == 'running'">
            <svg-icon icon-class="stop"/>
            {{ $t('constant.stop') }}
          </el-button>
          <el-button type="warning" v-if="row.status != 'running'">
            <svg-icon icon-class="running"/>
            {{ $t('constant.publish') }}
          </el-button>
          <el-button type="info" icon="el-icon-bell">{{ $t('constant.bell') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { fetchMineList } from "@/api/questionnaire/mine";
import Pagination from "@/components/Pagination"; // secondary package based on el-pagination

export default {
  name: "MiniQuestionnaire",
  components: { Pagination },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: false,
      showReviewer: false,
      listQuery: {
        page: 1,
        limit: 10,
        title: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    handleFilter() {
      console.log("搜索");
    },
    getList() {
      this.listLoading = true;
      fetchMineList(this.listQuery).then(response => {
        this.list = response.data.items;
        this.total = response.data.total;

        // Just to simulate the time of the request
        setTimeout(() => {
          this.listLoading = false;
        }, 1.5 * 1000);
      });
    }
  }
};
</script>

<style lang="scss" scoped>

</style>
