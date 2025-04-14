<template>
	<view id="demo-captcha-container"></view>
	<button id="demon-captcha-btn">人机验证</button>
</template>

<script setup>
	import { ref, onMounted , onBeforeUnmount} from 'vue'
	import { onReady, onShow } from '@dcloudio/uni-app'
	const captcha = ref(null);
	const captchaButton = ref(null);


	const getInstance = (instance) => {
		captcha.value = instance;
	};
	const captchaVerifyCallback = async (captchaVerifyParam) => {
		// 1.向后端发起业务请求，获取验证码验证结果和业务结果
		// const result = await xxxx('http://您的业务请求地址', {
		//     captchaVerifyParam: captchaVerifyParam, // 验证码参数
		//     yourBizParam... // 业务参数
		// });
		// return {
		//  captchaResult: true, // 验证码验证是否通过，boolean类型，必选
		//  bizResult: true, // 业务验证是否通过，boolean类型，可选；若为无业务验证结果的场景，bizResult可以为空
		// }
		console.log(captchaVerifyParam);
		return {
			captchaResult: true,
			bizResult: true,
		};
	};

	// 验证通过后调用
	const onBizResultCallback = () => {
		console.log('onBizResultCallback');
	};

	onMounted(() => {
		captchaButton.value = document.getElementById('demon-captcha-btn');

		window.initAliyunCaptcha({
			SceneId: 'lrazit40', // 场景ID。根据步骤二新建验证场景后，您可以在验证码场景列表，获取该场景的场景ID
			prefix: '204muw', // 身份标。开通阿里云验证码2.0后，您可以在控制台概览页面的实例基本信息卡片区域，获取身份标
			mode: 'popup', // 验证码模式。popup表示要集成的验证码模式为弹出式。无需修改
			element: '#demo-captcha-container', // 页面上预留的渲染验证码的元素，与原代码中预留的页面元素保持一致。
			button: '#demon-captcha-btn', // 触发验证码弹窗的元素。button表示单击登录按钮后，触发captchaVerifyCallback函数。您可以根据实际使用的元素修改element的值
			captchaVerifyCallback, // 业务请求(带验证码校验)回调函数，无需修改
			onBizResultCallback, // 业务请求结果回调函数，无需修改
			getInstance, // 绑定验证码实例函数，无需修改
			slideStyle: {
				width: 360,
				height: 40,
			}, // 滑块验证码样式，支持自定义宽度和高度，单位为px。其中，width最小值为320 px
			language: 'cn', // 验证码语言类型，支持简体中文（cn）、繁体中文（tw）、英文（en）
		});
	})

	onBeforeUnmount(() => {
		captchaButton.value = null;
		// 必须删除相关元素，否则再次mount多次调用 initAliyunCaptcha 会导致多次回调 captchaVerifyCallback
		document.getElementById('aliyunCaptcha-mask')?.remove();
		document.getElementById('aliyunCaptcha-window-popup')?.remove();
	});
</script>

<style>
</style>