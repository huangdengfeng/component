// 与环境相关变量
const  env = {
  baseUrl: import.meta.env.VITE_BASE_URL,
  apiPrefix: import.meta.env.VITE_API_PREFIX
}
console.log("env:",env)

export default env