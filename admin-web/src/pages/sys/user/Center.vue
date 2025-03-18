<template>
  <t-card title="个人信息">
    <template #actions>
      <t-button theme="primary" @click="showDialog = true">修改密码</t-button>
    </template>
    <t-form :data="userStore.userInfo" label-width="100px">
      <t-form-item label="用户名">
        <t-input v-model="userStore.userInfo.userName" readonly/>
      </t-form-item>
      <t-form-item label="姓名">
        <t-input v-model="userStore.userInfo.name" readonly/>
      </t-form-item>
      <t-form-item label="手机号">
        <t-input v-model="userStore.userInfo.mobile" placeholder="暂无" readonly/>
      </t-form-item>
      <t-form-item label="邮箱">
        <t-input v-model="userStore.userInfo.email" placeholder="暂无" readonly/>
      </t-form-item>
      <t-form-item label="头像">
        <t-avatar v-if="userStore.userInfo.photo" :image="userStore.userInfo.photo"/>
        <t-avatar v-else>{{ userStore.userInfo.name?.charAt(0) }}</t-avatar>
      </t-form-item>
      <t-form-item label="角色">
        <t-tag v-for="(name, index) in userStore.userInfo.roleNames" :key="index"
               style="margin-right: 8px">
          {{ name }}
        </t-tag>
      </t-form-item>
    </t-form>

    <!-- 修改密码对话框 -->
    <t-dialog
        :confirm-btn="{ content: '确定', loading: submitting }"
        :visible="showDialog"
        header="修改密码"
        width="600px"
        @close="closeDialog"
        @confirm="handleModifyPassword"
    >
      <template #body>
        <t-form ref="passwordFormRef" :data="passwordForm" :rules="passwordRules"
                style="height: 180px;">
          <t-form-item label="原密码" name="oldPassword">
            <t-input v-model="passwordForm.oldPassword" clearable type="password"/>
          </t-form-item>
          <t-form-item label="新密码" name="newPassword">
            <t-input v-model="passwordForm.newPassword" clearable type="password"/>
          </t-form-item>
          <t-form-item label="确认密码" name="confirmPassword">
            <t-input v-model="passwordForm.confirmPassword" clearable type="password"/>
          </t-form-item>
        </t-form>
      </template>
    </t-dialog>
  </t-card>
</template>

<script setup>
import {ref} from 'vue'
import {useUserStore} from '@/store/user'
import {MessagePlugin} from 'tdesign-vue-next'
import request from '@/utils/request'

const userStore = useUserStore()
const showDialog = ref(false)
const submitting = ref(false)
const passwordFormRef = ref(null)

const passwordForm = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const passwordRules = {
  oldPassword: [{required: true, message: '请输入原密码'}],
  newPassword: [
    {required: true, message: '请输入新密码'},
    {min: 6, message: '密码长度不能小于6位'}
  ],
  confirmPassword: [
    {required: true, message: '请确认新密码'},
    {
      validator: (val) => val === passwordForm.value.newPassword,
      message: '两次输入的密码不一致'
    }
  ]
}

const closeDialog = () => {
  showDialog.value = false
  passwordFormRef.value.reset();
  passwordFormRef.value.clearValidate()
}

const handleModifyPassword = async () => {
  const validateResult = await passwordFormRef.value.validate()
  if (validateResult === true) {
    submitting.value = true
    try {
      await request.post('/sys/user/modify_my_pwd', {
        oldPassword: passwordForm.value.oldPassword,
        newPassword: passwordForm.value.newPassword
      })
      MessagePlugin.success('密码修改成功')
      closeDialog()
    } finally {
      submitting.value = false
    }
  }
}
</script>