import { defineConfig } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'

export default defineConfig({
  plugins: [uni()],
  server: {
    port: 3000,
    open: false,
    proxy: {
      '/web': {
        target: 'http://szhlrj.com:10969',
        changeOrigin: true,
        secure: false
      },
      '/jsonrpc': {
        target: 'http://szhlrj.com:10969',
        changeOrigin: true,
        secure: false
      }
    }
  },
  build: {
    ssr: false
  }
})
