<template>
  <t-card title="用户管理">
    <t-row :gutter="[0, 20]">
      <t-col :span="12">
        <t-form :data="searchForm" colon @submit="query(true)">
          <t-row :gutter="[0, 20]">
            <t-col :span="3">
              <t-form-item label="用户名" name="userName">
                <t-input v-model="searchForm.userName"/>
              </t-form-item>
            </t-col>
            <t-col :span="3">
              <t-form-item label="姓名" name="fuzzyName">
                <t-input v-model="searchForm.fuzzyName" placeholder="支持模糊"/>
              </t-form-item>
            </t-col>
            <t-col :span="3">
              <t-form-item label="状态" name="status">
                <t-select v-model="searchForm.status" :options="USER_STATUS" clearable></t-select>
              </t-form-item>
            </t-col>
            <t-col :span="3">
              <t-space style="margin-left: 10px;">
                <t-button theme="primary" type="submit">查询</t-button>
                <t-button theme="default" type="reset">重置</t-button>
                <t-button v-auth="['sys:user:add']" theme="default" @click="addFormRef.open()">添加
                </t-button>
              </t-space>
            </t-col>
          </t-row>
        </t-form>
      </t-col>
      <t-col :span="12">
        <t-table :columns="columns" :data="tableData" :loading="tableLoading"
                 :pagination="pagination" row-key="uid"
                 @page-change="pageChange" @sort-change="sortChange">
          <template #status="{ row }">
            <t-tag v-if="row.status === 1" theme="success">有效</t-tag>
            <t-tag v-else-if="row.status === 2" theme="danger">已停用</t-tag>
            <t-tag v-else-if="row.status === 3" theme="danger">已锁定</t-tag>
          </template>
          <template #op="{ row }">
            <t-space>
              <t-link v-auth="['sys:user:modify']" theme="primary"
                      @click="modifyFormRef.open(row.uid)">修改
              </t-link>
              <t-dropdown>
                <t-icon name="ellipsis"/>
                <t-dropdown-menu>
                  <t-dropdown-item v-if="checkPermission(['sys:user:modify_pwd'])"
                                   @click="modifyPwdFormRef.open(row.uid, row.name, row.userName)">
                    修改密码
                  </t-dropdown-item>
                </t-dropdown-menu>
              </t-dropdown>
            </t-space>
          </template>
        </t-table>
      </t-col>
    </t-row>
  </t-card>
  <add-form ref="addFormRef" @refresh="query(true)"/>
  <modify-form ref="modifyFormRef" @refresh="query(true)"/>
  <modify-pwd-form ref="modifyPwdFormRef" @refresh="query(true)"/>
</template>

<script setup>
import {reactive, ref} from 'vue'
import AddForm from './AddForm.vue'
import ModifyForm from './ModifyForm.vue'
import ModifyPwdForm from './ModifyPwdForm.vue'
import {checkPermission} from "@/directives/auth"
import {SORT_ORDER_ASC, SORT_ORDER_DESC, USER_STATUS} from '@/utils/constant'
import request from "@/utils/request";

const addFormRef = ref(null)
const modifyFormRef = ref(null)
const modifyPwdFormRef = ref(null)  // 添加引用
// 表格列
const columns = [
  {
    colKey: 'name',
    title: '姓名',
    ellipsis: true,
  },
  {
    colKey: 'userName',
    title: '用户名',
  },
  {
    colKey: 'mobile',
    title: '手机号',
  },
  {
    colKey: 'status',
    title: '状态',
    width: 80,
  },
  {
    colKey: 'createTime',
    title: '创建时间',
    sorter: true,
    width: 200,
  },
  {
    colKey: 'op',
    title: '操作',
    width: 130,
    fixed: 'right',
  },
];

const searchForm = reactive({
  userName: null,
  fuzzyName: null,
  status: null,
});
// 表格数据
const tableData = ref([]);
const tableLoading = ref(false);
// 分页
const pagination = reactive({
  current: 1,
  pageSize: 10
});

const query = async (resetCurrentPage) => {
  if (resetCurrentPage) {
    pagination.current = 1;
  }
  tableLoading.value = true;
  try {
    const {data, total} = await request.post('/sys/user/page', {
      ...searchForm,
      page: pagination.current,
      pageSize: pagination.pageSize,
      orderBy: pagination.orderBy,
    });
    tableData.value = data;
    pagination.total = total;
  } finally {
    tableLoading.value = false;
  }
};

const pageChange = ({current, pageSize}) => {
  pagination.current = current;
  pagination.pageSize = pageSize;
  query();
};
const sortChange = (sort) => {
  if (sort) {
    pagination.orderBy = `${sort.sortBy} ${sort.descending ? SORT_ORDER_DESC
        : SORT_ORDER_ASC}`;
  } else {
    pagination.orderBy = null;
  }
  query();
};

</script>