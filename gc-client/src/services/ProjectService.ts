import { getData, postData } from "./utils";

export async function createProject(projectData: any, token: string) {
    if(!projectData) return null;
    const response = await postData(projectData, "/project/createProject", token);
    return response;
}

export async function getProjectList(token: string){
    const response = await getData(`/project/getProjects`, token);
    return response;
}

export async function getProject(projectId: any, token: string){
    const response = await getData(`/project/${projectId}`, token);
    return response;
}

export async function getProjectContributors(projectId: any, token: string){
    const response = await getData(`/project/${projectId}/contributors`, token);
    return response;
}

export async function addCollaborator(collaboratorDetails: any, token: string) {
    if(!collaboratorDetails) return null;
    const response = await postData(collaboratorDetails, "/project/addContributor", token);
    return response;
}

export async function getExploreProjects(level: any, token: string) {
    const response = await getData(`/project/explore/${level}`, token);
    return response
}

export async function getDashboardData(token: string){
    const response = await getData(`/project/dashboard`, token);
    return response;
}

export async function sendProjectRequest(projectRequestData: any, token: string) {
    if(!projectRequestData) return null;
    const response = await postData(projectRequestData, "/project/sendProjectRequest", token);
    return response;
}