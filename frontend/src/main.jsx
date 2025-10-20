import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import AppRouters from './config/AppRoutes'
import { BrowserRouter } from 'react-router'
import { Toaster } from 'react-hot-toast';

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <AppRouters />
      <Toaster 
      position="top-right"
      reverseOrder={false}
      gutter={8}
      toastOptions={{
      duration: 3000,
      style: { fontSize: '14px' },
    }}/>
    </BrowserRouter>
  </StrictMode>,
)
