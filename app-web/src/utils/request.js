import { getToken } from "./auth.js";
import env from "./env.js"

uni.addInterceptor('request',{
	invoke(args) {
	    // request 触发前拼接 url
	    args.url = env.baseUrl + env.apiPrefix + args.url
	    
	    // 设置默认超时时间为10秒
	    if (!args.timeout) {
	        args.timeout = 10000
	    }
	    
	    // Add Bearer token to headers
	    if (!args.header) {
	        args.header = {};
	    }
			const token = getToken();
			if (token) {
				args.header['Authorization'] = 'Bearer ' + getToken();
			}
	  },
	  fail(err) {
	    console.error('request interceptor-fail',err)
			uni.showToast({
				title: '网络错误',
				icon:"error"
			})
	  },
})

function handleResponse(response,successFunc,fullResponse) {
	if (response.statusCode == 200) {
		const responseData = response.data
		if (fullResponse === true) {
			successFunc(responseData)
			return 
		}
		if (!responseData.success) {
			uni.showToast({
				title: responseData.msg,
				icon:"error"
			})
			return 
		}
		successFunc(responseData.data)
	} else if (response.statusCode == 401) {
		uni.redirectTo({
			url:'/pages/login/index'
		})
		uni.showToast({
			title: '去登录',
			icon:"none"
		})
	} else {
		uni.showToast({
			title:  '网络错误'+ response.statusCode,
			icon:"error"
		})
	}
}

/**
 * POST JSON
 * @param {Object} url
 * @param {Object} data
 * @param {Object} successFunc
 * @param {Object} fullResponse 包含错误信息错误码
 */
export function post(url,data,successFunc,fullResponse) {
	uni.request({
		url:url,
		data:data,
		method: 'POST',
		success: function(response) {
			handleResponse(response,successFunc,fullResponse)
		}
	})
}

export function get(url,data,successFunc,fullResponse) {
	uni.request({
		url:url,
		data:data,
		method: 'GET',
		success: function(response) {
			handleResponse(response,successFunc,fullResponse)
		}
	})
}