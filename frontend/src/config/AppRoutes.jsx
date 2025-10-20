import { Route, Routes } from "react-router";
import App from "../App";
import Login from "../compnents/login";
import Register from "../compnents/Register";


const AppRouters = () => {
    return (
    <Routes>
      <Route path="/" element={<App />} />
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/register" element={<Register />} />
    </Routes>
    );
}

export default AppRouters;
