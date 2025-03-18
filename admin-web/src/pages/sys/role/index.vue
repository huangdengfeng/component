<template>
  <t-card title="角色管理">
    <t-row :gutter="[0, 20]">
      <t-col :span="12">
        <t-form :data="searchForm" colon @submit="query">
          <t-row :gutter="[0, 20]">
            <t-col :span="3">
              <t-form-item label="编码" name="code">
                <t-input v-model="searchForm.code"/>
              </t-form-item>
            </t-col>
            <t-col :span="3">
              <t-form-item label="名称" name="fuzzyName">
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
                <t-button v-auth="['sys:role:add']" theme="default" @click="addFormRef.open()">添加
                </t-button>
              </t-space>
            </t-col>
          </t-row>
        </t-form>
      </t-col>
      <t-col :span="12">
        <t-table :columns="columns" :data="tableData" :loading="tableLoading" row-key="id">
          <template #status="{ row }">
            <t-tag v-if="row.status === 1" theme="success">有效</t-tag>
            <t-tag v-else-if="row.status === 2" theme="danger">已停用</t-tag>
          </template>
          <template #op="{ row }">
            <t-space>
              <t-link v-auth="['sys:role:modify']" theme="primary"
                      @click="modifyFormRef.open(row.id)">修改
              </t-link>
            </t-space>
          </template>
        </t-table>
      </t-col>
    </t-row>
  </t-card>
  <add-form ref="addFormRef" @refresh="query"/>
  <modify-form ref="modifyFormRef" @refresh="query"/>
</template>

<script setup>
import { reactive, ref } from 'vue'
import AddForm from './AddForm.vue'
import ModifyForm from './ModifyForm.vue'
import { checkPermission } from "@/directives/auth"
import { USER_STATUS } from '@/utils/constant'
import request from "@/utils/request"

const addFormRef = ref(null)
const modifyFormRef = ref(null)

const columns = [
  {
    colKey: 'code',
    title: '编码',
  },
  {
    colKey: 'name',
    title: '名称',
  },
  {
    colKey: 'status',
    title: '状态',
    width: 80,
  },
  {
    colKey: 'op',
    title: '操作',
    width: 100,
    fixed: 'right',
  },
]

const searchForm = reactive({
  code: null,
  fuzzyName: null,
  status: null,
})

const tableData = ref([])
const tableLoading = ref(false)

const query = async () => {
  tableLoading.value = true
  try {
    const data = await request.post('/sys/role/list', searchForm)
    tableData.value = data
  } finally {
    tableLoading.value = false
  }
}

// 初始化查询
query()
</script>