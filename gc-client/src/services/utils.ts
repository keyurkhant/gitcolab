import axios from "axios";

const API_URL = "http://localhost:8080/api";
// const API_URL = "https://git-colab-0657fd479701.herokuapp.com/api";

export async function postData(body: string, url: string, token?: string) {
    try {
        var myHeaders: any = {};
        myHeaders["Content-Type"] = "application/json";
        myHeaders["Accept"] = "application/json";
        myHeaders["Access-Control-Allow-Origin"] = "*";
        if (token) myHeaders["authorization"] = `Bearer ${token}`;

        var requestOptions = {
            headers: myHeaders
        };

        const response = await axios.post(API_URL + url, body, requestOptions);
        return response;
    } catch (error: any) {
        console.log("error", error);
        return error;
    }
}

export async function putData(body: string, url: string, token?: string) {
    try {
        var myHeaders: any = {};
        myHeaders["Content-Type"] = "application/json";
        myHeaders["Accept"] = "application/json";

        if (token) myHeaders["authorization"] = `Bearer ${token}`;

        
        var requestOptions = {
            headers: myHeaders
        };

        const response = await axios.put(API_URL + url, body, requestOptions);
        return response;

    } catch (error: any) {
        console.log("error", error.response);
        return error;
    }
}

export async function getData(url: string, token?: string) {
    try {
        var myHeaders: any = {};
        myHeaders["Accept"] = "application/json";
        myHeaders["Access-Control-Allow-Origin"] = "*";
        if (token) myHeaders["Authorization"] = `Bearer ${token}`;

        var requestOptions = {
            headers: myHeaders
        };

        const response = await axios.get(API_URL + url, requestOptions);
        return response;
        
    } catch (error: any) {
        console.log("error", error);
        return error;
    }
}
