export function setToken(token) {
	uni.setStorageSync("token",token)
} 

export function getToken() {
	return uni.getStorageSync("token")
} 