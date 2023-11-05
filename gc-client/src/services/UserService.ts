import { getData, postData, putData } from "./utils";

export async function loginUser(userData: any) {
    if(userData && ( userData.username == null || userData.password == null )) return null;
    const data: any = {};
    data.username = userData.username;
    data.password = userData.password;
    const response = await postData(data, "/auth/login");
    return response;
}

export async function registerUser(userData: any) {
    if(!userData) return null;
    const response = await postData(userData, "/auth/register");
    return response;
}

export async function sendVerificationCode(userData: any) {
    if(!userData) return null;
    const response = await postData(userData, "/auth/send-otp");
    return response;
}

export async function validateVerificationCode(userData: any) {
    if(!userData) return null;
    const response = await postData(userData, "/auth/validate-otp");
    return response;
}

export async function resetPassword(userData: any) {
    if(!userData) return null;
    const response = await postData(userData, "/auth/reset-password");
    return response;
}

/* Implement a service to handle updating user profile data */

export async function updateUserProfile(userData: any){

    if(!userData) return null;
    
    const response = await putData(userData, "/user/user-profile", localStorage.getItem('token')!);

    return response;
}

export async function getUserData(username: string, token: string){
    if(!username) return null;
    const response = await getData(`/user/${username}`, token);
    return response;
}