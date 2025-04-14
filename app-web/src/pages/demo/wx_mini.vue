<template>
	<button @click="login" class="login">小程序登录</button>
	<button open-type="getPhoneNumber" @getphonenumber="getPhoneNumber">小程序获取手机号</button>
	
</template>

<script setup>
import { post } from '@/utils/request'

const login=()=>{
	wx.login({
	  success (res) {
	    if (res.code) {
		  console.debug("code:",res.code)
	      post('/login/wx_miniapp',{code:res.code},function(data){
			  console.debug(data)
		  })
	    } else {
	      console.log('登录失败！' + res.errMsg)
	    }
	  },
	  fail(err) {
	  	 console.log(`调用微信登录失败！${err.errno} ${err.errMsg}`)
	  }	
	})
}

const getPhoneNumber = (e)=>{
	if (e.detail.code) {
		console.log("phone code:",e.detail.code)
	} else {
		console.log(e.detail.errMsg) // 回调信息（成功失败都会返回）
		console.log(e.detail.errno)  // 错误码（失败时返回）		
	}
}
</script>

<style scoped lang="scss">

</style>