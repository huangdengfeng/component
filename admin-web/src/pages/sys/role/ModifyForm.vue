<template>
  <t-dialog v-model:visible="visible" :close-on-overlay-click="false" :destroy-on-close="true" :footer="false"
    header="修改角色" scroll-to-first-error="smooth" width="750">
    <template #body>
      <t-form :data="formData" :rules="formRules" @submit="save">
        <t-row :gutter="[0, 20]">
          <t-col :span="6">
            <t-form-item label="编码" name="code">
              <t-input v-model="formData.code" :maxcharacter="20" placeholder="请输入编码"></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="名称" name="name">
              <t-input v-model="formData.name" :maxcharacter="20" placeholder="请输入名称"></t-input>
            </t-form-item>
          </t-col>
          <t-col :span="6">
            <t-form-item label="状态" name="status">
              <t-radio-group v-model="formData.status" :options="RECORD_STATUS"></t-radio-group>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item label="备注" name="remark">
              <t-textarea v-model="formData.remark" :maxcharacter="100" placeholder="请输入备注"></t-textarea>
            </t-form-item>
          </t-col>
          <t-col :span="12">
            <t-form-item>
              <t-button variant="outline" @click="close()">取消</t-button>
              <t-button theme="primary" type="submit">保存</t-button>
            </t-form-item>
          </t-col>
        </t-row>
      </t-form>
    </template>
  </t-dialog>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { LoadingPlugin, MessagePlugin } from 'tdesign-vue-next'
import { RECORD_STATUS } from '@/utils/constant'
import request from '@/utils/request'

const formRules = {
  code: [
    { required: true },
    { whitespace: true }
  ],
  name: [
    { required: true },
    { whitespace: true }
  ],
  status: [
    { required: true }
  ]
}

const visible = ref(false)
const formData = reactive({})
const emit = defineEmits(['refresh'])

const open = (id) => {
  visible.value = true
  request.get(`/sys/role/detail/${id}`).then((data) => {
    Object.assign(formData, data)
  })
}

const save = async ({ validateResult }) => {
  if (validateResult !== true) {
    return
  }
  LoadingPlugin(true)
  try {
    await request.post('/sys/role/modify', {
      id: formData.id,
      code: formData.code,
      name: formData.name,
      status: formData.status,
      remark: formData.remark
    })
    emit('refresh')
    MessagePlugin.success('修改成功')
    close()
  } finally {
    LoadingPlugin(false)
  }
}

const close = () => {
  visible.value = false
}

defineExpose({
  open
})
</script>