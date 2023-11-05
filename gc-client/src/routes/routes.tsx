import { Routes, Route, Navigate } from "react-router-dom";

import Profile from '../view/Profile/Profile';
import Dashboard from '../view/Dashboard/Dashboard';
import Explore from '../view/Explore/Explore';
import Integration from '../view/Integration/Integration';
import Landing from '../view/Landing/Landing';
import ForgotPassword from '../view/ForgotPassword/ForgotPassword';
import ResetPassword from '../view/ForgotPassword/ResetPassword';
import Login from '../view/Login/Login';
import Registration from "../view/Registration/Registration";
import Otp from "../view/ForgotPassword/Otp";
import { useSelector } from "react-redux";
import { selectUser } from "../redux/userSlice";
import ProjectDetails from "../view/Integration/ProjectDetails";

const Router = () => {
    const isAuth = useSelector(selectUser);
    console.log("USER+=====>", isAuth);
    return (
        <>
            <Routes>
                <Route path='/login' element={isAuth ? <Navigate to="/dashboard" /> : <Login />}/>
                <Route path='/register' element={isAuth ? <Navigate to="/dashboard"/> : <Registration />}/>
                <Route path='/forgot-password' element={isAuth ? <Navigate to="/dashboard"/> : <ForgotPassword />}/>
                <Route path='/forgot-password/otp' element={isAuth ? <Navigate to="/dashboard"/> : <Otp />}/>
                <Route path='/reset-password' element={isAuth ? <Navigate to="/dashboard"/> : <ResetPassword />} />
                
                <Route path='/' element={<Landing />}>
                    {/* <Route path="/" element={ isAuth ? <Navigate to="/dashboard" /> : <Navigate to="/" />}/> */}
                    <Route path='/profile' Component={Profile}/>
                    <Route path='/dashboard' Component={Dashboard}/>
                    <Route path='/explore' Component={Explore}/>
                    <Route path='/integration' Component={Integration} />
                        
                    <Route path="/integration/:id" element={<ProjectDetails />}/>

                </Route>
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </>
    )
}

export default Router;