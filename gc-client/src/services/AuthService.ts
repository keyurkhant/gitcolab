import { postData } from "./utils";

export async function getGithubAccessToken(githubData: any, token: string) {
    if(!githubData) return null;
    const response = await postData(githubData, "/integration-tool/github/getAccessToken", token);
    return response;
}
export async function getAtlassianAccessToken(atlassianData: any, token: string) {
    if(!atlassianData) return null;
    const response = await postData(atlassianData, "/integration-tool/atlassian/getAccessToken", token);
    return response;
}