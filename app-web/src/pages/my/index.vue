<template>
	<view class="container">
		<view class="user-section">
			<image class="avatar" :src="userAvatar" mode="aspectFit"></image>
			<view class="user-info" v-if="userInfo.uid != null">
				<text>昵称不能为null</text>
				<text>VIP</text>
			</view>
			<view class="user-info" v-else>
				<button size="mini">去登录</button>
			</view>
		</view>
	</view>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import {get} from '@/utils/request'
import { onShow } from '@dcloudio/uni-app';

const userInfo = reactive({})
const userAvatar = ref('/static/logo.png')
onShow(()=>{
	get('/user/info',null,function(data){
		Object.assign(userInfo,data)
	})
})
</script>

<style lang="scss">
	.user-section {
		display: flex;
		width: 100vw;
		padding: 20rpx;
		.avatar {
			width: 120rpx;
			height: 120rpx;
			border-radius: 50%;
		}
		.user-info{
			display: flex;
			flex-direction: column;
			justify-content: space-around;
			padding-left: 10rpx;
		}
	}
	
</style> 