import { useSelector } from "react-redux";
import Typewriter from 'typewriter-effect';
import "./landing.css";
import { selectUser } from "../../redux/userSlice";
import { Navigate, Outlet } from "react-router-dom";
import React from "react";

const Landing = () => {
    const isAuth = useSelector(selectUser);

    return (
        <>
            {isAuth ? (
                <Outlet />
            ) : (
                <>
                    <Navigate to="/" />
                    <div className='landing'>
                        <div style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
                            <Typewriter
                                options={{ loop: true }}
                                onInit={(typewriter) => {
                                    typewriter.typeString('Welcome to GitColab.')
                                        .callFunction(() => {
                                            console.log('String typed out!');
                                        })
                                        .pauseFor(2500)
                                        .deleteAll()
                                        .callFunction(() => {
                                            console.log('All strings were deleted');
                                        })
                                        .start();
                                }}

                            /></div>
                    </div>
                </>
            )}
        </>
    )
}

export default Landing;